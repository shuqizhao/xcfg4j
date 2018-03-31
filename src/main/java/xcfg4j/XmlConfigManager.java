package xcfg4j;

import java.util.concurrent.ConcurrentHashMap;

public class XmlConfigManager {
	
	private static final ConcurrentHashMap<String, XmlConfig> _dic = new ConcurrentHashMap<String, XmlConfig>();
	
	static {

	}

	static void setXmlConfig(String name, XmlConfig<?> xmlConfig) {
		xmlConfig.setName(name);
		_dic.put(name.toLowerCase(), xmlConfig);
	}

	static XmlConfig<?> getXmlConfig(String name) {
		name = name.toLowerCase();
		if (_dic.containsKey(name)) {
			return _dic.get(name);
		} else {
			return null;
		}
	}
}
