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
public class KeywordStat {
	private Long keywordId;
	private Long googleResults;
	private Float googleKEI;
	private Date createdAt;
	private Date updatedAt;

	public KeywordStat(Long keywordId, Long googleResults, Float googleKEI,
			Date createdAt, Date updatedAt) {
		super();
		this.keywordId = keywordId;
		this.googleResults = googleResults;
		this.googleKEI = googleKEI;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
