package com.serpstat.rest.controller;

import static com.serpstat.rest.utils.JsonConverter.convertJsonToList;
import static com.serpstat.rest.utils.JsonConverter.convertJsonToMap;
import static com.serpstat.rest.utils.JsonConverter.convertObjectToJson;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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

import com.serpstat.rest.domain.Device;
import com.serpstat.rest.domain.Keyword;
import com.serpstat.rest.domain.Market;
import com.serpstat.rest.repository.KeywordRepository;

@RunWith(SpringRunner.class)
@WebMvcTest(value = KeywordController.class, secure = false)
public class KeywordControllerTest {
	final static String URI_KEYWORD = "/api/v1/site/1/keywords";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private KeywordRepository keywordRepository;

	@Test
	public void listAllKeywords() throws Exception {
		List<Keyword> keywordsList = getKeywordList();

		when(keywordRepository.findAll()).thenReturn(keywordsList);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URI_KEYWORD).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		verify(keywordRepository, atLeastOnce()).findAll();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		List<Map<String, Object>> list = convertJsonToList(result.getResponse().getContentAsString());
		assertEquals(2, list.size());
	}

	@Test
	public void getKeyword() throws Exception {
		Keyword keyword = getKeywordList().get(0);

		when(keywordRepository.findById(anyLong())).thenReturn(Optional.of(keyword));

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get(URI_KEYWORD+"/"+keyword.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		verify(keywordRepository, atLeastOnce()).findById(anyLong());
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());

		assertEquals(1,  map.get("id"));
	}

	@Test
	public void createKeywordWithSuccess() {
		Keyword newKeyword = getNewKeyword();
		String keywordJson = convertObjectToJson(newKeyword);

		when(keywordRepository.findBySiteIdAndKeyword(anyLong(), anyString())).thenReturn(Optional.empty());
		when(keywordRepository.saveAndFlush(any(Keyword.class))).thenReturn(newKeyword);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI_KEYWORD)
				.accept(MediaType.APPLICATION_JSON).content(keywordJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
			assertEquals("http://localhost"+URI_KEYWORD+"/1", result.getResponse().getHeader(HttpHeaders.LOCATION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createKeywordWithConflict() {
		Keyword newKeyword = getNewKeyword();
		String keywordJson = convertObjectToJson(newKeyword);

		when(keywordRepository.findBySiteIdAndKeyword(anyLong(), anyString())).thenReturn(Optional.of(newKeyword));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URI_KEYWORD)
				.accept(MediaType.APPLICATION_JSON).content(keywordJson).contentType(MediaType.APPLICATION_JSON);

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
	public void updateKeywordWithSuccess() {
		Keyword newKeyword = getNewKeyword();
		String keywordJson = convertObjectToJson(newKeyword);

		when(keywordRepository.findById(anyLong())).thenReturn(Optional.of(newKeyword));
		when(keywordRepository.saveAndFlush(any(Keyword.class))).thenReturn(newKeyword);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI_KEYWORD+"/"+newKeyword.getId())
				.accept(MediaType.APPLICATION_JSON).content(keywordJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateKeywordWithNotFound() {
		Keyword newKeyword = getNewKeyword();
		String keywordJson = convertObjectToJson(newKeyword);

		when(keywordRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(keywordRepository.saveAndFlush(any(Keyword.class))).thenReturn(newKeyword);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put(URI_KEYWORD+"/"+newKeyword.getId())
				.accept(MediaType.APPLICATION_JSON).content(keywordJson).contentType(MediaType.APPLICATION_JSON);

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
	public void deleteKeywordWithSuccess() {
		Keyword newKeyword = getNewKeyword();

		when(keywordRepository.findById(anyLong())).thenReturn(Optional.of(newKeyword));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI_KEYWORD+"/"+newKeyword.getId())
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			verify(keywordRepository, atLeastOnce()).deleteById(anyLong());

			assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteKeywordWithNotFound() {
		Keyword newKeyword = getNewKeyword();
		String keywordJson = convertObjectToJson(newKeyword);
		
		when(keywordRepository.findById(anyLong())).thenReturn(Optional.empty());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URI_KEYWORD+"/"+newKeyword.getId())
				.accept(MediaType.APPLICATION_JSON).content(keywordJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			verify(keywordRepository, never()).deleteById(anyLong());
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

			Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());
			assertEquals(HttpStatus.NOT_FOUND.value(), ((Integer) map.get("errorCode")).intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Device getDevice() {
		Device newDevice = new Device("desktop");
		newDevice.setId(1);
		return newDevice;
	}

	private Market getMarket() {
		Market newMarket = new Market("CA", "en", "CANADA", "English");
		newMarket.setId(1);
		return newMarket;
	}

	private Keyword getNewKeyword() {
		Device device = getDevice();
		Market market = getMarket();
		Keyword newKeyword = new Keyword(1L, "keyword1", "", "", 1, "US", device, market);
		newKeyword.setId(1L);
		return newKeyword;
	}

	private List<Keyword> getKeywordList() {
		Device device = getDevice();
		Market market = getMarket();
		Keyword Keyword1 = new Keyword(1L, "keyword1", "", "", 1, "US", device, market);
		Keyword1.setId(1L);

		Keyword Keyword2 = new Keyword(1L, "keyword2", "", "", 1, "US", device, market);
		Keyword2.setId(2L);

		List<Keyword> Keywords = new ArrayList<>();
		Keywords.add(Keyword1);
		Keywords.add(Keyword2);
		return Keywords;
	}
}
