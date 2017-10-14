package com.serpstat.restapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	
	@Column(name="key", unique=true, nullable=false)
	private String key;
	
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DELETED_AT")
	private Date deletedAt = null;
	
	@Column(name="DELETED")
	private Integer deleted = 0;

	@ManyToOne(optional = false)
	@JoinColumn(name="USER_ID")
	private User user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime=31;
		int result = 1;
		
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		
		UserAPI other = (UserAPI) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		if (key == null) {
			if (other.key != null) 
				return false;
		} else if (!key.equals(other.key)) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		return "UserAPI [id=" + id 
				+ ", user_id=" + user.getId() 
				+ ", key=" + ((key == null) ? "" : key)
				+ ", ips=" + ((ips == null) ? "" : ips)
				+ ", count=" + ((count == null) ? 0 : count)
				+ ", api_limit=" + ((apiLimit == null) ? 0 : apiLimit) + "]";
	}
}
