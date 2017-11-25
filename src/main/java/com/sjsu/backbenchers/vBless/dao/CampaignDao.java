package com.sjsu.backbenchers.vBless.dao;

import com.sjsu.backbenchers.vBless.entity.Campaign;
import com.sjsu.backbenchers.vBless.hibernatecore.GenericDao;
//created three methods that user would be able to use, related to campaigns
//interface - contract between client and company
public interface CampaignDao extends GenericDao<Campaign, Long>{

	public boolean addCampaign(Campaign campaign);
	
	public Campaign getCampaign(Long id); //declared ; implementation in class
	
	public boolean updateCampaign(Campaign campaign);
}
