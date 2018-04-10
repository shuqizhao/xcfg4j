package xcfg4j;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "RemoteConfigSectionCollection")
public class RemoteConfigSectionCollection {

	@XmlAttribute(name = "machine")
	private String machine;

	@XmlAttribute(name = "application")
	private String application;

	@XmlAttribute(name = "env")
	private String environment;

	@XmlElement(name = "section")
	private RemoteConfigSection[] sections;

	@XmlTransient
	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
	}

	@XmlTransient
	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	@XmlTransient
	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	@XmlTransient
	public RemoteConfigSection[] getSections() {
		return sections;
	}

	public void setSections(RemoteConfigSection[] sections) {
		this.sections = sections;
	}
}
