package com.sjsu.backbenchers.vBless.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FundDetailsRepository extends JpaRepository<FundDetails, Long> {
	List<FundDetails> findByFundPaymentId(Long fundPaymentId);
	
	List<FundDetails> findByCampaignId(Long campaignId);
	
	 @Query("SELECT sum(amountPaid) FROM FundDetails where campaignId =?")
	 public Long findTotalFundRaised(Long campaignId);
}