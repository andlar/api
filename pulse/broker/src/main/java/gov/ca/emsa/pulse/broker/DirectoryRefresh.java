package gov.ca.emsa.pulse.broker;

import gov.ca.emsa.pulse.broker.domain.Organization;
import gov.ca.emsa.pulse.broker.manager.OrganizationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DirectoryRefresh {
	
	@Autowired private Environment env;
	
	@Autowired private OrganizationManager organizationManager;
	
	@Scheduled(fixedDelay=20000)
	public void getDirectories(){
		System.out.println("Updating the directories...");
		RestTemplate restTemplate = new RestTemplate();
		Organization[] orgs = restTemplate.getForObject(env.getProperty("mockBaseUrl") + "/directory", Organization[].class);
		organizationManager.updateOrganizations(orgs);
	}
}
