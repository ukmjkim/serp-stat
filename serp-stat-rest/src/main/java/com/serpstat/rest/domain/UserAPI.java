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
@Table(name = "USER_APIS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class UserAPI {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "API_key", unique = true, nullable = false)
	private String apiKey;

	@Column(name = "IPS")
	private String ips;

	@Column(name = "COUNT")
	private Integer count = 0;

	@Column(name = "API_LIMIT")
	private Integer apiLimit = 0;

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

	public UserAPI(String apiKey, String ips, Integer count, Integer apiLimit) {
		super();
		this.apiKey = apiKey;
		this.ips = ips;
		this.count = count;
		this.apiLimit = apiLimit;
	}
}
