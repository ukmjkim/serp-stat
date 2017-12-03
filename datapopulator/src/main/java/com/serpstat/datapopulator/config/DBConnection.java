package com.serpstat.datapopulator.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DBConnection {
	private String host;
	private String user;
	private String pwd;
}
