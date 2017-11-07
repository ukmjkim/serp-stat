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
@Table(name = "SITES")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Site {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TITLE", nullable = false)
	private String title;

	@Column(name = "URL", nullable = false)
	private String url;

	@Column(name = "TRACKING", nullable = false)
	private Integer tracking = 1;

	@Column(name = "DROP_WWW_PREFIX")
	private Integer dropWWWPrefix = 1;

	@Column(name = "DROP_DIRECTORIES")
	private Integer dropDirectories = 0;

	@Column(name = "CONTACT_EMAIL")
	private String contactEmail = null;

	@Column(name = "TREAT_NON_RANKING_AS")
	private Double treatNonRankingAs = null;

	@Column(name = "NON_RANKING_VALUE")
	private Integer nonRankingValue = null;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT")
	private Date createdAt = null;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_AT")
	private Date updatedAt = null;

	@Column(name = "DELETED")
	private Integer deleted = 0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)
	@Getter(AccessLevel.NONE)
	private User user;

	public Site(String title, String url, Integer tracking, Integer dropWWWPrefix, Integer dropDirectories,
			String contactEmail, Double treatNonRankingAs, Integer nonRankingValue) {
		super();
		this.title = title;
		this.url = url;
		this.tracking = tracking;
		this.dropWWWPrefix = dropWWWPrefix;
		this.dropDirectories = dropDirectories;
		this.contactEmail = contactEmail;
		this.treatNonRankingAs = treatNonRankingAs;
		this.nonRankingValue = nonRankingValue;
	}

}
