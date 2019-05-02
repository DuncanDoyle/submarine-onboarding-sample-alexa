package org.jbpm.alexa.client.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jbpm.alexa.client.model.OnboardingServiceRequestResponseWrapper;

@Path("/onboarding")
@RegisterRestClient
public interface OnboardingService {

    @GET
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<OnboardingServiceRequestResponseWrapper> getAllOnboardings();

    @POST
    @Path("")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OnboardingServiceRequestResponseWrapper startNewOnboarding(OnboardingServiceRequestResponseWrapper request);

}