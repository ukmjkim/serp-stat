package com.serpstat.restapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "SITE_SEARCH_VOLUMES")
public class SiteSearchVolume {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "SITE_ID", nullable = false)
	private Long siteId;

	@Column(name = "CRAWL_DATE", nullable = false)
	private Integer crawlDate;

	@Column(name = "GLOBAL_AGGREGATE_SV")
	private Long globalAggregateSV = null;

	@Column(name = "GLOBAL_AVERAGE_SV")
	private Double globalAverageSV = null;

	@Column(name = "REGIONAL_AGGREGATE_SV")
	private Long regionalAggregateSV = null;

	@Column(name = "REGIONAL_AVERAGE_SV")
	private Double regionalAverageSV = null;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT")
	private Date createdAt;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_AT")
	private Date updatedAt = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Integer getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(Integer crawlDate) {
		this.crawlDate = crawlDate;
	}

	public Long getGlobalAggregateSV() {
		return globalAggregateSV;
	}

	public void setGlobalAggregateSV(Long globalAggregateSV) {
		this.globalAggregateSV = globalAggregateSV;
	}

	public Double getGlobalAverageSV() {
		return globalAverageSV;
	}

	public void setGlobalAverageSV(Double globalAverageSV) {
		this.globalAverageSV = globalAverageSV;
	}

	public Long getRegionalAggregateSV() {
		return regionalAggregateSV;
	}

	public void setRegionalAggregateSV(Long regionalAggregateSV) {
		this.regionalAggregateSV = regionalAggregateSV;
	}

	public Double getRegionalAverageSV() {
		return regionalAverageSV;
	}

	public void setRegionalAverageSV(Double regionalAverageSV) {
		this.regionalAverageSV = regionalAverageSV;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((siteId == null) ? 0 : siteId.hashCode());
		result = prime * result + ((crawlDate == null) ? 0 : crawlDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SiteSearchVolume))
			return false;

		SiteSearchVolume other = (SiteSearchVolume) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}

		if (siteId == null) {
			if (other.siteId != null)
				return false;
		} else if (!siteId.equals(other.siteId)) {
			return false;
		}

		if (crawlDate == null) {
			if (other.crawlDate != null)
				return false;
		} else if (!crawlDate.equals(other.crawlDate)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "SiteSearchVolume [id=" + id + ", site_id=" + siteId + ", crawl_date=" + crawlDate + "]";
	}
}
