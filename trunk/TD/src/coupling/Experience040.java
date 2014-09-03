package coupling;

import coupling.interaction.Interaction040;

/**
 * An Experience040 is an Experience that can be primitive or abstract.
 * An abstract Experience has an interaction attached to it.
 */
public class Experience040 extends Experience {

	/**
	 * The experience's interaction.
	 */
	private Interaction040 interaction;
	
	public Experience040(String label){
		super(label);
	}

	public boolean isAbstract(){
		return (this.interaction == null);
	}
	
	public void setInteraction(Interaction040 interaction){
		this.interaction = interaction;
	}
	
	public Interaction040 getInteraction(){
		return this.interaction;
	}

}
