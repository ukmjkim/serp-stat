package com.serpstat.rest.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "TAG_SEARCH_VOLUMES")
public class TagSearchVolume {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TAG_ID", nullable = false)
	private Long tagId;

	@Column(name = "CRAWL_DATE", nullable = false)
	private Integer crawlDate;

	@Column(name = "GLOBAL_AGGREGATE_SV")
	private Long globalAggregateSV = null;

	@Column(name = "GLOBAL_AVERAGE_SV")
	private Double globalAverageSV = null;

	@Column(name = "REGIONAL_AGGREGATE_SV")
	private Long regionalAggregateSV = null;

	@Column(name = "REGIONAL_AVERAGE_SV")
	private Double regionalAverageSV = null;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT")
	private Date createdAt;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_AT")
	private Date updatedAt = null;

	public TagSearchVolume(Long tagId, Integer crawlDate, Long globalAggregateSV, Double globalAverageSV,
			Long regionalAggregateSV, Double regionalAverageSV) {
		super();
		this.tagId = tagId;
		this.crawlDate = crawlDate;
		this.globalAggregateSV = globalAggregateSV;
		this.globalAverageSV = globalAverageSV;
		this.regionalAggregateSV = regionalAggregateSV;
		this.regionalAverageSV = regionalAverageSV;
	}
}
