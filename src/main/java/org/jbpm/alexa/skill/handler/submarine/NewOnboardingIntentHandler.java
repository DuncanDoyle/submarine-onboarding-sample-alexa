package org.jbpm.alexa.skill.handler.submarine;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.IntentRequest;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.model.ui.PlainTextOutputSpeech;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jbpm.alexa.client.model.Address;
import org.jbpm.alexa.client.model.Employee;
import org.jbpm.alexa.client.model.OnboardingServiceRequestResponseWrapper;
import org.jbpm.alexa.client.rest.OnboardingService;
import org.jbpm.alexa.speech.GenericOutputSpeechFactory;
import org.jbpm.alexa.speech.OutputSpeechFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NewOnboardingIntentHandler
 */
@ApplicationScoped
public class NewOnboardingIntentHandler implements RequestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(NewOnboardingIntentHandler.class);

	private static final String NEW_ONBOARDING_INTENT = "NewOnboarding";

	private static final String PERSON_SLOT = "Person";

	@Inject
	@RestClient
	private OnboardingService onboardingService;
	
	@Override
	public boolean canHandle(HandlerInput input) {
		return input.matches(intentName(NEW_ONBOARDING_INTENT));
	}

	@Override
	public Optional<Response> handle(HandlerInput input) {
		OutputSpeechFactory<PlainTextOutputSpeech> osFactory = null;
		IntentRequest intentRequest = (IntentRequest) input.getRequest();
		Map<String, Slot> slots = intentRequest.getIntent().getSlots();
		Slot personSlot = slots.get(PERSON_SLOT);
		
		LOGGER.info("Slot-value is: " + personSlot.getValue());
		
		if (personSlot != null) {
			String fullName = personSlot.getValue();
			String firstName = fullName.substring(0, fullName.indexOf(' '));
			String lastName = fullName.substring(fullName.indexOf(' ') + 1);
			try {
				if (onboardingService == null) {
					LOGGER.info("Huh, service does not exist!");	
				}
				OnboardingServiceRequestResponseWrapper response = onboardingService.startNewOnboarding(buildRequestResponseWrapper(buildEmployee(firstName, lastName)));				
				String responseString = new StringBuilder()
											.append("New onboarding process started for employee ")
											.append(response.getEmployee().getFirstName())
											.append(" ")
											.append(response.getEmployee().getLastName())
											.append(". ")
											.append("The assigned department is: ")
											.append(response.getDepartment())
											.append(". ")
											.append("The assigned manager is: ")
											.append(response.getManager())
											.toString();
											
				osFactory = new GenericOutputSpeechFactory(responseString);
			} catch (Exception e) {
				LOGGER.error("Error while calling service.", e);
				osFactory = new GenericOutputSpeechFactory("Unable to start new onboarding proces for employee with name " + firstName + " " + lastName);
			}
		} else {
			// We can't do much without the name of a person.
			// TODO: we might improve this by asking for the name again.
			osFactory = new GenericOutputSpeechFactory("Can't start a new onboarding process. I didn't hear the name of the employee");
		}

		String speechText = osFactory.getSpeechText();

		return input.getResponseBuilder()
						.withSpeech(speechText)
						.withSimpleCard("New Onboarding", speechText)
						.build();
	}

	private OnboardingServiceRequestResponseWrapper buildRequestResponseWrapper(Employee employee) {
		OnboardingServiceRequestResponseWrapper wrapper = new OnboardingServiceRequestResponseWrapper();
		wrapper.setEmployee(employee);
		return wrapper;
	}

	private Employee buildEmployee(String firstName, String lastName) {
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setBirthDate(new Date());
		employee.setPersonalId(String.valueOf(Math.floor(Math.random() * 1000)));
		employee.setAddress(buildAddress());
		return employee;
	}

	private Address buildAddress() {
		Address address = new Address();
		address.setCity("Boston");
		address.setStreet("A Street");
		address.setCountry("US");
		address.setZipCode("02210");
		return address;
	}
}   