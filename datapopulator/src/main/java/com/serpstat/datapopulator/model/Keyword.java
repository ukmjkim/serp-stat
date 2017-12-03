package com.serpstat.datapopulator.model;

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
public class Keyword implements Entity {
	private Long id;
	private Long siteId;
	private String keyword;

	public Keyword(Long Id, Long siteId, String keyword) {
		super();
		this.id = Id;
		this.siteId = siteId;
		this.keyword = keyword;
	}
}