package coupling.interaction;

import coupling.Experience040;

/**
 * An interaction040 is an Interaction031 that works with Experience040
 * Composite interactions now have an abstract experience.
 */
public class Interaction040 extends Interaction031 {

	public Interaction040(String label){
		super(label);
	}
	
	public Experience040 getExperience() {
		return (Experience040)super.getExperience(); 
	}
}
