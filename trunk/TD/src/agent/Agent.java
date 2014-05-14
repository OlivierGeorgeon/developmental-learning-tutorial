package agent;

import coupling.Experience;
import coupling.Result;

/**
 * An agent that can choose an experience to perform in the environment and receive a result.
 * @author Olivier
 */
public interface Agent {

	/**
	 * @param result The result of the previous experience.
	 * @return The next experience to perform in the environment.
	 */
	public Experience chooseExperience(Result result);

}
