package com.sjsu.backbenchers.vBless.entity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {
	List<Campaign> findByCampaignId(Long campaignId);
}