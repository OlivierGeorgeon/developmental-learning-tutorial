package Environments;

import coupling.Intention;
import coupling.Obtention;

/**
 * The Reality is the reactive part of the Existence.
 * It receives the previous Intention and provides the next Obtention through its method produceObtention(Intention). 
 * @author Olivier
 */
public interface Environment {

	/**
	 * @param intention The previous intention.
	 * @return The next obtention.
	 */
	public Obtention provideObtention(Intention intention);

}
