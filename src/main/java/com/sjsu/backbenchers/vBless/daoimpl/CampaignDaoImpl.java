package com.sjsu.backbenchers.vBless.daoimpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sjsu.backbenchers.vBless.dao.CampaignDao;
import com.sjsu.backbenchers.vBless.entity.Campaign;
import com.sjsu.backbenchers.vBless.hibernatecore.HibernateDao;

@Repository
@Transactional
public class CampaignDaoImpl extends HibernateDao<Campaign, Long> implements CampaignDao {


	@Override
	public boolean addCampaign(Campaign campaign) {
		super.add(campaign);
		return true;
	}

	@Override
	public Campaign getCampaign(Long id) {
		return super.find(id);
	}

	@Override
	public boolean updateCampaign(Campaign campaign) {
		super.update(campaign);
		return true;
	}

	

}
