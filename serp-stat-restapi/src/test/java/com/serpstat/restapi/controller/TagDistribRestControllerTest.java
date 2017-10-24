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

import com.serpstat.restapi.exception.TagNotFoundException;
import com.serpstat.restapi.exception.TagDistribNotFoundException;
import com.serpstat.restapi.exception.TagDistribNotUniqueInTagException;
import com.serpstat.restapi.model.Tag;
import com.serpstat.restapi.model.TagDistrib;
import com.serpstat.restapi.service.TagService;
import com.serpstat.restapi.service.TagDistribService;

public class TagDistribRestControllerTest {
	@Mock
	TagService tagService;

	@Mock
	TagDistribService tagDistribService;

	@Mock
	HttpHeaders headers;

	@InjectMocks
	TagDistribRestController tagDistribController;

	@Spy
	Tag tag = new Tag();

	@Spy
	List<TagDistrib> tagDistribs = new ArrayList<TagDistrib>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		tagDistribs = getTagDistribList();
		tag = getTagList();
	}

	@Test
	public void getTagDistrib() {
		TagDistrib tagDistrib = tagDistribs.get(0);
		when(tagDistribService.findById(anyLong())).thenReturn(tagDistrib);
		ResponseEntity<TagDistrib> response;
		try {
			response = tagDistribController.getTagDistrib(tagDistrib.getTagId(), tagDistrib.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagDistribService, atLeastOnce()).findById(anyLong());
		} catch (TagDistribNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void listTagDistrib() {
		TagDistrib tagDistrib = tagDistribs.get(0);
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagDistribService.findAllByTagIdInPeriod(anyLong(), anyInt(), anyInt())).thenReturn(tagDistribs);
		ResponseEntity<List<TagDistrib>> response;
		try {
			response = tagDistribController.listTagDistrib(tagDistrib.getTagId(), tagDistrib.getCrawlDate(), tagDistrib.getCrawlDate());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagDistribService, atLeastOnce()).findAllByTagIdInPeriod(anyLong(), anyInt(), anyInt());
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void createTagDistribWithConflict() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(tagDistribService).saveTagDistrib(any(TagDistrib.class));
		TagDistrib tagDistrib = tagDistribs.get(0);
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagDistribService.isTagDistribUnique(anyLong(), anyLong(), anyInt())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = tagDistribController.createTagDistrib(tagDistrib.getTagId(), tagDistrib, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagDistribService, atLeastOnce()).isTagDistribUnique(anyLong(), anyLong(), anyInt());
		} catch (TagNotFoundException e) {

		} catch (TagDistribNotUniqueInTagException e) {

		}
	}

	@Test
	public void createTagDistribWithSuccess() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(tagDistribService).saveTagDistrib(any(TagDistrib.class));
		TagDistrib tagDistrib = tagDistribs.get(0);
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagDistribService.isTagDistribUnique(anyLong(), anyLong(), anyInt())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = tagDistribController.createTagDistrib(tagDistrib.getTagId(), tagDistrib, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagDistribService, atLeastOnce()).isTagDistribUnique(anyLong(), anyLong(), anyInt());
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagDistribNotUniqueInTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateTagDistribWithNotFound() {
		TagDistrib tagDistrib = tagDistribs.get(0);
		doNothing().when(tagDistribService).updateTagDistrib(any(TagDistrib.class));
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagDistribService.findById(anyLong())).thenReturn(null);
		ResponseEntity<TagDistrib> response;
		try {
			response = tagDistribController.updateTagDistrib(tagDistrib.getTagId(), tagDistrib.getId(), tagDistrib);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagDistribService, atLeastOnce()).findById(anyLong());
		} catch (TagNotFoundException e) {

		} catch (TagDistribNotFoundException e) {
	
		}
	}

	@Test
	public void updateTagDistribWithSuccess() {
		TagDistrib tagDistrib = tagDistribs.get(0);
		doNothing().when(tagDistribService).updateTagDistrib(any(TagDistrib.class));
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagDistribService.findById(anyLong())).thenReturn(null);
		ResponseEntity<TagDistrib> response;
		try {
			response = tagDistribController.updateTagDistrib(tagDistrib.getTagId(), tagDistrib.getId(), tagDistrib);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagDistribService, atLeastOnce()).findById(anyLong());
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagDistribNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void deleteTagDistribWithNotFound() {
		TagDistrib tagDistrib = tagDistribs.get(0);
		doNothing().when(tagDistribService).deleteById(anyLong());
		when(tagDistribService.findById(anyLong())).thenReturn(null);
		ResponseEntity<TagDistrib> response;
		try {
			response = tagDistribController.deleteTagDistrib(tagDistrib.getTagId(), tagDistrib.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		} catch (TagDistribNotFoundException e) {

		}
	}

	@Test
	public void deleteTagDistribWithSuccess() {
		TagDistrib tagDistrib = tagDistribs.get(0);
		doNothing().when(tagDistribService).deleteById(anyLong());
		when(tagDistribService.findById(anyLong())).thenReturn(tagDistrib);
		ResponseEntity<TagDistrib> response;
		try {
			response = tagDistribController.deleteTagDistrib(tagDistrib.getTagId(), tagDistrib.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		} catch (TagDistribNotFoundException e) {
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

	public List<TagDistrib> getTagDistribList() {
		Tag tag = new Tag();
		tag.setId(1L);
		tag.setSiteId(1L);
		tag.setTag("tag1");

		TagDistrib tagDistrib1 = new TagDistrib();
		tagDistrib1.setId(1L);
		tagDistrib1.setTagId(1L);
		tagDistrib1.setCrawlDate(20170101);

		TagDistrib tagDistrib2 = new TagDistrib();
		tagDistrib2.setId(2L);
		tagDistrib2.setTagId(1L);
		tagDistrib2.setCrawlDate(20170102);

		tagDistribs.add(tagDistrib1);
		tagDistribs.add(tagDistrib2);
		return tagDistribs;
	}
}
