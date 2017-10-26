package com.serpstat.restapi.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.MarketDao;
import com.serpstat.restapi.model.Market;

public class MarketServiceImplTest {
	@Mock
	MarketDao dao;

	@InjectMocks
	MarketServiceImpl deviceService;

	@Spy
	List<Market> markets = new ArrayList<Market>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		markets = getMarketList();
	}

	@Test
	public void findById() {
		Market device = markets.get(0);
		when(dao.findById(anyInt())).thenReturn(device);
		Assert.assertEquals(deviceService.findById(device.getId()), device);
	}

	@Test
	public void findAll() {
		when(dao.findAll()).thenReturn(markets);
		Assert.assertEquals(deviceService.findAll(), markets);
	}

	@Test
	public void saveMarket() {
		doNothing().when(dao).save(any(Market.class));
		deviceService.saveMarket((Market) any(Market.class));
		verify(dao, atLeastOnce()).save(any(Market.class));
	}

	@Test
	public void updateSite() {
		Market device = markets.get(0);
		when(dao.findById(anyInt())).thenReturn(device);
		deviceService.updateMarket(device);
		verify(dao, atLeastOnce()).findById(anyInt());
	}

	@Test
	public void deleteById() {
		doNothing().when(dao).deleteById(anyInt());
		deviceService.deleteById(anyInt());
		verify(dao, atLeastOnce()).deleteById(anyInt());
	}

	@Test
	public void isMarketUnique() {
		Market device = markets.get(0);
		when(dao.findByRegionAneLang(anyString(), anyString())).thenReturn(device);
		Assert.assertEquals(deviceService.isMarketUnique(device.getRegion(), device.getLang()), true);
	}

	public List<Market> getMarketList() {
		List<Market> markets = new LinkedList<>();
		Market market1 = new Market();
		market1.setId(1);
		market1.setRegion("region1");
		market1.setLang("lang1");
		market1.setRegionName("region1");
		market1.setLangName("lang1");

		Market market2 = new Market();
		market2.setId(2);
		market2.setRegion("region2");
		market2.setLang("lang2");
		market2.setRegionName("region2");
		market2.setLangName("lang2");

		markets.add(market1);
		markets.add(market2);

		return markets;
	}
}
