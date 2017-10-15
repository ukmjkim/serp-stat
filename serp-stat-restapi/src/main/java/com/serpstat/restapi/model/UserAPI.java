package com.serpstat.restapi.model;

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

@Entity
@Table(name="USER_APIS")
public class UserAPI {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="API_key", unique=true, nullable=false)
	private String apiKey;
	
	@Column(name="IPS")
	private String ips;
	
	@Column(name="COUNT")
	private Integer count = 0;
	
	@Column(name="API_LIMIT")
	private Integer apiLimit = 0;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="UPDATED_AT")
	private Date updatedAt = null;
	
	@Column(name="DELETED")
	private Integer deleted = 0;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USER_ID", nullable = false)
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getIps() {
		return ips;
	}

	public void setIps(String ips) {
		this.ips = ips;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getApiLimit() {
		return apiLimit;
	}

	public void setApiLimit(Integer apiLimit) {
		this.apiLimit = apiLimit;
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

	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public int hashCode() {
		final int prime=31;
		int result = 1;
		
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((apiKey == null) ? 0 : apiKey.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserAPI))
			return false;
		
		UserAPI other = (UserAPI) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		if (apiKey == null) {
			if (other.apiKey != null) 
				return false;
		} else if (!apiKey.equals(other.apiKey)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return "UserAPI [id=" + id 
				+ ", api_key=" + ((apiKey == null) ? "" : apiKey)
				+ ", ips=" + ((ips == null) ? "" : ips)
				+ ", count=" + ((count == null) ? 0 : count)
				+ ", api_limit=" + ((apiLimit == null) ? 0 : apiLimit) + "]";
	}
}
