package xcfg4j;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

public class RemoteConfigSection {
	@XmlAttribute(name = "name")
	private String sectionName;

	@XmlAttribute(name = "majorVerion")
	private int majorVersion;

	@XmlAttribute(name = "minorVerion")
	private int minorVersion;

	@XmlAttribute(name = "downloadUrl")
	private String downloadUrl;

	@XmlTransient
	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	@XmlTransient
	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	@XmlTransient
	public int getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	@XmlTransient
	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}
}
