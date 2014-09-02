package reactive;

import coupling.Experience;
import coupling.Intention;
import coupling.Obtention;
import coupling.Result;

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
	
	public Result giveResult(Experience experience);


}
