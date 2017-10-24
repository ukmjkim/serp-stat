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
@Table(name = "TAG_STATS")
public class TagStat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TAG_ID", nullable = false)
	private Long tagId;

	@Column(name = "CRAWL_DATE", nullable = false)
	private Integer crawlDate;

	@Column(name = "TOTAL_KEYWORDS")
	private Integer totalKeywords = null;

	@Column(name = "TRACKED_KEYWORDS")
	private Integer trackedKeywords = null;

	@Column(name = "RANKING_KEYWORDS")
	private Integer rankingKeywords = null;

	@Column(name = "GOOGLE_RANKING_KEYWORDS")
	private Integer googleRankingKeywords = null;

	@Column(name = "GOOGLE_BASE_RANKING_KEYWORDS")
	private Integer googleBaseRankingKeywords = null;

	@Column(name = "GOOGLE_TOP_TEN_KEYWORDS")
	private Integer googleTopTenKeywords = null;

	@Column(name = "UNTRACKED_KEYWORDS")
	private Integer untrackedKeywords = null;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTagId() {
		return tagId;
	}

	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}

	public Integer getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(Integer crawlDate) {
		this.crawlDate = crawlDate;
	}

	public Integer getTotalKeywords() {
		return totalKeywords;
	}

	public void setTotalKeywords(Integer totalKeywords) {
		this.totalKeywords = totalKeywords;
	}

	public Integer getTrackedKeywords() {
		return trackedKeywords;
	}

	public void setTrackedKeywords(Integer trackedKeywords) {
		this.trackedKeywords = trackedKeywords;
	}

	public Integer getRankingKeywords() {
		return rankingKeywords;
	}

	public void setRankingKeywords(Integer rankingKeywords) {
		this.rankingKeywords = rankingKeywords;
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

	public Integer getGoogleTopTenKeywords() {
		return googleTopTenKeywords;
	}

	public void setGoogleTopTenKeywords(Integer googleTopTenKeywords) {
		this.googleTopTenKeywords = googleTopTenKeywords;
	}

	public Integer getUntrackedKeywords() {
		return untrackedKeywords;
	}

	public void setUntrackedKeywords(Integer untrackedKeywords) {
		this.untrackedKeywords = untrackedKeywords;
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
		result = prime * result + ((tagId == null) ? 0 : tagId.hashCode());
		result = prime * result + ((crawlDate == null) ? 0 : crawlDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TagStat))
			return false;

		TagStat other = (TagStat) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}

		if (tagId == null) {
			if (other.tagId != null)
				return false;
		} else if (!tagId.equals(other.tagId)) {
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
		return "TagStat [id=" + id + ", tag_id=" + tagId + ", crawl_date=" + crawlDate + "]";
	}
}
