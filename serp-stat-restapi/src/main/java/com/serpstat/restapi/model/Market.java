package com.serpstat.restapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MARKETS")
public class Market {
	@Id
	@Column(name = "ID", nullable = false)
	private Integer id;

	@Column(name = "REGION", nullable = false)
	private String region;

	@Column(name = "LANG", nullable = false)
	private String lang;

	@Column(name = "REGION_NAME", nullable = false)
	private String regionName;

	@Column(name = "LANG_NAME", nullable = false)
	private String langName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getLangName() {
		return langName;
	}

	public void setLangName(String langName) {
		this.langName = langName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Market))
			return false;

		Market other = (Market) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Market [id=" + id + ", region=" + region + ", lang=" + lang + ", region_name=" + regionName
				+ ", lang_name=" + langName + "]";
	}
}
