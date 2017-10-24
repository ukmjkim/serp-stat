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

import com.serpstat.restapi.dao.TagStatDao;
import com.serpstat.restapi.model.TagStat;

public class TagStatServiceImplTest {
	@Mock
	TagStatDao dao;

	@InjectMocks
	TagStatServiceImpl tagStatService;

	@Spy
	List<TagStat> tagStats = new ArrayList<TagStat>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		tagStats = getTagStatList();
	}

	@Test
	public void findById() {
		TagStat tagStat = tagStats.get(0);
		when(dao.findById(anyLong())).thenReturn(tagStat);
		Assert.assertEquals(tagStatService.findById(tagStat.getId()), tagStat);
	}

	@Test
	public void findAllByTagId() {
		when(dao.findAllByTagId(anyLong())).thenReturn(tagStats);
		Assert.assertEquals(tagStatService.findAllByTagId(tagStats.get(0).getTagId()), tagStats);
	}

	@Test
	public void findAllByTagIdInPeriod() {
		when(dao.findAllByTagIdInPeriod(anyLong(), anyInt(), anyInt())).thenReturn(tagStats);
		Assert.assertEquals(tagStatService.findAllByTagIdInPeriod(tagStats.get(0).getTagId(), 20170101, 20170102), tagStats);
	}

	@Test
	public void saveTagStat() {
		doNothing().when(dao).save(any(TagStat.class));
		tagStatService.saveTagStat((TagStat) any(TagStat.class));
		verify(dao, atLeastOnce()).save(any(TagStat.class));
	}

	@Test
	public void updateTagStat() {
		TagStat tagStat = tagStats.get(0);
		when(dao.findById(anyLong())).thenReturn(tagStat);
		tagStatService.updateTagStat(tagStat);
		verify(dao, atLeastOnce()).findById(anyLong());
	}

	@Test
	public void deleteById() {
		doNothing().when(dao).deleteById(anyLong());
		tagStatService.deleteById(anyLong());
		verify(dao, atLeastOnce()).deleteById(anyLong());
	}

	@Test
	public void isTagTitleUnique() {
		TagStat tagStat = tagStats.get(0);
		when(dao.findById(anyLong())).thenReturn(tagStat);
		Assert.assertEquals(tagStatService.isTagStatUnique(tagStat.getId(), tagStat.getTagId(), tagStat.getCrawlDate()), true);
	}

	public List<TagStat> getTagStatList() {
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
