package jkt.centralisateur.storage.common;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class PerformanceDataKey {
	private String paquetage;
	private String classe;
	private String methode;
	
	public String getPaquetage() {
		return paquetage;
	}
	
	public void setPaquetage(String paquetage) {
		this.paquetage = paquetage;
	}
	
	public String getClasse() {
		return classe;
	}
	
	public void setClasse(String classe) {
		this.classe = classe;
	}
	
	public String getMethode() {
		return methode;
	}
	
	public void setMethode(String methode) {
		this.methode = methode;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}
