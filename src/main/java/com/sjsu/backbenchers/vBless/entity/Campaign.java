package com.sjsu.backbenchers.vBless.entity;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Lob;

@Entity
public class Campaign {

	@javax.persistence.Id
	@GeneratedValue
	private Long campaignId;
	private String userId;
	private String campaignTitle;
	private String campaignDescription;
	private String blurb;
	
	@Lob @Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "LONGBLOB")
	private byte[] imageBlob;
	
	private String thumbnailUrl;
	private Date createDate;
	private Date suspendDate;
	private String category;
	private String duration;
	private String goal;
	private String city;
	private String country;
	private String active;

	public Campaign() {
		super();
	}

	public Campaign(Long campaignId, String userId, String campaignTitle, String campaignDescription, String blurb,
			byte[] imageBlob, String thumbnailUrl, Date createDate, Date suspendDate, String category, String duration,
			String goal, String city, String country, String active) {
		super();
		this.campaignId = campaignId;
		this.userId = userId;
		this.campaignTitle = campaignTitle;
		this.campaignDescription = campaignDescription;
		this.blurb = blurb;
		this.imageBlob = imageBlob;
		this.thumbnailUrl = thumbnailUrl;
		this.createDate = createDate;
		this.suspendDate = suspendDate;
		this.category = category;
		this.duration = duration;
		this.goal = goal;
		this.city = city;
		this.country = country;
		this.active = active;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCampaignTitle() {
		return campaignTitle;
	}

	public void setCampaignTitle(String campaignTitle) {
		this.campaignTitle = campaignTitle;
	}

	public String getCampaignDescription() {
		return campaignDescription;
	}

	public void setCampaignDescription(String campaignDescription) {
		this.campaignDescription = campaignDescription;
	}

	/**
	 * @return the blurb
	 */
	public String getBlurb() {
		return blurb;
	}

	/**
	 * @param blurb
	 *            the blurb to set
	 */
	public void setBlurb(String blurb) {
		this.blurb = blurb;
	}

	/**
	 * @return the imageBlob
	 */
	public byte[] getImageBlob() {
		return imageBlob;
	}

	/**
	 * @param imageBlob
	 *            the imageBlob to set
	 */
	public void setImageBlob(byte[] imageBlob) {
		this.imageBlob = imageBlob;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the suspendDate
	 */
	public Date getSuspendDate() {
		return suspendDate;
	}

	/**
	 * @param suspendDate
	 *            the suspendDate to set
	 */
	public void setSuspendDate(Date suspendDate) {
		this.suspendDate = suspendDate;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

}