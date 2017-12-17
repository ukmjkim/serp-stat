package com.serpstat.crawler.model;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
public class KeywordSERP {
	private Long id;
	private Long siteId;
	private String url;
	private String keyword;
	private Map<String, String> serpResult;
	
	public KeywordSERP() {
		Map<String, String> serpResult = new HashMap<>();
	}
}
