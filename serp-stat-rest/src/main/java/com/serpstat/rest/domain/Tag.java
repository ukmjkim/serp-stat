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

	public Tag(Long siteId, String tag, Integer reportTag, String filterRefresh, Integer backfillId) {
		super();
		this.siteId = siteId;
		this.tag = tag;
		this.reportTag = reportTag;
		this.filterRefresh = filterRefresh;
		this.backfillId = backfillId;
	}
}
