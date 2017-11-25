package com.sjsu.backbenchers.vBless.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.crypto.Data;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sjsu.backbenchers.vBless.daoimpl.CampaignDaoImpl;
import com.sjsu.backbenchers.vBless.entity.Campaign;
import com.sjsu.backbenchers.vBless.entity.CampaignRepository;

@RestController
@RequestMapping("/createCampaign/")//this is url
public class CampaignController {

	@Autowired
	private CampaignDaoImpl CampaignDaoImpl;
	
	@Autowired
	private CampaignRepository campaignRepository;

	private Campaign newCampaign; //declaration of object
//requesting the values from Angular page
	@RequestMapping(value = "/save")
	public boolean insertCampaign(@RequestParam(value = "projectTitle", required = true) String projectTitle,
			@RequestParam(value = "initDesc", required = true) String initiativeDesc,
			@RequestParam(value = "shortBlurb", required = true) String shortBlurb,
			@RequestParam(value = "category", required = true) String category,
			@RequestParam(value = "fGoal") String goal, @RequestParam(value = "city") String city,
			@RequestParam(value = "campDurDay") String campDurDay, @RequestParam(value = "country") String country,
			@RequestParam(value = "createdDate") String createdDate,@RequestParam(value = "type") String type) throws Exception {
		//checks if the campaign is new then creates new campaign if no values already present
		if (newCampaign == null) {
			newCampaign = new Campaign(); //definition of object
		}
		//set all values
		newCampaign.setCampaignTitle(projectTitle);
		newCampaign.setCampaignDescription(initiativeDesc);
		newCampaign.setBlurb(shortBlurb);
		newCampaign.setCategory(category);
		newCampaign.setCountry(country);
		newCampaign.setCity(city);
//if the value is not null or undeined or empty , then it has some value which should be numeric and then set it to fundgoals
		if (goal != null && !goal.isEmpty() && !goal.equalsIgnoreCase("undefined")) {
			if (NumberUtils.isNumber(goal)) {
				newCampaign.setGoal(goal);
			} else {
				newCampaign=null;
				throw new Exception("Campaign Funding goal Must be Numeric");//if not numeric value
			}
		}
		//if new campaign carete page > it will directly set the campaign duration value(numeric, >0, non empty)
		//else it checks if the previous value f 'x' and new value not equal to x, then set the filed else not aloow to save
		if (campDurDay != null && !campDurDay.isEmpty() && !campDurDay.equalsIgnoreCase("undefined")) {
			if (StringUtils.isNumeric(campDurDay)) {
				if(Integer.valueOf(campDurDay)>0) {
				if (newCampaign.getDuration() == null || !newCampaign.getDuration().equals(campDurDay))
					newCampaign.setDuration(campDurDay);
				}else {
					newCampaign=null;
					throw new Exception("Campaign Duration Must be Greater than 0");
				}
			} else {
				newCampaign=null;
				throw new Exception("Campaign Duration Must be Numeric");
			}
		} else {
			newCampaign=null;
			throw new Exception("Campaign Duration Cannot be blank");

		}
//check if newly created campaign date is = null/empty, set it to current system date.
//checks if campaign suspended date = todays date 
		//suspended date = created data + duration
		Calendar calendar = Calendar.getInstance();
		if (newCampaign.getCreateDate() == null) {
			Date date = new Date(new java.util.Date(createdDate).getTime());

			newCampaign.setCreateDate(date);//system date and time as parameter to setter method
			calendar.setTime(new java.util.Date(createdDate));
		} else {
			calendar.setTime(newCampaign.getCreateDate());
		}

		calendar.add(Calendar.DATE, Integer.valueOf(newCampaign.getDuration()));
		newCampaign.setSuspendDate(new Date(calendar.getTime().getTime()));

		if (new java.util.Date().equals(newCampaign.getSuspendDate())) {
			newCampaign.setActive("N");
		} else {
			newCampaign.setActive("Y");
		}

		if (type.equalsIgnoreCase("Add")) {
			CampaignDaoImpl.add(newCampaign);
		} else {
			CampaignDaoImpl.update(newCampaign); //
		}
		newCampaign = null;
		return true;
	}
//method to upload an image file 
	@RequestMapping(value = "/uploadPhoto", method = RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	public Data uploadPhoto(HttpServletRequest request, HttpServletResponse response) {
		if (newCampaign == null) {
			newCampaign = new Campaign();
		}
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
		MultipartFile mFile = mRequest.getFile(mRequest.getFileNames().next());
		try {
			InputStream is = mFile.getInputStream();
			newCampaign.setImageBlob(IOUtils.toByteArray(is));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
//to read the campaign by id from DB ; Hybernate generated the ids
	//this method has to called from campaign list page to load the data for that particular campaign (by ID)
	@RequestMapping("/getCamapignById/{campaignId}")
	public Campaign getCamapignById(@PathVariable("campaignId") Long campaignId) {
		newCampaign = CampaignDaoImpl.getCampaign(campaignId);
		if (new Date(new java.util.Date().getTime()).equals(newCampaign.getSuspendDate()) ||
				new Date(new java.util.Date().getTime()).after(newCampaign.getSuspendDate())) {
			newCampaign.setActive("N");
		} else {
			newCampaign.setActive("Y");
		}
		CampaignDaoImpl.update(newCampaign); //
		return newCampaign;

	}
	
	@RequestMapping("top")
	public List<Campaign> getTopCampaigns(){
		return campaignRepository.findAll();
	}

}
