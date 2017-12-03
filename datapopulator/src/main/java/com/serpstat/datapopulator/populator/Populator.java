package com.serpstat.datapopulator.populator;

public interface Populator {
	public void populate() throws Exception;

	public void destroy();
}
