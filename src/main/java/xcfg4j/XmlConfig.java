package xcfg4j;

public class XmlConfig<T extends XmlConfigEntity> extends XmlConfigEntity {

	private static XmlConfig<?> instance = new XmlConfig();

	protected XmlConfig() {

	}

	public static XmlConfig<?> newInstance() {
		return instance;
	}
	
	private static String getCfgName()
    {
        String cfgName = "";
        var attrs = T..GetCustomAttributes(false);
        foreach (var typeAttribute in attrs)
        {
            if (typeAttribute is XmlRootAttribute)
            {
                var ta = typeAttribute as XmlRootAttribute;
                cfgName = ta.ElementName;
            }
        }
        if (string.IsNullOrWhiteSpace(cfgName))
        {
            cfgName = typeof(T).Name;
        }
        return cfgName;
    }
}
