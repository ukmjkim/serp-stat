package com.serpstat.restapi.service.impl;

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
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.TagDao;
import com.serpstat.restapi.model.Tag;

public class TagServiceImplTest {
	@Mock
	TagDao dao;

	@InjectMocks
	TagServiceImpl tagService;

	@Spy
	List<Tag> tags = new ArrayList<Tag>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		tags = getTagList();
	}

	@Test
	public void findById() {
		Tag tag = tags.get(0);
		when(dao.findById(anyLong())).thenReturn(tag);
		Assert.assertEquals(tagService.findById(tag.getId()), tag);
	}

	@Test
	public void findAllBySiteId() {
		when(dao.findAllBySiteId(anyLong())).thenReturn(tags);
		Assert.assertEquals(tagService.findAllBySiteId(tags.get(0).getId()), tags);
	}

	@Test
	public void findAllSiteIdAndMatchedTag() {
		when(dao.findAllSiteIdAndMatchedTag(anyLong(), anyString())).thenReturn(tags);
		Assert.assertEquals(tagService.findAllSiteIdAndMatchedTag(1L, "tag"), tags);
	}

	@Test
	public void saveTag() {
		doNothing().when(dao).save(any(Tag.class));
		tagService.saveTag((Tag) any(Tag.class));
		verify(dao, atLeastOnce()).save(any(Tag.class));
	}

	@Test
	public void updateSite() {
		Tag site = tags.get(0);
		when(dao.findById(anyLong())).thenReturn(site);
		tagService.updateTag(site);
		verify(dao, atLeastOnce()).findById(anyLong());
	}

	@Test
	public void deleteById() {
		doNothing().when(dao).deleteById(anyLong());
		tagService.deleteById(anyLong());
		verify(dao, atLeastOnce()).deleteById(anyLong());
	}

	@Test
	public void isTagUnique() {
		Tag tag = tags.get(0);
		when(dao.findById(anyLong())).thenReturn(tag);
		Assert.assertEquals(tagService.isTagUnique(3L, 1L, "tag3"), true);
	}

	public List<Tag> getTagList() {
		List<Tag> tags = new ArrayList<Tag>();

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
