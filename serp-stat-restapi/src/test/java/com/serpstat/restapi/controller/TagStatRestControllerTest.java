package com.serpstat.restapi.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.exception.SiteStatNotFoundException;
import com.serpstat.restapi.exception.TagNotFoundException;
import com.serpstat.restapi.exception.TagStatNotFoundException;
import com.serpstat.restapi.exception.TagStatNotUniqueInTagException;
import com.serpstat.restapi.model.SiteStat;
import com.serpstat.restapi.model.Tag;
import com.serpstat.restapi.model.TagStat;
import com.serpstat.restapi.service.TagService;
import com.serpstat.restapi.service.TagStatService;

public class TagStatRestControllerTest {
	@Mock
	TagService tagService;

	@Mock
	TagStatService tagStatService;

	@Mock
	HttpHeaders headers;

	@InjectMocks
	TagStatRestController tagStatController;

	@Spy
	Tag tag = new Tag();

	@Spy
	List<TagStat> tagStats = new ArrayList<TagStat>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		tagStats = getTagStatList();
		tag = getTagList();
	}

	@Test
	public void getTagStat() {
		TagStat tagStat = tagStats.get(0);
		when(tagStatService.findById(anyLong())).thenReturn(tagStat);
		ResponseEntity<TagStat> response;
		try {
			response = tagStatController.getTagStat(tagStat.getTagId(), tagStat.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagStatService, atLeastOnce()).findById(anyLong());
		} catch (TagStatNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void listTagStat() {
		TagStat tagStat = tagStats.get(0);
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagStatService.findAllByTagIdInPeriod(anyLong(), anyInt(), anyInt())).thenReturn(tagStats);
		ResponseEntity<List<TagStat>> response;
		try {
			response = tagStatController.listTagStat(tagStat.getTagId(), tagStat.getCrawlDate(), tagStat.getCrawlDate());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagStatService, atLeastOnce()).findAllByTagIdInPeriod(anyLong(), anyInt(), anyInt());
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void createTagStatWithConflict() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(tagStatService).saveTagStat(any(TagStat.class));
		TagStat tagStat = tagStats.get(0);
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagStatService.isTagStatUnique(anyLong(), anyLong(), anyInt())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = tagStatController.createTagStat(tagStat.getTagId(), tagStat, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagStatService, atLeastOnce()).isTagStatUnique(anyLong(), anyLong(), anyInt());
		} catch (TagNotFoundException e) {

		} catch (TagStatNotUniqueInTagException e) {

		}
	}

	@Test
	public void createTagStatWithSuccess() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(tagStatService).saveTagStat(any(TagStat.class));
		TagStat tagStat = tagStats.get(0);
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagStatService.isTagStatUnique(anyLong(), anyLong(), anyInt())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = tagStatController.createTagStat(tagStat.getTagId(), tagStat, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagStatService, atLeastOnce()).isTagStatUnique(anyLong(), anyLong(), anyInt());
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagStatNotUniqueInTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateTagStatWithNotFound() {
		TagStat tagStat = tagStats.get(0);
		doNothing().when(tagStatService).updateTagStat(any(TagStat.class));
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagStatService.findById(anyLong())).thenReturn(null);
		ResponseEntity<TagStat> response;
		try {
			response = tagStatController.updateTagStat(tagStat.getTagId(), tagStat);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagStatService, atLeastOnce()).findById(anyLong());
		} catch (TagNotFoundException e) {

		} catch (TagStatNotFoundException e) {
	
		}
	}

	@Test
	public void updateTagStatWithSuccess() {
		TagStat tagStat = tagStats.get(0);
		doNothing().when(tagStatService).updateTagStat(any(TagStat.class));
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagStatService.findById(anyLong())).thenReturn(null);
		ResponseEntity<TagStat> response;
		try {
			response = tagStatController.updateTagStat(tagStat.getTagId(), tagStat);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagStatService, atLeastOnce()).findById(anyLong());
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagStatNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void deleteTagStatWithNotFound() {
		TagStat tagStat = tagStats.get(0);
		doNothing().when(tagStatService).deleteById(anyLong());
		when(tagStatService.findById(anyLong())).thenReturn(null);
		ResponseEntity<TagStat> response;
		try {
			response = tagStatController.deleteTagStat(tagStat.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		} catch (TagStatNotFoundException e) {

		}
	}

	@Test
	public void deleteTagStatWithSuccess() {
		TagStat tagStat = tagStats.get(0);
		doNothing().when(tagStatService).deleteById(anyLong());
		when(tagStatService.findById(anyLong())).thenReturn(tagStat);
		ResponseEntity<TagStat> response;
		try {
			response = tagStatController.deleteTagStat(tagStat.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		} catch (TagStatNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Tag getTagList() {
		Tag tag = new Tag();
		tag.setId(1L);
		tag.setSiteId(1L);
		tag.setTag("tag1");

		return tag;
	}

	public List<TagStat> getTagStatList() {
		Tag tag = new Tag();
		tag.setId(1L);
		tag.setSiteId(1L);
		tag.setTag("tag1");

		TagStat tagStat1 = new TagStat();
		tagStat1.setId(1L);
		tagStat1.setTagId(1L);
		tagStat1.setCrawlDate(20170101);

		TagStat tagStat2 = new TagStat();
		tagStat2.setId(2L);
		tagStat2.setTagId(1L);
		tagStat2.setCrawlDate(20170102);

		tagStats.add(tagStat1);
		tagStats.add(tagStat2);
		return tagStats;
	}
}
