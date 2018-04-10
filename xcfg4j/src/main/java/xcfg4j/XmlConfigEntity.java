package xcfg4j;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

public class XmlConfigEntity {

	private String name;

	@XmlAttribute(name = "majorVersion")
	private int major;

	@XmlAttribute(name = "minorVersion")
	private int minor;

	@XmlTransient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlTransient
	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	@XmlTransient
	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}
}
