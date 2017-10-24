package com.serpstat.restapi.service.impl;

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
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.TagDistribDao;
import com.serpstat.restapi.model.TagDistrib;

public class TagDistribServiceImplTest {
	@Mock
	TagDistribDao dao;

	@InjectMocks
	TagDistribServiceImpl tagDistribService;

	@Spy
	List<TagDistrib> tagDistribs = new ArrayList<TagDistrib>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		tagDistribs = getTagDistribList();
	}

	@Test
	public void findById() {
		TagDistrib tagDistrib = tagDistribs.get(0);
		when(dao.findById(anyLong())).thenReturn(tagDistrib);
		Assert.assertEquals(tagDistribService.findById(tagDistrib.getId()), tagDistrib);
	}

	@Test
	public void findAllByTagId() {
		when(dao.findAllByTagId(anyLong())).thenReturn(tagDistribs);
		Assert.assertEquals(tagDistribService.findAllByTagId(tagDistribs.get(0).getTagId()), tagDistribs);
	}

	@Test
	public void findAllByTagIdInPeriod() {
		when(dao.findAllByTagIdInPeriod(anyLong(), anyInt(), anyInt())).thenReturn(tagDistribs);
		Assert.assertEquals(tagDistribService.findAllByTagIdInPeriod(tagDistribs.get(0).getTagId(), 20170101, 20170102), tagDistribs);
	}

	@Test
	public void saveTagDistrib() {
		doNothing().when(dao).save(any(TagDistrib.class));
		tagDistribService.saveTagDistrib((TagDistrib) any(TagDistrib.class));
		verify(dao, atLeastOnce()).save(any(TagDistrib.class));
	}

	@Test
	public void updateTagDistrib() {
		TagDistrib tagDistrib = tagDistribs.get(0);
		when(dao.findById(anyLong())).thenReturn(tagDistrib);
		tagDistribService.updateTagDistrib(tagDistrib);
		verify(dao, atLeastOnce()).findById(anyLong());
	}

	@Test
	public void deleteById() {
		doNothing().when(dao).deleteById(anyLong());
		tagDistribService.deleteById(anyLong());
		verify(dao, atLeastOnce()).deleteById(anyLong());
	}

	@Test
	public void isTagDistribUnique() {
		TagDistrib tagDistrib = tagDistribs.get(0);
		when(dao.findById(anyLong())).thenReturn(tagDistrib);
		Assert.assertEquals(tagDistribService.isTagDistribUnique(tagDistrib.getId(), tagDistrib.getTagId(), tagDistrib.getCrawlDate()), true);
	}

	public List<TagDistrib> getTagDistribList() {
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
