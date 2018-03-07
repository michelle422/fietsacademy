package be.vdab.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;
import javax.persistence.Version;

import be.vdab.enums.Geslacht;

@Entity
@Table(name="docenten")
@NamedEntityGraphs({
	@NamedEntityGraph(name = Docent.MET_CAMPUS, attributeNodes = @NamedAttributeNode("campus")),
	@NamedEntityGraph(name = Docent.MET_CAMPUS_EN_VERANTWOORDELIJKHEDEN, 
						attributeNodes = {@NamedAttributeNode("campus"), 
										@NamedAttributeNode("verantwoordelijkheden")}), 
	@NamedEntityGraph(name = Docent.MET_CAMPUS_EN_MANAGER, 
						attributeNodes = @NamedAttributeNode(value = "campus", subgraph = "metManager"), 
							subgraphs = @NamedSubgraph(name = "metManager", 
											attributeNodes = @NamedAttributeNode("manager")))
})
public class Docent implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String MET_CAMPUS = "Docent.metCampus";
	public static final String MET_CAMPUS_EN_VERANTWOORDELIJKHEDEN = 
			"Docent.metCampusEnVerantwoordelijkheden";
	public static final String MET_CAMPUS_EN_MANAGER = "Docent.metCampusEnManager";
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String voornaam;
	private String familienaam;
	private BigDecimal wedde;
	private long rijksRegisterNr;
	@Enumerated(EnumType.STRING)
	private Geslacht geslacht;
	@ElementCollection
	@CollectionTable(name = "docentenbijnamen", joinColumns = @JoinColumn(name = "docentid"))
	@Column(name = "Bijnaam")
	private Set<String> bijnamen;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "campusid")
	private Campus campus;
	@ManyToMany(mappedBy = "docenten")
	private Set<Verantwoordelijkheid> verantwoordelijkheden;
	@Version
	private long versie;
	
	public Docent(String voornaam, String familienaam, BigDecimal wedde, long rijksRegisterNr, 
			Geslacht geslacht) {
		setVoornaam(voornaam);
		setFamilienaam(familienaam);
		setWedde(wedde);
		setRijksRegisterNr(rijksRegisterNr);
		setGeslacht(geslacht);
		bijnamen = new HashSet<>();
		verantwoordelijkheden = new LinkedHashSet<>();
	}

	protected Docent() {
	}
	
	public long getId() {
		return id;
	}
	public String getVoornaam() {
		return voornaam;
	}
	public String getFamilienaam() {
		return familienaam;
	}
	public BigDecimal getWedde() {
		return wedde;
	}
	public long getRijksRegisterNr() {
		return rijksRegisterNr;
	}
	public String getNaam() {
		return voornaam + ' ' + familienaam;
	}
	
	public Geslacht getGeslacht() {
		return geslacht;
	}

	public Set<String> getBijnamen() {
		return Collections.unmodifiableSet(bijnamen);
	}

	public Campus getCampus() {
		return campus;
	}

	public Set<Verantwoordelijkheid> getVerantwoordelijkheden() {
		return Collections.unmodifiableSet(verantwoordelijkheden);
	}

	public void setCampus(Campus campus) {
		if (this.campus != null && this.campus.getDocenten().contains(this)) {
			this.campus.remove(this);
		}
		this.campus = campus;
		if (this.campus != null && !this.campus.getDocenten().contains(this)) {
			this.campus.add(this);
		}
	}

	public static boolean isVoornaamValid(String voornaam) {
		return voornaam != null && !voornaam.trim().isEmpty();
	}
	
	public static boolean isFamilienaamValid(String familienaam) {
		return familienaam != null && !familienaam.trim().isEmpty();
	}
	
	public static boolean isWeddeValid(BigDecimal wedde) {
		return wedde != null && wedde.compareTo(BigDecimal.ZERO) >= 0;
	}
	
	public static boolean isRijksRegisterNrValid(long rijksRegisterNr) {
		long getal = rijksRegisterNr / 100;
		if (rijksRegisterNr / 1_000_000_000 < 50) {
			getal += 2_000_000_000;
		}
		return rijksRegisterNr % 100 == 97 - getal % 97;
	}
	
	public void setVoornaam(String voornaam) {
		if (!isVoornaamValid(voornaam)) {
			throw new IllegalArgumentException();
		}
		this.voornaam = voornaam;
	}

	public void setFamilienaam(String familienaam) {
		if (!isFamilienaamValid(familienaam)) {
			throw new IllegalArgumentException(); 
		}
		this.familienaam = familienaam;
	}

	public void setWedde(BigDecimal wedde) {
		if (!isWeddeValid(wedde)) {
			throw new IllegalArgumentException();
		}
		this.wedde = wedde;
	}

	public void setRijksRegisterNr(long rijksRegisterNr) {
		if (!isRijksRegisterNrValid(rijksRegisterNr)) {
			throw new IllegalArgumentException();
		}
		this.rijksRegisterNr = rijksRegisterNr;
	}

	public void setGeslacht(Geslacht geslacht) {
		this.geslacht = geslacht;
	}
	
	public void opslag(BigDecimal percentage) {
		BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
		wedde = wedde.multiply(factor).setScale(2, RoundingMode.HALF_UP);
	}
	
	public void addBijnaam(String bijnaam) {
		bijnamen.add(bijnaam);
	}
	
	public void removeBijnaam(String bijnaam) {
		bijnamen.remove(bijnaam);
	}
	
	public void add(Verantwoordelijkheid verantwoordelijkheid) {
		verantwoordelijkheden.add(verantwoordelijkheid);
		if (!verantwoordelijkheid.getDocenten().contains(this)) {
			verantwoordelijkheid.add(this);
		}
	}
	
	public void remove(Verantwoordelijkheid verantwoordelijkheid) {
		verantwoordelijkheden.remove(verantwoordelijkheid);
		if (verantwoordelijkheid.getDocenten().contains(this)) {
			verantwoordelijkheid.remove(this);
		}
	}

	@Override
	public int hashCode() {
		return Long.valueOf(rijksRegisterNr).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Docent)) {
			return false;
		}
		return rijksRegisterNr == ((Docent)obj).rijksRegisterNr;
	}
}
