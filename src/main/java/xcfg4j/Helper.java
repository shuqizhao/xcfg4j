package xcfg4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

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
			File file = new File(Helper.class.getResource("").getPath() + "\\app.properties");
			InputStream input = new FileInputStream(file);
			prop.load(input);
			appName = prop.getProperty("appname");
		} catch (Exception e) {
			// e.printStackTrace();
		}

		if (isNullOrEmpty(appName)) {
			String filePath = System.getProperty("java.class.path");
			appName = filePath.replace("\\", "_");
			appName = appName.replace(":", "_");
		}
		return appName;
	}

	public static String getEnvironment() {

		String environment = "";
		Properties prop = new Properties();
		try {
			File file = new File(Helper.class.getResource("").getPath() + "\\app.properties");
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
	
	   public static byte[] SerializeToXml<T>(T obj)
       {
           byte[] bytes = null;
           try
           {
               using (System.IO.MemoryStream writer = new System.IO.MemoryStream())
               {
                   System.Xml.Serialization.XmlSerializer xs = new System.Xml.Serialization.XmlSerializer(typeof(T));
                   xs.Serialize(writer, obj);
                   bytes = writer.ToArray();
               }
           }
           catch (Exception ex) { }
           return bytes;
       }

	public static String GetRemoteCfgUrl()
       {
           return GetRemoteCfgShortUrl() + "/ConfigVersionHandler.ashx";
       }

       public static T DeserializeFromXml<T>(String xmlStr)
       {
           try
           {
               var bytes = Encoding.UTF8.GetBytes(xmlStr);
               System.Xml.Serialization.XmlSerializer xs = new System.Xml.Serialization.XmlSerializer(typeof(T));
               T ret = (T)xs.Deserialize(new MemoryStream(bytes));
               return ret;
           }
           catch (Exception ex)
           {
               return default(T);
           }
       }

       public static XmlConfig DeserializeFromXml(String xmlStr, Type type)
       {
           try
           {
               var bytes = Encoding.UTF8.GetBytes(xmlStr);
               System.Xml.Serialization.XmlSerializer xs = new System.Xml.Serialization.XmlSerializer(type);
               var ret = (XmlConfig)xs.Deserialize(new MemoryStream(bytes));
               return ret;
           }
           catch (Exception ex)
           {
               return null;
           }
       }

       public static String GetRemoteCfgShortUrl()
       {
           var host = ConfigurationManager.AppSettings["remote_cfg_host"] ?? "";
           var port = ConfigurationManager.AppSettings["remote_cfg_port"] ?? "";
           return $"http://{host}:{port}";
       }

	public static boolean isNullOrEmpty(String input) {
		return input == null || input.length() == 0;
	}
}
