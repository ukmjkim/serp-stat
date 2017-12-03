package com.serpstat.datapopulator.model;

import java.util.Date;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class KeywordDailyRank {
	private Long keywordId;
	private Integer rank;
	private Integer displayRank;
	private Integer baseRank;
	private Integer movement;
	private Integer urlId;
	private Float advertiserCompetition;
	private Integer globalMonthlySearches;
	private Integer regionalMonthlySearches;
	private Float cpc;
	private Long googleResults;
	private Float googleKEI;
	private Date createdAt;
	private Date updatedAt;

	public KeywordDailyRank(Long keywordId, Integer rank, Integer displayRank,
			Integer baseRank, Integer movement, Integer urlId,
			Float advertiserCompetition, Integer globalMonthlySearches,
			Integer regionalMonthlySearches, Float cpc,
			Long googleResults, Float googleKEI,
			Date createdAt, Date updatedAt) {
		super();
		this.keywordId = keywordId;
		this.rank = rank;
		this.displayRank = displayRank;
		this.baseRank = baseRank;
		this.movement = movement;
		this.urlId = urlId;
		this.advertiserCompetition = advertiserCompetition;
		this.globalMonthlySearches = globalMonthlySearches;
		this.regionalMonthlySearches = regionalMonthlySearches;
		this.cpc = cpc;
		this.googleResults = googleResults;
		this.googleKEI = googleKEI;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
