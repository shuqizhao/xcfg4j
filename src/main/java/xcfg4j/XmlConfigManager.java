package xcfg4j;

import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class XmlConfigManager {

	private static final ConcurrentHashMap<String, XmlConfig<?>> _dic = new ConcurrentHashMap<String, XmlConfig<?>>();

	static {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(5 * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					int dicCount = _dic.size();
					if (dicCount > 0) {
						RemoteConfigSectionCollection rcfg = new RemoteConfigSectionCollection();
						rcfg.setApplication(Helper.getAppName());
						rcfg.setMachine(Helper.getHostName());
						rcfg.setEnvironment(Helper.getEnvironment());
						rcfg.setSections(new RemoteConfigSection[dicCount]);

						int i = 0;
						for (Entry<String, XmlConfig<?>> entry : _dic.entrySet()) {
							RemoteConfigSection rcs = new RemoteConfigSection();
							rcs.setSectionName(entry.getKey());
							rcs.setMajorVersion(entry.getValue().getMajor());
							rcs.setMinorVersion(entry.getValue().getMinor());
							rcfg.getSections()[i] = rcs;
							i++;
						}

						RemoteConfigSectionCollection rcfgResult = Helper.GetServerVersions(rcfg);
						if (rcfgResult.getSections() == null || rcfgResult.getSections().length == 0) {
//							System.out.println("...no change");
						} else {
							System.out.println("...has change");
							String cfgFolder = Helper.getAppCfgFolder();
							for (RemoteConfigSection v : rcfgResult.getSections()) {
								String cfgPath = cfgFolder + "/" + _dic.get(v.getSectionName()).getName() + ".config";
								boolean sucess = Helper.downloadRemoteCfg(v.getSectionName(), v.getDownloadUrl(),
										cfgPath);
								if (sucess) {
									String xmlStr = Helper.readToString(cfgPath);
									XmlConfig<?> xmlConfig = Helper.deserializeFromXml(xmlStr,
											_dic.get(v.getSectionName()).getClass());
									if (xmlConfig != null) {
										setXmlConfig(_dic.get(v.getSectionName()).getName(), xmlConfig);
									}
								}
							}
						}
					}
				}
			}
		}).start();
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
