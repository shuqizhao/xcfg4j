package xcfg4j;

import javax.xml.bind.annotation.XmlAttribute;

public class XmlConfigEntity {

	private String name;

	@XmlAttribute(name = "majorVersion")
	private int major;

	@XmlAttribute(name = "minorVersion")
	private int minor;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}
}
