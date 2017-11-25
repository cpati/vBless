package com.sjsu.backbenchers.vBless.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class CampaignUser {
	
	@javax.persistence.Id
	private String userId;
	private String firstname;
	private String lastname;
	private String email;
	private String phone;
	private String paymentinfo;
	
	@OneToMany
	@JoinColumn(name="userId",referencedColumnName="userId")
	private List<Campaign> campaigns;
	
	public CampaignUser() {
		super();
	}
	
	public CampaignUser(String userId, String firstname, String lastname, String email, String phone, String paymentinfo) 
	{
		this.userId = userId;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.phone = phone;
		this.paymentinfo = paymentinfo;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPaymentinfo() {
		return paymentinfo;
	}
	public void setPaymentinfo(String paymentinfo) {
		this.paymentinfo = paymentinfo;
	}
	
	
}