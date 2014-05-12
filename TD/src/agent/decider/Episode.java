package agent.decider;

import coupling.Result;
import coupling.interaction.Interaction;

/**
 * An episode of interaction with the environment.
 * @author Olivier
 */
public interface Episode<I extends Interaction> {

	/**
	 * Record the result obtained by enacting this episode.
	 * @param result The result of this episode of interaction.
	 */
	public void record(Result result);

	public I getInteraction();

}
