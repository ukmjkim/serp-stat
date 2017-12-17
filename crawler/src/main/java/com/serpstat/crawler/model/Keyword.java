package com.serpstat.crawler.model;

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
public class Keyword {
	private Long id;
	private Long siteId;
	private String url;
	private String keyword;

	public Keyword(Long Id, Long siteId, String url, String keyword) {
		super();
		this.id = Id;
		this.siteId = siteId;
		this.url = url;
		this.keyword = keyword;
	}
}