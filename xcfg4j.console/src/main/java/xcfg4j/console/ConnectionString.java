package xcfg4j.console;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

public class ConnectionString {
	@XmlAttribute(name = "name")
	private String name;

	@XmlAttribute(name = "providerName")
	private String providerName;

	@XmlAttribute(name = "connectionString")
	private String connectionStr;

	@XmlTransient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	@XmlTransient
	public String getConnectionStr() {
		return connectionStr;
	}

	public void setConnectionStr(String connectionStr) {
		this.connectionStr = connectionStr;
	}
}
