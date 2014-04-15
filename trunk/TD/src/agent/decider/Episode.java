package agent.decider;

import coupling.Result;

/**
 * An episode of interaction with the environment.
 * @author Olivier
 */
public interface Episode {

	/**
	 * Record the result obtained by enacting this episode.
	 * @param result The result of this episode of interaction.
	 */
	public void record(Result result);

}
