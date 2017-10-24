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

import com.serpstat.restapi.dao.TagSearchVolumeDao;
import com.serpstat.restapi.model.TagSearchVolume;

public class TagSearchVolumeServiceImplTest {
	@Mock
	TagSearchVolumeDao dao;

	@InjectMocks
	TagSearchVolumeServiceImpl tagSearchVolumeService;

	@Spy
	List<TagSearchVolume> tagSearchVolumes = new ArrayList<TagSearchVolume>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		tagSearchVolumes = getTagSearchVolumeList();
	}

	@Test
	public void findById() {
		TagSearchVolume tagSearchVolume = tagSearchVolumes.get(0);
		when(dao.findById(anyLong())).thenReturn(tagSearchVolume);
		Assert.assertEquals(tagSearchVolumeService.findById(tagSearchVolume.getId()), tagSearchVolume);
	}

	@Test
	public void findAllByTagId() {
		when(dao.findAllByTagId(anyLong())).thenReturn(tagSearchVolumes);
		Assert.assertEquals(tagSearchVolumeService.findAllByTagId(tagSearchVolumes.get(0).getTagId()), tagSearchVolumes);
	}

	@Test
	public void findAllByTagIdInPeriod() {
		when(dao.findAllByTagIdInPeriod(anyLong(), anyInt(), anyInt())).thenReturn(tagSearchVolumes);
		Assert.assertEquals(tagSearchVolumeService.findAllByTagIdInPeriod(tagSearchVolumes.get(0).getTagId(), 20170101, 20170102), tagSearchVolumes);
	}

	@Test
	public void saveTagSearchVolume() {
		doNothing().when(dao).save(any(TagSearchVolume.class));
		tagSearchVolumeService.saveTagSearchVolume((TagSearchVolume) any(TagSearchVolume.class));
		verify(dao, atLeastOnce()).save(any(TagSearchVolume.class));
	}

	@Test
	public void updateTagSearchVolume() {
		TagSearchVolume tagSearchVolume = tagSearchVolumes.get(0);
		when(dao.findById(anyLong())).thenReturn(tagSearchVolume);
		tagSearchVolumeService.updateTagSearchVolume(tagSearchVolume);
		verify(dao, atLeastOnce()).findById(anyLong());
	}

	@Test
	public void deleteById() {
		doNothing().when(dao).deleteById(anyLong());
		tagSearchVolumeService.deleteById(anyLong());
		verify(dao, atLeastOnce()).deleteById(anyLong());
	}

	@Test
	public void isTagSearchVolumeUnique() {
		TagSearchVolume tagSearchVolume = tagSearchVolumes.get(0);
		when(dao.findById(anyLong())).thenReturn(tagSearchVolume);
		Assert.assertEquals(tagSearchVolumeService.isTagSearchVolumeUnique(tagSearchVolume.getId(), tagSearchVolume.getTagId(), tagSearchVolume.getCrawlDate()), true);
	}

	public List<TagSearchVolume> getTagSearchVolumeList() {
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
