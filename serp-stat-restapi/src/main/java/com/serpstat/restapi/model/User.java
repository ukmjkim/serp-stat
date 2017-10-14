package com.serpstat.restapi.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="USERS")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="LOGIN", unique=true, nullable=false)
	private String login;
	
	@Column(name="PASSWORD", nullable=false)
	private String password;
	
	@Column(name="NICENAME", nullable=false)
	private String niceName;
	
	@Column(name="EMAIL", nullable=false)
	private String email;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_AT")
	private Date updatedAt = null;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DELETED_AT")
	private Date deletedAt = null;
	
	@Column(name="DELETED")
	private Integer deleted = 0;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private Set<UserAPI> userAPIs = new HashSet<UserAPI>();

	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private Set<Site> sites = new HashSet<Site>();


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getNiceName() {
		return niceName;
	}


	public void setNiceName(String niceName) {
		this.niceName = niceName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
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


	public Date getDeletedAt() {
		return deletedAt;
	}


	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}


	public Integer getDeleted() {
		return deleted;
	}


	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}


	public Set<UserAPI> getUserAPIs() {
		return userAPIs;
	}


	public void setUserAPIs(Set<UserAPI> userAPIs) {
		this.userAPIs = userAPIs;
	}


	public Set<Site> getSites() {
		return sites;
	}


	public void setSites(Set<Site> sites) {
		this.sites = sites;
	}

	/* Effective Java 2nd Edition, the reason to choose 31 is:
	 * 1. Because it's an odd prime, and it's "traditional" to use primes
	 * 2. It's also one less than a power of two, which permits for bitwise optimization
	 * 31 * i == (i << 5) - i
	 * Modern VMs do this sort of optimization automatically.
	 */
	@Override
	public int hashCode() {
		final int prime=31;
		int result = 1;
		
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		if (login == null) {
			if (other.login != null) 
				return false;
		} else if (!login.equals(other.login)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password
				+ ", nicename=" + niceName + ", email=" + email + "]";
	}
}
