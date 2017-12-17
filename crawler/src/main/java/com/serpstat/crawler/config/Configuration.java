package com.serpstat.crawler.config;

import static java.lang.String.format;

import lombok.Data;

@Data
public class Configuration {
	private DBConnection database;

	@Override
	public String toString() {
		return new StringBuilder().append(format("Database: %s\n", database)).toString();
	}
}
