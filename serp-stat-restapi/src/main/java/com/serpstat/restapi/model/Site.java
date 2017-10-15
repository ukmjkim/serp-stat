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
@Table(name="SITES")
public class Site {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="TITLE", nullable=false)
	private String title;
	
	@Column(name="URL", nullable=false)
	private String url;
	
	@Column(name="TRACKING", nullable=false)
	private Integer tracking = 1;
	
	@Column(name="DROP_WWW_PREFIX")
	private Integer dropWWWPrefix = 1;
	
	@Column(name="DROP_DIRECTORIES")
	private Integer dropDirectories = 0;;
	
	@Column(name="CONTACT_EMAIL")
	private String contactEmail = null;
	
	@Column(name="TREAT_NON_RANKING_AS")
	private Double treatNonRankingAs = null;
	
	@Column(name="NON_RANKING_VALUE")
	private Integer nonRankingValue = null;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getTracking() {
		return tracking;
	}

	public void setTracking(Integer tracking) {
		this.tracking = tracking;
	}

	public Integer getDropWWWPrefix() {
		return dropWWWPrefix;
	}

	public void setDropWWWPrefix(Integer dropWWWPrefix) {
		this.dropWWWPrefix = dropWWWPrefix;
	}

	public Integer getDropDirectories() {
		return dropDirectories;
	}

	public void setDropDirectories(Integer dropDirectories) {
		this.dropDirectories = dropDirectories;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public Double getTreatNonRankingAs() {
		return treatNonRankingAs;
	}

	public void setTreatNonRankingAs(Double treatNonRankingAs) {
		this.treatNonRankingAs = treatNonRankingAs;
	}

	public Integer getNonRankingValue() {
		return nonRankingValue;
	}

	public void setNonRankingValue(Integer nonRankingValue) {
		this.nonRankingValue = nonRankingValue;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Site))
			return false;
		
		Site other = (Site) obj;
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
		return "Site [id=" + id
				+ ", title=" + title
				+ ", url=" + url + ", tracking=" + tracking
				+ ", drop_www_prefix=" + dropWWWPrefix
				+ ", drop_directories=" + dropDirectories
				+ ", contact_email=" + contactEmail
				+ ", treat_non_ranking_as=" + treatNonRankingAs
				+ ", non_ranking_value=" + nonRankingValue
				+ "]";
	}
}
