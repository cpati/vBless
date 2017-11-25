package com.sjsu.backbenchers.vBless.rest;

import org.springframework.web.bind.annotation.RestController;

import com.sjsu.backbenchers.vBless.entity.CampaignUser;
import com.sjsu.backbenchers.vBless.entity.CampaignUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/vBless/")
public class vBlessController {
	
	@Autowired
	private CampaignUserRepository campaignUserRepository;
	
	
	@RequestMapping("/test")
	public String test() {
		return "Hello World!!!";
	}
	
	@RequestMapping("/getCampaignUser")
	public CampaignUser getCampaignUser(@PathVariable("userId") String userId) {
		return campaignUserRepository.findByUserId(userId);
	}
	
	@RequestMapping("/updateCampaignUser")
	public CampaignUser updateCampaignUser(@PathVariable("userId") String userId,
			@PathVariable("firstname") String firstname,
			@PathVariable("lastname") String lastname,
			@PathVariable("email") String email,
			@PathVariable("phone") String phone,
			@PathVariable("paymentinfo") String paymentinfo
			) {
		CampaignUser campaignUser = new CampaignUser(userId, firstname, lastname, email, phone, paymentinfo);
		return campaignUserRepository.save(campaignUser);
	}

}
