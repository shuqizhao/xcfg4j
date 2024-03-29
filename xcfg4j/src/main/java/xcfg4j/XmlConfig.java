package xcfg4j;

import java.io.File;
import javax.xml.bind.annotation.XmlRootElement;

public class XmlConfig<T extends XmlConfigEntity> extends XmlConfigEntity {

	protected XmlConfig() {

	}

	static {
		String cfgFolder = Helper.getAppCfgFolder();
		File file = new File(cfgFolder);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static synchronized <T extends XmlConfig<T>> T getInstance(Class<T> entity) {
		String cfgName = getCfgName(entity);
		@SuppressWarnings("unchecked")
		T instance = (T) XmlConfigManager.getXmlConfig(cfgName);
		if (instance == null) {
			instance = newInstance(entity);
			XmlConfigManager.setXmlConfig(cfgName, instance);
		}
		return instance;
	}

	private static <T extends XmlConfig<T>> T newInstance(Class<T> entity) {
		String cfgFolder = Helper.getAppCfgFolder();
		File file = new File(cfgFolder);
		if (!file.exists()) {
			file.mkdirs();
		}
		String cfgName = getCfgName(entity);
		String cfgPath = cfgFolder + "/" + cfgName + ".config";
		T instance = loadLocalCfg(cfgPath, entity);
		int majorVersion = 1;
		int minorVersion = 0;
		if (instance != null) {
			majorVersion = instance.getMajor();
			minorVersion = instance.getMinor();
		}
		RemoteConfigSection rcs = Helper.getRemoteConfigSectionParam(cfgName, majorVersion, minorVersion);
		if (rcs != null) {
			boolean sucess = Helper.downloadRemoteCfg("", rcs.getDownloadUrl(), cfgPath);
			if (sucess) {
				instance = loadLocalCfg(cfgPath, entity);
			}
		}
		return instance;
	}

	private static <T> String getCfgName(Class<T> t) {
		String cfgName = "";
		XmlRootElement xmlRootElement = t.getAnnotation(XmlRootElement.class);
		if (xmlRootElement != null && !Helper.isNullOrEmpty(xmlRootElement.name())) {
			cfgName = xmlRootElement.name();
		}

		if (Helper.isNullOrEmpty(cfgName)) {
			cfgName = t.getSimpleName();
		}
		return cfgName;
	}

	private static <T> T loadLocalCfg(String cfgPath, Class<T> entity) {
		File file = new File(cfgPath);
		if (!file.exists()) {
			return null;
		}
		if (entity.getDeclaredFields().length == 0) {
			return null;
		}
		String xmlStr = Helper.readToString(file);
		return Helper.deserializeFromXml(xmlStr, entity);
	}
}
