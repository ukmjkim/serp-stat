package com.serpstat.crawler.buffer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.serpstat.crawler.model.KeywordSERP;

public class SearchResultBuffer {
	private LinkedBlockingQueue<KeywordSERP> buffer;
	private ConcurrentHashMap<Long, String> storedItems;

	public SearchResultBuffer() {
		buffer = new LinkedBlockingQueue<>();
		storedItems = new ConcurrentHashMap<Long, String>();
	}

	public void add(KeywordSERP item) {
		storedItems.compute(item.getId(), (id, oldItem) -> {
			if (oldItem == null) {
				buffer.add(item);
				return item.getKeyword();
			} else {
				// System.out.println("Item " + item.getId() + " has been processed before");
				return oldItem;
			}
		});
	}

	public KeywordSERP get() throws InterruptedException {
		return buffer.take();
	}
}
