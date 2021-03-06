package org.jbpm.alexa.skill;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jbpm.alexa.client.rest.OnboardingService;
import org.jbpm.alexa.skill.handler.CancelAndStopIntentHandler;
import org.jbpm.alexa.skill.handler.HelloWorldIntentHandler;
import org.jbpm.alexa.skill.handler.HelpIntentHandler;
import org.jbpm.alexa.skill.handler.LaunchRequestHandler;
import org.jbpm.alexa.skill.handler.SessionEndedRequestHandler;
import org.jbpm.alexa.skill.handler.submarine.GetAllOnboardingsIntentHandler;
import org.jbpm.alexa.skill.handler.submarine.NewOnboardingIntentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JbpmSkillFactory
 */
@ApplicationScoped
public class SubmarineSkillFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubmarineSkillFactory.class);
	
	@Inject
	private NewOnboardingIntentHandler newOnboardingIntentHandler;

	@Inject
	private GetAllOnboardingsIntentHandler getAllOnboardingsIntentHandler;

	public Skill getSkill() {

		return Skills.standard()
				.addRequestHandlers(
						new CancelAndStopIntentHandler(), 
						new HelloWorldIntentHandler(), 
						new HelpIntentHandler(),
						new LaunchRequestHandler(), 
						new SessionEndedRequestHandler(), 
						newOnboardingIntentHandler,
						getAllOnboardingsIntentHandler)

				// Add your skill id below

				// .withSkillId("")
				.build();

	}
}