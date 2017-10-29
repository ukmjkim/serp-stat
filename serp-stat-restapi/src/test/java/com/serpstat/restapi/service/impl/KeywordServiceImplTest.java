package com.serpstat.restapi.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.KeywordDao;
import com.serpstat.restapi.model.Device;
import com.serpstat.restapi.model.Keyword;
import com.serpstat.restapi.model.Market;

public class KeywordServiceImplTest {
	@Mock
	KeywordDao dao;

	@InjectMocks
	KeywordServiceImpl keywordService;

	@Spy
	List<Keyword> keywords = new ArrayList<Keyword>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		keywords = getKeywordList();
	}

	@Test
	public void findById() {
		Keyword keyword = keywords.get(0);
		when(dao.findById(anyInt())).thenReturn(keyword);
		Assert.assertEquals(keywordService.findById(keyword.getId()), keyword);
	}

	@Test
	public void findAllBySiteId() {
		when(dao.findAllBySiteId(anyLong())).thenReturn(keywords);
		Assert.assertEquals(keywordService.findAllBySiteId(1), keywords);
	}

	public void findPagenatedBySiteId() {
		when(dao.findPagenatedBySiteId(anyLong(), anyInt(), anyInt())).thenReturn(keywords);
		Assert.assertEquals(keywordService.findPagenatedBySiteId(1, 0, 500).size(), keywords.size());
	}

	public void findTotalCountBySiteId() {
		when(dao.findTotalCountBySiteId(anyLong())).thenReturn(432);
		Assert.assertEquals(keywordService.findTotalCountBySiteId(1), 432);
	}

	@Test
	public void findBySiteIdAndKeyword() {
		Keyword keyword = keywords.get(0);
		when(dao.findBySiteIdAndKeyword(anyLong(), anyString())).thenReturn(keyword);
		Assert.assertEquals(keywordService.findBySiteIdAndKeyword(keyword.getSiteId(), keyword.getKeyword()), keyword);
	}

	@Test
	public void saveKeyword() {
		doNothing().when(dao).save(any(Keyword.class));
		keywordService.saveKeyword((Keyword) any(Keyword.class));
		verify(dao, atLeastOnce()).save(any(Keyword.class));
	}

	@Test
	public void updateSite() {
		Keyword keyword = keywords.get(0);
		when(dao.findById(anyLong())).thenReturn(keyword);
		keywordService.updateKeyword(keyword);
		verify(dao, atLeastOnce()).findById(anyLong());
	}

	@Test
	public void deleteById() {
		doNothing().when(dao).deleteById(anyLong());
		keywordService.deleteById(anyLong());
		verify(dao, atLeastOnce()).deleteById(anyLong());
	}

	@Test
	public void isKeywordInSiteUnique() {
		Keyword keyword = keywords.get(0);
		when(dao.findBySiteIdAndKeyword(anyLong(), anyString())).thenReturn(keyword);
		Assert.assertEquals(keywordService.isKeywordInSiteUnique(keyword.getSiteId(), keyword.getKeyword()), true);
	}

	public List<Keyword> getKeywordList() {
		List<Keyword> keywords = new ArrayList<Keyword>();

		Device device = new Device();
		device.setId(1);
		device.setName("laptop");

		Market market = new Market();
		market.setId(1);
		market.setRegion("region");
		market.setLang("lang");
		market.setRegionName("regionName");
		market.setLangName("langName");

		Keyword keyword1 = new Keyword();
		keyword1.setId(1L);
		keyword1.setSiteId(1L);
		keyword1.setKeyword("new keyword1");
		keyword1.setDevice(device);
		keyword1.setMarket(market);

		Keyword keyword2 = new Keyword();
		keyword2.setId(2L);
		keyword2.setSiteId(1L);
		keyword2.setKeyword("new keyword2");
		keyword2.setDevice(device);
		keyword2.setMarket(market);

		keywords.add(keyword1);
		keywords.add(keyword2);

		return keywords;
	}
}
