package com.serpstat.crawler.config;

import static com.serpstat.crawler.common.Constants.CONFIG_FILE;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.yaml.snakeyaml.Yaml;

public class YAMLConfigLoader {
	public Configuration load() throws IOException {
		Yaml yaml = new Yaml();
		try (InputStream in = Files.newInputStream(Paths.get(CONFIG_FILE))) {
			Configuration config = yaml.loadAs(in, Configuration.class);
			return config;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
