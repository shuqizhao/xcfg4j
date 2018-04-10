package xcfg4j.console;

import java.io.File;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

public class Log4jHelper {
	public static final Logger LOGGER;
	static {
		try {
			String path = xcfg4j.Helper.getAppCfgFolder();
			Log4jEntity.getInstance(Log4jEntity.class);

			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(new File(path + "/log4j.config"));
			Element rootEl = document.getRootElement();
			rootEl.remove(rootEl.attribute("majorVersion"));
			rootEl.remove(rootEl.attribute("minorVersion"));
			String xmlStr = document.asXML();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document w3cDocument = builder.parse(new InputSource(new StringReader(xmlStr)));
			DOMConfigurator.configure(w3cDocument.getDocumentElement());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		LOGGER = LogManager.getLogger(Log4jHelper.class);
	}
}
