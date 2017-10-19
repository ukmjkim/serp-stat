package com.serpstat.restapi.controller;

import static org.mockito.Matchers.any;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.exception.TagNotFoundException;
import com.serpstat.restapi.exception.TagNotUniqueInSiteException;
import com.serpstat.restapi.model.Tag;
import com.serpstat.restapi.service.TagService;

public class TagRestControllerTest {
	@Mock
	TagService tagService;

	@Mock
	HttpHeaders headers;

	@InjectMocks
	TagRestController tagController;

	@Spy
	List<Tag> tags = new ArrayList<Tag>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		tags = getTagList();
	}

	@Test
	public void getUser() {
		Tag tag = tags.get(0);
		when(tagService.findById(anyLong())).thenReturn(tag);
		ResponseEntity<Tag> response;
		try {
			response = tagController.getTag(tag.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagService, atLeastOnce()).findById(anyLong());
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void createUserWithConflict() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(tagService).saveTag(any(Tag.class));
		when(tagService.isTagUnique(anyLong(), anyLong(), anyString())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = tagController.createTag(tags.get(0), ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
		} catch (TagNotUniqueInSiteException e) {

		}
	}

	@Test
	public void createTagWithSuccess() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(tagService).saveTag(any(Tag.class));
		when(tagService.isTagUnique(anyLong(), anyLong(), anyString())).thenReturn(true);
		ResponseEntity<Void> response;
		try {
			response = tagController.createTag(tags.get(0), ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		} catch (TagNotUniqueInSiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateTagWithNotFound() {
		Tag tag = tags.get(0);
		doNothing().when(tagService).updateTag(any(Tag.class));
		when(tagService.findById(anyLong())).thenReturn(null);
		ResponseEntity<Tag> response;
		try {
			response = tagController.updateTag(tag.getId(), tag);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		} catch (TagNotFoundException e) {

		}
	}

	@Test
	public void updateTagWithSuccess() {
		Tag tag = tags.get(0);
		doNothing().when(tagService).updateTag(any(Tag.class));
		when(tagService.findById(anyLong())).thenReturn(tag);
		ResponseEntity<Tag> response;
		try {
			response = tagController.updateTag(tag.getId(), tag);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void deleteTagWithNotFound() {
		Tag tag = tags.get(0);
		doNothing().when(tagService).deleteById(anyLong());
		when(tagService.findById(anyLong())).thenReturn(null);
		ResponseEntity<Tag> response;
		try {
			response = tagController.deleteTag(tag.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		} catch (TagNotFoundException e) {

		}
	}

	@Test
	public void deleteTagWithSuccess() {
		Tag tag = tags.get(0);
		doNothing().when(tagService).deleteById(anyLong());
		when(tagService.findById(anyLong())).thenReturn(tag);
		ResponseEntity<Tag> response;
		try {
			response = tagController.deleteTag(tag.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Tag> getTagList() {
		Tag tag1 = new Tag();
		tag1.setId(1L);
		tag1.setSiteId(1L);
		tag1.setTag("tag1");

		Tag tag2 = new Tag();
		tag2.setId(2L);
		tag2.setSiteId(1L);
		tag2.setTag("tag2");

		tags.add(tag1);
		tags.add(tag2);
		return tags;
	}
}
