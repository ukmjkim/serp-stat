package com.serpstat.rest.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "LOGIN", unique = true, nullable = false)
	private String login;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "NICENAME", nullable = false)
	private String niceName;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_AT")
	private Date createdAt = null;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATED_AT")
	private Date updatedAt = null;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELETED_AT")
	private Date deletedAt = null;

	@Column(name = "DELETED")
	private Integer deleted = 0;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserAPI> userAPIs = new HashSet<UserAPI>();

	public User(String login, String password, String niceName, String email) {
		super();
		this.login = login;
		this.password = password;
		this.niceName = niceName;
		this.email = email;
	}
}
