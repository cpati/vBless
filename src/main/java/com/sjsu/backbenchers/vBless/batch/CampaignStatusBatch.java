package com.sjsu.backbenchers.vBless.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sjsu.backbenchers.vBless.entity.Campaign;
import com.sjsu.backbenchers.vBless.entity.CampaignRepository;
import com.sjsu.backbenchers.vBless.entity.CampaignUserRepository;
import com.sjsu.backbenchers.vBless.service.EmailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
public class CampaignStatusBatch{
		private static final Logger log = LoggerFactory.getLogger(CampaignStatusBatch.class);
		private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		@Autowired
		private CampaignRepository campaignRepository;
		
		@Autowired
		private EmailService emailService;
		
		
	    @Scheduled(fixedRate = 900000)
	    public void updateCampaignStatus() {
	        try {
				List<Campaign> campaigns = campaignRepository.findAll();
				Calendar cal1 = Calendar.getInstance();				
				Date todayDate = dateFormat.parse(dateFormat.format(new Date() ));
				
				log.info("campaign today's date : " + todayDate);
				
				for (Campaign c : campaigns) {
					if("Y".equals(c.getActive())) {
						Calendar cal2 = Calendar.getInstance();
						Date createDt = dateFormat.parse(dateFormat.format(c.getCreateDate()));
						
						cal2.setTime(createDt);
						cal2.add(Calendar.DATE,Integer.parseInt(c.getDuration())); 
						Date expDt = cal2.getTime();
						
						if((expDt.compareTo(todayDate)<=0)) {
							c.setActive("N");
							campaignRepository.save(c);
							log.info("campaign start date : " + createDt);
							log.info("campaign exp date : " + expDt);
							log.info("send mail to the users of this campaign");
							emailService.sendEmail(c.getCampaignId());
						}
					}
					
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
				e.printStackTrace();
			}
	        
	    }
}
