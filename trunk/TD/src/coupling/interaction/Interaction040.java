package coupling.interaction;

import coupling.Experiment040;

/**
 * An interaction040 is an Interaction031 that has an Experiment040
 * Composite interactions now have an abstract experiment.
 */
public class Interaction040 extends Interaction031 {

	public Interaction040(String label){
		super(label);
	}
	
	public Experiment040 getExperience() {
		return (Experiment040)super.getExperience(); 
	}
}
