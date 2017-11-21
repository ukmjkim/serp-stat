package com.serpstat.rest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Data
@Table(name="MARKETS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
@Getter
@Setter
@NamedQueries({
	@NamedQuery(
		name="findByRegionAndLang",
		query="SELECT t FROM Market t WHERE region = :region AND lang = :lang"
	)
})
public class Market {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "REGION", nullable = false)
	private String region;

	@Column(name = "LANG", nullable = false)
	private String lang;

	@Column(name = "REGION_NAME", nullable = true)
	private String regionName;

	@Column(name = "LANG_NAME", nullable = true)
	private String langName;

	public Market(String region, String lang, String regionName, String langName) {
		super();
		this.region = region;
		this.lang = lang;
		this.regionName = regionName;
		this.langName = langName;
	}
}
