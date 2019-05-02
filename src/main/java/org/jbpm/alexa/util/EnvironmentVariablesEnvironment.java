package org.jbpm.alexa.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentVariablesEnvironment implements Environment {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnvironmentVariablesEnvironment.class);
	 
	private static final String ONBOARDING_SERVICE_URL = System.getenv("ONBOARDING_SERVICE_URL");

	@Override
	public String getOnboardingServiceUrl() {
		return ONBOARDING_SERVICE_URL;
	}
	
}
