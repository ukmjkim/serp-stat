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

	public SiteStat(Long siteId, Integer crawlDate, Integer backLinks, Integer indexed, Integer pageRank,
			Integer nonRankingValue, Integer totalKeywords, Integer trackedKeywords, Integer untrackedKeywords,
			Integer uniqueKeywords, Integer nonUniqueKeywords, Integer rankingKeywords, Integer googleRankingKeywords,
			Integer googleBaseRankingKeywords, Integer googleTopTenKeywords, Float googleAverage,
			Float googleBaseAverage) {
		super();
		this.siteId = siteId;
		this.crawlDate = crawlDate;
		this.backLinks = backLinks;
		this.indexed = indexed;
		this.pageRank = pageRank;
		this.nonRankingValue = nonRankingValue;
		this.totalKeywords = totalKeywords;
		this.trackedKeywords = trackedKeywords;
		this.untrackedKeywords = untrackedKeywords;
		this.uniqueKeywords = uniqueKeywords;
		this.nonUniqueKeywords = nonUniqueKeywords;
		this.rankingKeywords = rankingKeywords;
		this.googleRankingKeywords = googleRankingKeywords;
		this.googleBaseRankingKeywords = googleBaseRankingKeywords;
		this.googleTopTenKeywords = googleTopTenKeywords;
		this.googleAverage = googleAverage;
		this.googleBaseAverage = googleBaseAverage;
	}
}
