package com.serpstat.rest.controller;

import static com.serpstat.rest.utils.JsonConverter.convertJsonToList;
import static com.serpstat.rest.utils.JsonConverter.convertJsonToMap;
import static com.serpstat.rest.utils.JsonConverter.convertObjectToJson;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.serpstat.rest.domain.Market;
import com.serpstat.rest.repository.MarketRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = MarketController.class, secure = false)
public class MarketControllerTest {
	final static String URI_MARKET = "/api/v1/markets";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MarketRepository marketRepository;

	@Test
	public void listAllMarkets() throws Exception {
		List<Market> marketList = getMarketList();

		when(marketRepository.findAll()).thenReturn(marketList);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URI_MARKET).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		verify(marketRepository, atLeastOnce()).findAll();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		List<Map<String, Object>> list = convertJsonToList(result.getResponse().getContentAsString());
		assertEquals(2, list.size());
	}

	@Test
	public void getMarket() throws Exception {
		Market market = getMarketList().get(0);

		when(marketRepository.findById(anyInt())).thenReturn(Optional.of(market));

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URI_MARKET+"/"+market.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		verify(marketRepository, atLeastOnce()).findById(anyInt());
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());

		assertEquals(1,  map.get("id"));
	}

	@Test
	public void createMarketWithSuccess() {
		Market newMarket = getNewMarket();
		String marketJson = convertObjectToJson(newMarket);

		when(marketRepository.findByRegionAndLang(anyString(), anyString())).thenReturn(Optional.empty());
		when(marketRepository.saveAndFlush(any(Market.class))).thenReturn(newMarket);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI_MARKET)
				.accept(MediaType.APPLICATION_JSON).content(marketJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
			assertEquals("http://localhost"+URI_MARKET+"/1", result.getResponse().getHeader(HttpHeaders.LOCATION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createMarketWithConflict() {
		Market newMarket = getNewMarket();
		String marketJson = convertObjectToJson(newMarket);

		when(marketRepository.findByRegionAndLang(anyString(), anyString())).thenReturn(Optional.of(newMarket));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI_MARKET)
				.accept(MediaType.APPLICATION_JSON).content(marketJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

			Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());
			assertEquals(HttpStatus.CONFLICT.value(), ((Integer) map.get("errorCode")).intValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateMarketWithSuccess() {
		Market newMarket = getNewMarket();
		String marketJson = convertObjectToJson(newMarket);

		when(marketRepository.findById(anyInt())).thenReturn(Optional.of(newMarket));
		when(marketRepository.saveAndFlush(any(Market.class))).thenReturn(newMarket);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI_MARKET+"/"+newMarket.getId())
				.accept(MediaType.APPLICATION_JSON).content(marketJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateMarketWithNotFound() {
		Market newMarket = getNewMarket();
		String marketJson = convertObjectToJson(newMarket);

		when(marketRepository.findById(anyInt())).thenReturn(Optional.empty());
		when(marketRepository.saveAndFlush(any(Market.class))).thenReturn(newMarket);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI_MARKET+"/"+newMarket.getId())
				.accept(MediaType.APPLICATION_JSON).content(marketJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

			Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());
			assertEquals(HttpStatus.NOT_FOUND.value(), ((Integer) map.get("errorCode")).intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteMarketWithSuccess() {
		Market newMarket = getNewMarket();

		when(marketRepository.findById(anyInt())).thenReturn(Optional.of(newMarket));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI_MARKET+"/"+newMarket.getId())
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			verify(marketRepository, atLeastOnce()).deleteById(anyInt());

			assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteMarketWithNotFound() {
		Market newMarket = getNewMarket();
		String marketJson = convertObjectToJson(newMarket);
		
		when(marketRepository.findById(anyInt())).thenReturn(Optional.empty());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI_MARKET+"/"+newMarket.getId())
				.accept(MediaType.APPLICATION_JSON).content(marketJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			verify(marketRepository, never()).deleteById(anyInt());
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

			Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());
			assertEquals(HttpStatus.NOT_FOUND.value(), ((Integer) map.get("errorCode")).intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Market getNewMarket() {
		Market newMarket = new Market("CA", "en", "CANADA", "English");
		newMarket.setId(1);
		return newMarket;
	}

	private List<Market> getMarketList() {
		Market market1 = new Market("US", "en", "USA", "English");
		market1.setId(1);

		Market market2 = new Market("US", "en", "USA", "Spanish");
		market2.setId(2);

		List<Market> markets = new ArrayList<>();
		markets.add(market1);
		markets.add(market2);
		return markets;
	}
}
