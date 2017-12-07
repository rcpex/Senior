package Senior.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="cidades")
public class Cidade {

	@Id
	@Column(name="ibge_id")
	private String ibgeId;
	
	private String uf;
	private String name;
	private boolean capital;
	private double lon;
	private double lat;
	
	@Column(name="no_accents")
	private String noAccents;
	
	@Column(name="alternative_names")
	private String alternativeNames;
	
	private String microregion;
	private String mesoregion;
	
	public String getIbgeId() {
		return ibgeId;
	}
	
	public void setIbgeId(String ibgeId) {
		this.ibgeId = ibgeId;
	}
	
	public String getUf() {
		return uf;
	}
	
	public void setUf(String uf) {
		this.uf = uf;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isCapital() {
		return capital;
	}
	
	public void setCapital(boolean capital) {
		this.capital = capital;
	}
	
	public double getLon() {
		return lon;
	}
	
	public void setLon(double lon) {
		this.lon = lon;
	}
	
	public double getLat() {
		return lat;
	}
	
	public void setLat(double lat) {
		this.lat = lat;
	}
	
	public String getNoAccents() {
		return noAccents;
	}
	
	public void setNoAccents(String noAccents) {
		this.noAccents = noAccents;
	}
	
	public String getAlternativeNames() {
		return alternativeNames;
	}
	
	public void setAlternativeNames(String alternativeNames) {
		this.alternativeNames = alternativeNames;
	}
	
	public String getMicroregion() {
		return microregion;
	}
	
	public void setMicroregion(String microregion) {
		this.microregion = microregion;
	}
	
	public String getMesoregion() {
		return mesoregion;
	}
	
	public void setMesoregion(String mesoregion) {
		this.mesoregion = mesoregion;
	}
}