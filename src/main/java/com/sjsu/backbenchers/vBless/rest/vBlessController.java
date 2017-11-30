package com.sjsu.backbenchers.vBless.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjsu.backbenchers.vBless.entity.CampaignUser;
import com.sjsu.backbenchers.vBless.entity.CampaignUserRepository;

@RestController
@RequestMapping("/vBless/")
public class vBlessController {
	
	@Autowired
	private CampaignUserRepository campaignUserRepository;
	
	@RequestMapping("/user")
	public Principal user(Principal principal) {
	    return principal;
	}
	
	@RequestMapping("/test")
	public String test() {
		return "Hello World!!!";
	}
	
	@RequestMapping("/getCampaignUser/{userId}")
	public CampaignUser getCampaignUser(@PathVariable("userId") String userId) {
		return campaignUserRepository.findByUserId(userId);
	}
	
	@RequestMapping("/updateCampaignUser")
	public CampaignUser updateCampaignUser(@RequestParam(value="userId", required=true) String userId,
			@RequestParam(value="firstname", required=true) String firstname,
			@RequestParam(value="lastname", required=true) String lastname,
			@RequestParam(value="email", required=true) String email,
			@RequestParam(value="phone", required=true) String phone,
			@RequestParam(value="paymentinfo", required=true) String paymentinfo
			) {
		CampaignUser campaignUser = new CampaignUser(userId, firstname, lastname, email, phone, paymentinfo);
		return campaignUserRepository.save(campaignUser);
	}

}
