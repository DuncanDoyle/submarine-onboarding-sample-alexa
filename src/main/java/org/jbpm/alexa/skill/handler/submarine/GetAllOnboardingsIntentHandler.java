package org.jbpm.alexa.skill.handler.submarine;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.ui.PlainTextOutputSpeech;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jbpm.alexa.client.model.Employee;
import org.jbpm.alexa.client.model.OnboardingServiceRequestResponseWrapper;
import org.jbpm.alexa.client.rest.OnboardingService;
import org.jbpm.alexa.speech.GenericOutputSpeechFactory;
import org.jbpm.alexa.speech.OutputSpeechFactory;



/**
 * GetAllOnboardingsIntentHandler
 */
@ApplicationScoped
public class GetAllOnboardingsIntentHandler implements RequestHandler {

    private static final String GET_ALL_ONBOARDINGS_INTENT = "GetAllOnboardings";

    private static final int MAX_RESPONSE = 2;

    @Inject
    @RestClient
    private OnboardingService onboardingService;

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName(GET_ALL_ONBOARDINGS_INTENT));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

        List<OnboardingServiceRequestResponseWrapper> response = onboardingService.getAllOnboardings();        

        StringBuilder responseStringBuilder = new StringBuilder()
                                .append("There are " )
                                .append(response.size())
                                .append(" ongoing onboarding processes. ");
        
        //Filter out the the empty onboardings (seems we have some empty ones created during testing ...)
        List<OnboardingServiceRequestResponseWrapper> filteredResponse = response.stream().filter(o -> o.getEmployee() != null).collect(Collectors.toList());

        int maxResponses = (filteredResponse.size() > MAX_RESPONSE) ? MAX_RESPONSE : filteredResponse.size();
 
        if (maxResponses > 0) {
            responseStringBuilder.append("These are the first ")
                                    .append(maxResponses)
                                    .append(" persons being onboarded. ");
            
            for (int counter = 0; counter < maxResponses; counter++ ) {
                OnboardingServiceRequestResponseWrapper onboarding = filteredResponse.get(counter);
                Employee employee = filteredResponse.get(counter).getEmployee();
                responseStringBuilder.append(employee.getFirstName())
                                    .append(" ")
                                    .append(employee.getLastName());
                                    
                if (counter == maxResponses - 2) {
                    responseStringBuilder.append(", and ");
                } else if (counter == maxResponses - 1) {
                    responseStringBuilder.append(".");
                } else {
                    responseStringBuilder.append(", ");
                }
            }
        }

        OutputSpeechFactory<PlainTextOutputSpeech> outputSpeechFactory = new GenericOutputSpeechFactory(responseStringBuilder.toString());

        String speechText = outputSpeechFactory.getSpeechText();

        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(GET_ALL_ONBOARDINGS_INTENT, speechText)
                .build();
    }

}