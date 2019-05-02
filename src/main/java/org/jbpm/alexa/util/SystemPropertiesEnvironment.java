package org.jbpm.alexa.util;

public class SystemPropertiesEnvironment implements Environment {

	private static final String ONBOARDING_SERVICE_URL = System.getProperty("onboarding.service.url");
	
	@Override
	public String getOnboardingServiceUrl() {
		return ONBOARDING_SERVICE_URL;
	}

}
