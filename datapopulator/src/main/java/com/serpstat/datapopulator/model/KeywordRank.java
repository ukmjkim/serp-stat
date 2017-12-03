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
public class KeywordRank implements Entity {
	private Long keywordId;
	private Integer rank;
	private Integer displayRank;
	private Integer baseRank;
	private Integer movement;
	private Integer urlId;
	private Date createdAt;
	private Date updatedAt;

	public KeywordRank(Long keywordId, Integer rank, Integer displayRank,
			Integer baseRank, Integer movement, Integer urlId,
			Date createdAt, Date updatedAt) {
		super();
		this.keywordId = keywordId;
		this.rank = rank;
		this.displayRank = displayRank;
		this.baseRank = baseRank;
		this.movement = movement;
		this.urlId = urlId;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
}
