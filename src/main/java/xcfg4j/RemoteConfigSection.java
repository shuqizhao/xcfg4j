package xcfg4j;

import javax.xml.bind.annotation.XmlAttribute;

public class RemoteConfigSection {
	@XmlAttribute(name = "name")
	private String sectionName;

	@XmlAttribute(name = "majorVerion")
	private int majorVersion;

	@XmlAttribute(name = "minorVerion")
	private int minorVersion;

	@XmlAttribute(name = "downloadUrl")
	private String downloadUrl;

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public int getMinorVersion() {
		return minorVersion;
	}

	public void setMinorVersion(int minorVersion) {
		this.minorVersion = minorVersion;
	}

	public int getMajorVersion() {
		return majorVersion;
	}

	public void setMajorVersion(int majorVersion) {
		this.majorVersion = majorVersion;
	}
}
