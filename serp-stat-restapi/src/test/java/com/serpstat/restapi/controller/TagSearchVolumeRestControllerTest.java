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
import com.serpstat.restapi.exception.TagSearchVolumeNotFoundException;
import com.serpstat.restapi.exception.TagSearchVolumeNotUniqueInTagException;
import com.serpstat.restapi.exception.TagStatNotFoundException;
import com.serpstat.restapi.model.Tag;
import com.serpstat.restapi.model.TagSearchVolume;
import com.serpstat.restapi.model.TagStat;
import com.serpstat.restapi.service.TagService;
import com.serpstat.restapi.service.TagSearchVolumeService;

public class TagSearchVolumeRestControllerTest {
	@Mock
	TagService tagService;

	@Mock
	TagSearchVolumeService tagSearchVolumeService;

	@Mock
	HttpHeaders headers;

	@InjectMocks
	TagSearchVolumeRestController tagSearchVolumeController;

	@Spy
	Tag tag = new Tag();

	@Spy
	List<TagSearchVolume> tagSearchVolumes = new ArrayList<TagSearchVolume>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		tagSearchVolumes = getTagSearchVolumeList();
		tag = getTagList();
	}

	@Test
	public void getTagSearchVolume() {
		TagSearchVolume tagSearchVolume = tagSearchVolumes.get(0);
		when(tagSearchVolumeService.findById(anyLong())).thenReturn(tagSearchVolume);
		ResponseEntity<TagSearchVolume> response;
		try {
			response = tagSearchVolumeController.getTagSearchVolume(tagSearchVolume.getTagId(), tagSearchVolume.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagSearchVolumeService, atLeastOnce()).findById(anyLong());
		} catch (TagSearchVolumeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void listTagSearchVolume() {
		TagSearchVolume tagSearchVolume = tagSearchVolumes.get(0);
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagSearchVolumeService.findAllByTagIdInPeriod(anyLong(), anyInt(), anyInt())).thenReturn(tagSearchVolumes);
		ResponseEntity<List<TagSearchVolume>> response;
		try {
			response = tagSearchVolumeController.listTagSearchVolume(tagSearchVolume.getTagId(), tagSearchVolume.getCrawlDate(), tagSearchVolume.getCrawlDate());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagSearchVolumeService, atLeastOnce()).findAllByTagIdInPeriod(anyLong(), anyInt(), anyInt());
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void createTagSearchVolumeWithConflict() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(tagSearchVolumeService).saveTagSearchVolume(any(TagSearchVolume.class));
		TagSearchVolume tagSearchVolume = tagSearchVolumes.get(0);
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagSearchVolumeService.isTagSearchVolumeUnique(anyLong(), anyLong(), anyInt())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = tagSearchVolumeController.createTagSearchVolume(tagSearchVolume.getTagId(), tagSearchVolume, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagSearchVolumeService, atLeastOnce()).isTagSearchVolumeUnique(anyLong(), anyLong(), anyInt());
		} catch (TagNotFoundException e) {

		} catch (TagSearchVolumeNotUniqueInTagException e) {

		}
	}

	@Test
	public void createTagSearchVolumeWithSuccess() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(tagSearchVolumeService).saveTagSearchVolume(any(TagSearchVolume.class));
		TagSearchVolume tagSearchVolume = tagSearchVolumes.get(0);
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagSearchVolumeService.isTagSearchVolumeUnique(anyLong(), anyLong(), anyInt())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = tagSearchVolumeController.createTagSearchVolume(tagSearchVolume.getTagId(), tagSearchVolume, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagSearchVolumeService, atLeastOnce()).isTagSearchVolumeUnique(anyLong(), anyLong(), anyInt());
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagSearchVolumeNotUniqueInTagException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateTagSearchVolumeWithNotFound() {
		TagSearchVolume tagSearchVolume = tagSearchVolumes.get(0);
		doNothing().when(tagSearchVolumeService).updateTagSearchVolume(any(TagSearchVolume.class));
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagSearchVolumeService.findById(anyLong())).thenReturn(null);
		ResponseEntity<TagSearchVolume> response;
		try {
			response = tagSearchVolumeController.updateTagSearchVolume(tagSearchVolume.getTagId(), tagSearchVolume);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagSearchVolumeService, atLeastOnce()).findById(anyLong());
		} catch (TagNotFoundException e) {

		} catch (TagSearchVolumeNotFoundException e) {
	
		}
	}

	@Test
	public void updateTagSearchVolumeWithSuccess() {
		TagSearchVolume tagSearchVolume = tagSearchVolumes.get(0);
		doNothing().when(tagSearchVolumeService).updateTagSearchVolume(any(TagSearchVolume.class));
		when(tagService.findById(anyLong())).thenReturn(tag);
		when(tagSearchVolumeService.findById(anyLong())).thenReturn(null);
		ResponseEntity<TagSearchVolume> response;
		try {
			response = tagSearchVolumeController.updateTagSearchVolume(tagSearchVolume.getTagId(), tagSearchVolume);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(tagService, atLeastOnce()).findById(anyLong());
			verify(tagSearchVolumeService, atLeastOnce()).findById(anyLong());
		} catch (TagNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TagSearchVolumeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void deleteTagSearchVolumeWithNotFound() {
		TagSearchVolume tagSearchVolume = tagSearchVolumes.get(0);
		doNothing().when(tagSearchVolumeService).deleteById(anyLong());
		when(tagSearchVolumeService.findById(anyLong())).thenReturn(null);
		ResponseEntity<TagSearchVolume> response;
		try {
			response = tagSearchVolumeController.deleteTagSearchVolume(tagSearchVolume.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		} catch (TagSearchVolumeNotFoundException e) {

		}
	}

	@Test
	public void deleteTagSearchVolumeWithSuccess() {
		TagSearchVolume tagSearchVolume = tagSearchVolumes.get(0);
		doNothing().when(tagSearchVolumeService).deleteById(anyLong());
		when(tagSearchVolumeService.findById(anyLong())).thenReturn(tagSearchVolume);
		ResponseEntity<TagSearchVolume> response;
		try {
			response = tagSearchVolumeController.deleteTagSearchVolume(tagSearchVolume.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		} catch (TagSearchVolumeNotFoundException e) {
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

	public List<TagSearchVolume> getTagSearchVolumeList() {
		Tag tag = new Tag();
		tag.setId(1L);
		tag.setSiteId(1L);
		tag.setTag("tag1");

		TagSearchVolume tagSearchVolume1 = new TagSearchVolume();
		tagSearchVolume1.setId(1L);
		tagSearchVolume1.setTagId(1L);
		tagSearchVolume1.setCrawlDate(20170101);

		TagSearchVolume tagSearchVolume2 = new TagSearchVolume();
		tagSearchVolume2.setId(2L);
		tagSearchVolume2.setTagId(1L);
		tagSearchVolume2.setCrawlDate(20170102);

		tagSearchVolumes.add(tagSearchVolume1);
		tagSearchVolumes.add(tagSearchVolume2);
		return tagSearchVolumes;
	}
}
