package coupling;

import coupling.interaction.Interaction040;

/**
 * An Experiment040 is an Experiment that can be primitive or abstract.
 * An abstract Experiment has an intendedInteraction 
 * which is the sensorimotor pattern to try to enact if this experiment is selected.
 */
public class Experiment040 extends Experiment {

	/**
	 * The experience's interaction.
	 */
	private Interaction040 intendedInteraction;
	private boolean isAbstract = true;
	
	public Experiment040(String label){
		super(label);
	}

	public boolean isAbstract(){
		return this.isAbstract;
	}
	
	public void resetAbstract(){
		this.isAbstract = false;
	}
	
	public void setIntendedInteraction(Interaction040 intendedInteraction){
		this.intendedInteraction = intendedInteraction;
	}
	
	public Interaction040 getIntendedInteraction(){
		return this.intendedInteraction;
	}

}
