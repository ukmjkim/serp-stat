package com.serpstat.crawler.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DBConnection {
	private String host;
	private String user;
	private String password;
}
