package xcfg4j.console;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import xcfg4j.XmlConfig;

@XmlRootElement(name = "connectionStrings")
public class ConnectionStrings extends XmlConfig<ConnectionStrings> {
	@XmlElement(name = "add")
	private List<ConnectionString> conns;

	@XmlTransient
	public List<ConnectionString> getConns() {
		return conns;
	}

	public void setConns(List<ConnectionString> conns) {
		this.conns = conns;
	}
}
