package org.jbpm.alexa;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazon.ask.servlet.SkillServlet;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.graalvm.nativeimage.Feature.OnAnalysisExitAccess;
import org.jbpm.alexa.client.rest.OnboardingService;
import org.jbpm.alexa.skill.SubmarineSkillFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main servlet for the Submarine Onboarding Sample Alexa interface.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@WebServlet("/speech")
public class SubmarineAlexaSkillServlet extends SkillServlet {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubmarineAlexaSkillServlet.class);

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	public SubmarineAlexaSkillServlet(SubmarineSkillFactory skillFactory) {
		super(skillFactory.getSkill());
		LOGGER.info("Bootstrapping Submarine Alexa Skill.");
	}

	/**
	 * Just overriding to do some logging.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		super.doPost(request, response);
	}

}
