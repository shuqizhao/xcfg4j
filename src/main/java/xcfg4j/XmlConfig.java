package xcfg4j;

import java.lang.reflect.ParameterizedType;

import javax.xml.bind.annotation.XmlType;

public class XmlConfig<T extends XmlConfigEntity> extends XmlConfigEntity {

	private static XmlConfig<?> instance = new XmlConfig();

	protected XmlConfig() {
	}

	public static <T> XmlConfig<?> newInstance(Class<T> entity) {
		String cfgName = GetCfgName(entity);
		System.out.println(cfgName);
		return instance;
	}

	private static <T> String GetCfgName(Class<T> t) {
		String cfgName = "";
		XmlType xmlType = t.getAnnotation(XmlType.class);
		if (xmlType != null && !Helper.isNullOrEmpty(xmlType.name())) {
			cfgName = xmlType.name();
		}

		if (Helper.isNullOrEmpty(cfgName)) {
			cfgName = t.getSimpleName();
		}
		return cfgName;
	}
}
