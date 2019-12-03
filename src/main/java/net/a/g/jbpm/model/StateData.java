package net.a.g.jbpm.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class was automatically generated by the data modeler tool.
 */
@XmlRootElement
public class StateData implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	@org.kie.api.definition.type.Label("Id")
	private int id;
	@org.kie.api.definition.type.Label("Date")
	private java.time.LocalDateTime date;
	@org.kie.api.definition.type.Label("Name")
	private java.lang.String name;
	@org.kie.api.definition.type.Label("Transaction ID")
	private java.lang.Long txId;

	public StateData() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public java.time.LocalDateTime getDate() {
		return this.date;
	}

	public void setDate(java.time.LocalDateTime date) {
		this.date = date;
	}

	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.Long getTxId() {
		return this.txId;
	}

	public void setTxId(java.lang.Long txId) {
		this.txId = txId;
	}

	public StateData(int id, java.time.LocalDateTime date, java.lang.String name, java.lang.Long txId) {
		this.id = id;
		this.date = date;
		this.name = name;
		this.txId = txId;
	}

	@Override
	public String toString() {
		return "StateData [id=" + id + ", date=" + date + ", name=" + name + ", txId=" + txId + "]";
	}

}