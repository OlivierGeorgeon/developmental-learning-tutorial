package coupling.interaction;

import coupling.Experience4;

/**
 * An interaction040 is an Interaction031 that works with Experience040
 * Composite interactions now have an abstract experience.
 */
public class Interaction040 extends Interaction031 {

	public Interaction040(String label){
		super(label);
	}
	
	public Experience4 getExperience() {
		return (Experience4)super.getExperience(); 
	}
}
