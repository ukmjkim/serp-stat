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
@Table(name = "TAGS")
public class Tag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "SITE_ID", nullable = false)
	private Long siteId;

	@Column(name = "TAG", nullable = false)
	private String tag;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT")
	private Date createdAt;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_AT")
	private Date updatedAt = null;

	@Column(name = "DELETED")
	private Integer deleted = 0;

	@Column(name = "REPORT_TAG")
	private Integer reportTag = 0;

	@Column(name = "FILTER_REFRESH")
	private String filterRefresh = null;

	@Column(name = "BACKFILL_ID")
	private Integer backfillId = null;

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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
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

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Integer getReportTag() {
		return reportTag;
	}

	public void setReportTag(Integer reportTag) {
		this.reportTag = reportTag;
	}

	public String getFilterRefresh() {
		return filterRefresh;
	}

	public void setFilterRefresh(String filterRefresh) {
		this.filterRefresh = filterRefresh;
	}

	public Integer getBackfillId() {
		return backfillId;
	}

	public void setBackfillId(Integer backfillId) {
		this.backfillId = backfillId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tag))
			return false;

		Tag other = (Tag) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", site_id=" + siteId + ", tag=" + tag + ", report_tag=" + reportTag + ", filter_refresh="
				+ filterRefresh + ", backfill_id=" + backfillId + ", deleted=" + deleted + "]";
	}
}
