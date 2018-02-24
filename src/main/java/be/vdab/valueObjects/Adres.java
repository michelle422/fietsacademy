package be.vdab.valueObjects;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Adres implements Serializable {
	private static final long serialVersionUID = 1L;
	private String straat;
	private String huisNr;
	private String postCode;
	private String gemeente;
	public Adres(String straat, String huisNr, String postCode, String gemeente) {
		this.straat = straat;
		this.huisNr = huisNr;
		this.postCode = postCode;
		this.gemeente = gemeente;
	}
	
	public String getStraat() {
		return straat;
	}
	public String getHuisNr() {
		return huisNr;
	}
	public String getPostCode() {
		return postCode;
	}
	public String getGemeente() {
		return gemeente;
	}
	protected Adres() {
	}
}
