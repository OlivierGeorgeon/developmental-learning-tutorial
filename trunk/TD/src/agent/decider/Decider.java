package agent.decider;

import coupling.Intention;
import coupling.Obtention;

/**
 * The Decider is the pro-active part of the Existence.
 * It receives the previous Obtention and chooses the next Intention through its method decideIntention(Obtention).
 * @author Olivier
 */
public interface Decider {

	/**
	 * @param obtention The previous obtention.
	 * @return The next intention.
	 */
	public Intention chooseIntention(Obtention obtention);

}
