package com.serpstat.restapi.model;

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
@Table(name = "SITE_DISTRIBS")
public class SiteDistrib {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "SITE_ID", nullable = false)
	private Long siteId;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSiteId() {
		return siteId;
	}

	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}

	public Integer getCrawlDate() {
		return crawlDate;
	}

	public void setCrawlDate(Integer crawlDate) {
		this.crawlDate = crawlDate;
	}

	public Integer getOne() {
		return one;
	}

	public void setOne(Integer one) {
		this.one = one;
	}

	public Integer getTwo() {
		return two;
	}

	public void setTwo(Integer two) {
		this.two = two;
	}

	public Integer getThree() {
		return three;
	}

	public void setThree(Integer three) {
		this.three = three;
	}

	public Integer getFour() {
		return four;
	}

	public void setFour(Integer four) {
		this.four = four;
	}

	public Integer getFive() {
		return five;
	}

	public void setFive(Integer five) {
		this.five = five;
	}

	public Integer getSixToTen() {
		return sixToTen;
	}

	public void setSixToTen(Integer sixToTen) {
		this.sixToTen = sixToTen;
	}

	public Integer getElevenToTwenty() {
		return elevenToTwenty;
	}

	public void setElevenToTwenty(Integer elevenToTwenty) {
		this.elevenToTwenty = elevenToTwenty;
	}

	public Integer getTwentyOneToThirty() {
		return twentyOneToThirty;
	}

	public void setTwentyOneToThirty(Integer twentyOneToThirty) {
		this.twentyOneToThirty = twentyOneToThirty;
	}

	public Integer getThirtyOneToForty() {
		return thirtyOneToForty;
	}

	public void setThirtyOneToForty(Integer thirtyOneToForty) {
		this.thirtyOneToForty = thirtyOneToForty;
	}

	public Integer getFortyOneToFifty() {
		return fortyOneToFifty;
	}

	public void setFortyOneToFifty(Integer fortyOneToFifty) {
		this.fortyOneToFifty = fortyOneToFifty;
	}

	public Integer getFiftyOneToHundred() {
		return fiftyOneToHundred;
	}

	public void setFiftyOneToHundred(Integer fiftyOneToHundred) {
		this.fiftyOneToHundred = fiftyOneToHundred;
	}

	public Integer getNonRanking() {
		return nonRanking;
	}

	public void setNonRanking(Integer nonRanking) {
		this.nonRanking = nonRanking;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((siteId == null) ? 0 : siteId.hashCode());
		result = prime * result + ((crawlDate == null) ? 0 : crawlDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SiteDistrib))
			return false;

		SiteDistrib other = (SiteDistrib) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}

		if (siteId == null) {
			if (other.siteId != null)
				return false;
		} else if (!siteId.equals(other.siteId)) {
			return false;
		}

		if (crawlDate == null) {
			if (other.crawlDate != null)
				return false;
		} else if (!crawlDate.equals(other.crawlDate)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "SiteDistrib [id=" + id + ", site_id=" + siteId + ", crawl_date=" + crawlDate + "]";
	}
}
