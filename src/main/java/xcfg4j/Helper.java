package xcfg4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Helper {

	public static final String Product = "prod";
	public static final String Dev = "dev";
	public static final String Testing = "testing";
	public static final String Labs = "labs";

	public static String getCfgFolder() {
		String dd = System.getProperty("os.name");
		if (dd.contains("Windows")) {
			return "c:/xcfg.configs";
		} else {
			return "/usr/local/etc/xcfg.configs";
		}
	}

	public static String getAppName() {
		String appName = "";
		Properties prop = new Properties();
		try {
			File file = new File(Helper.class.getResource("").getPath() + "/app.properties");
			InputStream input = new FileInputStream(file);
			prop.load(input);
			appName = prop.getProperty("appname");
		} catch (Exception e) {
			// e.printStackTrace();
		}

		if (isNullOrEmpty(appName)) {
			String filePath = Helper.class.getResource("").getPath();
			appName = filePath.replace("\\", "_");
			appName = filePath.replace("/", "_");
			appName = appName.replace(":", "_");
		}
		return appName;
	}

	public static String getEnvironment() {

		String environment = "";
		Properties prop = new Properties();
		try {
			File file = new File(Helper.class.getResource("").getPath() + "/app.properties");
			InputStream input = new FileInputStream(file);
			prop.load(input);
			environment = prop.getProperty("environment");
		} catch (Exception e) {
			// e.printStackTrace();
		}

		if (environment.equals(Product)) {
			return Product;
		} else if (environment.equals(Labs)) {
			return Labs;
		} else if (environment.equals(Testing)) {
			return Testing;
		} else if (environment.equals(Dev)) {
			return Dev;
		} else {
			return Dev;
		}
	}

	public static String getAppCfgFolder() {
		return getCfgFolder() + "/" + getAppName() + "/" + getEnvironment();
	}

	public static String getRemoteCfgUrl() {
		return getRemoteCfgShortUrl() + "/ConfigVersionHandler.ashx";
	}

	public static String getRemoteCfgShortUrl() {
		String host = "";
		String port = "";
		Properties prop = new Properties();
		try {
			File file = new File(Helper.class.getResource("").getPath() + "/app.properties");
			InputStream input = new FileInputStream(file);
			prop.load(input);
			host = prop.getProperty("remote_cfg_host");
			port = prop.getProperty("remote_cfg_port");
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return String.format("http://%s:%s", host, port);
	}

	public static boolean isNullOrEmpty(String input) {
		return input == null || input.length() == 0;
	}

	public static <T> String serializeToXml(T obj) {
		try {
			StringWriter sw = new StringWriter();
			JAXBContext jc = JAXBContext.newInstance(obj.getClass());
			Marshaller ms = jc.createMarshaller();
			ms.marshal(obj, sw);
			String result = sw.getBuffer().toString();
			sw.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T deserializeFromXml(String xmlStr, Class<T> entity) {
		try {
			JAXBContext jc = JAXBContext.newInstance(entity);
			Unmarshaller unmar = jc.createUnmarshaller();
			T ret = (T) unmar.unmarshal(new StringReader(xmlStr));
			return ret;
		} catch (Exception ex) {
			return null;
		}
	}

	public static RemoteConfigSection getRemoteConfigSectionParam(String cfgName, int majorVersion, int minorVersion) {
		RemoteConfigSectionCollection rcfg = new RemoteConfigSectionCollection();
		rcfg.setApplication(Helper.getAppName());
		rcfg.setMachine(System.getenv().get("COMPUTERNAME"));
		rcfg.setEnvironment(Helper.getEnvironment());
		rcfg.setSections(new RemoteConfigSection[1]);
		RemoteConfigSection rcs = new RemoteConfigSection();
		rcs.setSectionName(cfgName.toLowerCase());
		rcs.setMajorVersion(majorVersion);
		rcs.setMinorVersion(minorVersion);
		rcfg.getSections()[0] = rcs;
		RemoteConfigSectionCollection rcfgResult = GetServerVersions(rcfg);
		if (rcfgResult == null || rcfgResult.getSections() == null || rcfgResult.getSections().length == 0) {
			return null;
		} else {
			return rcfgResult.getSections()[0];
		}
	}

	private static RemoteConfigSectionCollection GetServerVersions(RemoteConfigSectionCollection rcfg) {
		String requestStr = Helper.serializeToXml(rcfg);
		String url = Helper.getRemoteCfgUrl();
		String xmlStr = Helper.HttpPost(url, requestStr);
		return Helper.deserializeFromXml(xmlStr, RemoteConfigSectionCollection.class);
	}

	

	public static boolean downloadRemoteCfg(String sectionName, String url, String targetPath) {
		try {
			if (!url.startsWith("http")) {
				url = Helper.getRemoteCfgShortUrl() + "/" + url;
			}

			String data = Helper.HttpGet(url, "");

			data = FormatXml(data);

			String tempFile = targetPath + "." + UUID.randomUUID();
			FileWriter writer = new FileWriter(tempFile, true);
			writer.write(data);
			writer.close();
			File file = new File(targetPath);
			if (file.exists()) {
				file.delete();
			}
			File tFile = new File(tempFile);
			tFile.renameTo(file);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static String FormatXml(String data) {
		return data;
	}

	private static String HttpGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	public static String HttpPost(String url, String param) {
		PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
	}
}
