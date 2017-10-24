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
@Table(name = "SITE_STATS")
public class SiteStat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "SITE_ID", nullable = false)
	private Long siteId;

	@Column(name = "CRAWL_DATE", nullable = false)
	private Integer crawlDate;

	@Column(name = "BACKLINKS")
	private Integer backLinks = null;

	@Column(name = "INDEXED")
	private Integer indexed = null;

	@Column(name = "PAGERANK")
	private Integer pageRank = 0;

	@Column(name = "NON_RANKING_VALUE")
	private Integer nonRankingValue = null;

	@Column(name = "TOTAL_KEYWORDS")
	private Integer totalKeywords = null;

	@Column(name = "TRACKED_KEYWORDS")
	private Integer trackedKeywords = null;

	@Column(name = "UNTRACKED_KEYWORDS")
	private Integer untrackedKeywords = null;

	@Column(name = "UNIQUE_KEYWORDS")
	private Integer uniqueKeywords = null;

	@Column(name = "NON_UNIQUE_KEYWORDS")
	private Integer nonUniqueKeywords = null;

	@Column(name = "RANKING_KEYWORDS")
	private Integer rankingKeywords = null;

	@Column(name = "GOOGLE_RANKING_KEYWORDS")
	private Integer googleRankingKeywords = null;

	@Column(name = "GOOGLE_BASE_RANKING_KEYWORDS")
	private Integer googleBaseRankingKeywords = null;

	@Column(name = "GOOGLE_TOP_TEN_KEYWORDS")
	private Integer googleTopTenKeywords = null;

	@Column(name = "GOOGLE_AVERAGE")
	private Float googleAverage = null;

	@Column(name = "GOOGLE_BASE_AVERAGE")
	private Float googleBaseAverage = null;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT")
	private Date createdAt;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_AT")
	private Date updatedAt = null;

	public Integer getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(Integer crawlDate) {
		this.crawlDate = crawlDate;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

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

	public Integer getBackLinks() {
		return backLinks;
	}

	public void setBackLinks(Integer backLinks) {
		this.backLinks = backLinks;
	}

	public Integer getIndexed() {
		return indexed;
	}

	public void setIndexed(Integer indexed) {
		this.indexed = indexed;
	}

	public Integer getPageRank() {
		return pageRank;
	}

	public void setPageRank(Integer pageRank) {
		this.pageRank = pageRank;
	}

	public Integer getTotalKeywords() {
		return totalKeywords;
	}

	public void setTotalKeywords(Integer totalKeywords) {
		this.totalKeywords = totalKeywords;
	}

	public Integer getRankingKeywords() {
		return rankingKeywords;
	}

	public void setRankingKeywords(Integer rankingKeywords) {
		this.rankingKeywords = rankingKeywords;
	}

	public Integer getGoogleTopTenKeywords() {
		return googleTopTenKeywords;
	}

	public void setGoogleTopTenKeywords(Integer googleTopTenKeywords) {
		this.googleTopTenKeywords = googleTopTenKeywords;
	}

	public Integer getTrackedKeywords() {
		return trackedKeywords;
	}

	public void setTrackedKeywords(Integer trackedKeywords) {
		this.trackedKeywords = trackedKeywords;
	}

	public Integer getUntrackedKeywords() {
		return untrackedKeywords;
	}

	public void setUntrackedKeywords(Integer untrackedKeywords) {
		this.untrackedKeywords = untrackedKeywords;
	}

	public Integer getUniqueKeywords() {
		return uniqueKeywords;
	}

	public void setUniqueKeywords(Integer uniqueKeywords) {
		this.uniqueKeywords = uniqueKeywords;
	}

	public Integer getNonUniqueKeywords() {
		return nonUniqueKeywords;
	}

	public void setNonUniqueKeywords(Integer nonUniqueKeywords) {
		this.nonUniqueKeywords = nonUniqueKeywords;
	}

	public Float getGoogleAverage() {
		return googleAverage;
	}

	public void setGoogleAverage(Float googleAverage) {
		this.googleAverage = googleAverage;
	}

	public Float getGoogleBaseAverage() {
		return googleBaseAverage;
	}

	public void setGoogleBaseAverage(Float googleBaseAverage) {
		this.googleBaseAverage = googleBaseAverage;
	}

	public Integer getGoogleRankingKeywords() {
		return googleRankingKeywords;
	}

	public void setGoogleRankingKeywords(Integer googleRankingKeywords) {
		this.googleRankingKeywords = googleRankingKeywords;
	}

	public Integer getGoogleBaseRankingKeywords() {
		return googleBaseRankingKeywords;
	}

	public void setGoogleBaseRankingKeywords(Integer googleBaseRankingKeywords) {
		this.googleBaseRankingKeywords = googleBaseRankingKeywords;
	}

	public Integer getNonRankingValue() {
		return nonRankingValue;
	}

	public void setNonRankingValue(Integer nonRankingValue) {
		this.nonRankingValue = nonRankingValue;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
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
		if (!(obj instanceof SiteStat))
			return false;

		SiteStat other = (SiteStat) obj;
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
		return "SiteStat [id=" + id + ", site_id=" + siteId + ", crawl_date=" + crawlDate + "]";
	}
}
