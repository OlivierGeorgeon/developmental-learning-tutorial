package coupling.interaction;

import coupling.Experience040;

/**
 * An interaction040 is an Interaction031 that has an Experience040
 * Composite interactions now have an abstract experience.
 */
public class Interaction040 extends Interaction032 {

	public Interaction040(String label){
		super(label);
	}
	
	@Override
	public Experience040 getExperience() {
		return (Experience040)super.getExperience(); 
	}
}
