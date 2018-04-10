package xcfg4j.console;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MyBatisHelper {
	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			String path = xcfg4j.Helper.getAppCfgFolder();
			MybaitsEntity.getInstance(MybaitsEntity.class);
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(path + "/mybatis.config"));
			Element rootEl = document.getRootElement();
			rootEl.remove(rootEl.attribute("majorVersion"));
			rootEl.remove(rootEl.attribute("minorVersion"));
			String xmlStr = document.asXML();
			InputStream inputStream = new ByteArrayInputStream(xmlStr.getBytes());
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static SqlSessionFactory getSession() {
		return sqlSessionFactory;
	}
}
