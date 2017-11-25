package com.sjsu.backbenchers.vBless.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class FundDetails {
	
	
	@javax.persistence.Id
	@GeneratedValue
	private Long fundPaymentId;
	private Long campaignId;
	private String userId;
	private String amountPaid;
	private String paymentDate;	
	
		
	public FundDetails() {
		super();
	}
	
	public FundDetails(Long campaignId, String userId, String amountPaid, String paymentDate) {
		super();
		this.campaignId = campaignId;
		this.userId = userId;
		this.amountPaid = amountPaid;
		this.paymentDate = paymentDate;
	}


	public Long getFundPaymentId() {
		return fundPaymentId;
	}

	public void setFundPaymentId(Long fundPaymentId) {
		this.fundPaymentId = fundPaymentId;
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

	public String getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	
		
}
	
	