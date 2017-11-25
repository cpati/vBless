package com.sjsu.backbenchers.vBless.service;

import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sjsu.backbenchers.vBless.batch.CampaignStatusBatch;
import com.sjsu.backbenchers.vBless.entity.Campaign;
import com.sjsu.backbenchers.vBless.entity.CampaignRepository;
import com.sjsu.backbenchers.vBless.entity.CampaignUser;
import com.sjsu.backbenchers.vBless.entity.CampaignUserRepository;
import com.sjsu.backbenchers.vBless.entity.FundDetails;
import com.sjsu.backbenchers.vBless.entity.FundDetailsRepository;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {
	private static final Logger log = LoggerFactory.getLogger(EmailService.class);
	
	@Value("${email.userName}")
	private String fromEmail;

	@Value("${email.password}")
	private String password;

	@Autowired
	private FundDetailsRepository fundDetailsRep;
	
	@Autowired
	private CampaignUserRepository campaignUserRepo;
	
	@Autowired
	private CampaignRepository campaignRepository;
	
	
	public void sendEmail(Long campaignId){
		System.out.println(fromEmail);
		System.out.println(password);
		try {
			// fetch campaign details
			Campaign campaign = campaignRepository.findByCampaignId(campaignId).get(0);

			List<FundDetails> fundDetails = fundDetailsRep.findByCampaignId(campaignId);

			// fetch total fund collected
			Long fundCollected = fundDetailsRep.findTotalFundRaised(campaignId);

			for (FundDetails fd : fundDetails) {
				CampaignUser cu = campaignUserRepo.findByUserId(fd.getUserId());

				System.out.println("TLSEmail Start");
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
				props.put("mail.smtp.port", "587"); // TLS Port
				props.put("mail.smtp.auth", "true"); // enable authentication
				props.put("mail.smtp.starttls.enable", "true"); // enable STARTTLS

				Authenticator auth = new Authenticator() {
					// override the getPasswordAuthentication method
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(fromEmail, password);
					}
				};
				Session session = Session.getInstance(props, auth);

				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(fromEmail));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(cu.getEmail()));
				String emailMessage = "vBless would like to thank you for the contribution you made for the campaign "
						+ campaign.getCampaignTitle() + ".\n\nWe have currently raised " + fundCollected + " Dollars of " + 
						campaign.getGoal() + " Dollars ."
						+ "  It will be great for you to share this campaign with family and friends.\n\n\n Thanks,\n" + 
						"Team vBless";

				String emailSubject = "Thanks for your contribution";
				message.setSubject(emailSubject);
				message.setText(emailMessage);

				Transport.send(message);
				log.info("Mail sent ");
				
			}

		} catch (Exception ex) {
			System.out.println(ex);
			log.error("Mail error " + ex.getMessage());
		}
	}
}
