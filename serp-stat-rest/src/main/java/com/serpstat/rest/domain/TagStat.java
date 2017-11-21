package com.serpstat.rest.domain;

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

	public TagStat(Long tagId, Integer crawlDate, Integer totalKeywords, Integer trackedKeywords,
			Integer rankingKeywords, Integer googleRankingKeywords, Integer googleBaseRankingKeywords,
			Integer googleTopTenKeywords, Float googleAverage, Float googleBaseAverage) {
		super();
		this.tagId = tagId;
		this.crawlDate = crawlDate;
		this.totalKeywords = totalKeywords;
		this.trackedKeywords = trackedKeywords;
		this.rankingKeywords = rankingKeywords;
		this.googleRankingKeywords = googleRankingKeywords;
		this.googleBaseRankingKeywords = googleBaseRankingKeywords;
		this.googleTopTenKeywords = googleTopTenKeywords;
		this.googleAverage = googleAverage;
		this.googleBaseAverage = googleBaseAverage;
	}
}
