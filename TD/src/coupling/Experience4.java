package coupling;

import coupling.interaction.Interaction3;

public class Experience4 extends Experience {

	/**
	 * The experience's sub-interaction.
	 */
	private Interaction3 interaction;
	
	public Experience4(String label){
		super(label);
	}

	public boolean isPrimitive(){
		return (this.interaction == null);
	}
	
	public void setInteraction(Interaction3 interaction){
		this.interaction = interaction;
	}
	
	public Interaction3 getInteraction(){
		return this.interaction;
	}

}
