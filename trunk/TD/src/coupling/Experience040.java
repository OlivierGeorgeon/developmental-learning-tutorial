package coupling;

import coupling.interaction.Interaction040;

/**
 * An Experience040 is an Experience that can be primitive or abstract.
 * An abstract Experience has an intendedInteraction 
 * which is the sensorimotor pattern to try to enact if this experience is selected.
 */
public class Experience040 extends Experience {

	/**
	 * The experience's interaction.
	 */
	private Interaction040 intendedInteraction;
	private boolean isAbstract = true;
	
	public Experience040(String label){
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
