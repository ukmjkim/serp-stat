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
@Table(name = "TAG_DISTRIBS")
public class TagDistrib {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TAG_ID", nullable = false)
	private Long tagId;

	@Column(name = "CRAWL_DATE", nullable = false)
	private Integer crawlDate;

	@Column(name = "ONE")
	private Integer one = null;

	@Column(name = "TWO")
	private Integer two = null;

	@Column(name = "THREE")
	private Integer three = null;

	@Column(name = "FOUR")
	private Integer four = null;

	@Column(name = "FIVE")
	private Integer five = null;

	@Column(name = "SIX_TO_TEN")
	private Integer sixToTen = null;

	@Column(name = "ELEVEN_TO_TWENTY")
	private Integer elevenToTwenty = null;

	@Column(name = "TWENTY_ONE_TO_THIRTY")
	private Integer twentyOneToThirty = null;

	@Column(name = "THIRTY_ONE_TO_FORTY")
	private Integer thirtyOneToForty = null;

	@Column(name = "FORTY_ONE_TO_FIFTY")
	private Integer fortyOneToFifty = null;

	@Column(name = "FIFTY_ONE_TO_HUNDRED")
	private Integer fiftyOneToHundred = null;

	@Column(name = "NON_RANKING")
	private Integer nonRanking = null;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT")
	private Date createdAt;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_AT")
	private Date updatedAt = null;

	public TagDistrib(Long tagId, Integer crawlDate, Integer one, Integer two, Integer three, Integer four,
			Integer five, Integer sixToTen, Integer elevenToTwenty, Integer twentyOneToThirty, Integer thirtyOneToForty,
			Integer fortyOneToFifty, Integer fiftyOneToHundred, Integer nonRanking) {
		super();
		this.tagId = tagId;
		this.crawlDate = crawlDate;
		this.one = one;
		this.two = two;
		this.three = three;
		this.four = four;
		this.five = five;
		this.sixToTen = sixToTen;
		this.elevenToTwenty = elevenToTwenty;
		this.twentyOneToThirty = twentyOneToThirty;
		this.thirtyOneToForty = thirtyOneToForty;
		this.fortyOneToFifty = fortyOneToFifty;
		this.fiftyOneToHundred = fiftyOneToHundred;
		this.nonRanking = nonRanking;
	}

}
