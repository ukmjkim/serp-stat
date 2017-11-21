package com.serpstat.rest.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "KEYWORDS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Keyword {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "SITE_ID", nullable = false)
	private Long siteId;

	@Column(name = "KEYWORD", nullable = false)
	private String keyword;

	@Column(name = "TRANSLATION")
	private String translation = "";

	@Column(name = "CHECKSUM")
	private String checksum = "";

	@Column(name = "TRACKING")
	private Integer tracking = 1;

	@Column(name = "LOCATION", nullable = false)
	private String location = "";

	@Column(name = "DELETED", nullable = false)
	private Integer deleted = 0;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DEVICE_ID", nullable = false)
	private Device device;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MARKET_ID", nullable = false)
	private Market market;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT")
	private Date createdAt;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_AT")
	private Date updatedAt = null;

	public Keyword(Long siteId, String keyword, String translation, String checksum, Integer tracking, String location,
			Device device, Market market) {
		super();
		this.siteId = siteId;
		this.keyword = keyword;
		this.translation = translation;
		this.checksum = checksum;
		this.tracking = tracking;
		this.location = location;
		this.device = device;
		this.market = market;
	}
}
