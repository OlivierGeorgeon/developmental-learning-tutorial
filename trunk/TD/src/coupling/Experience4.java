package coupling;

import coupling.interaction.Interaction4;

public class Experience4 extends Experience {

	/**
	 * The experience's interaction.
	 */
	private Interaction4 interaction;
	
	public Experience4(String label){
		super(label);
	}

	public boolean isPrimitive(){
		return (this.interaction == null);
	}
	
	public void setInteraction(Interaction4 interaction){
		this.interaction = interaction;
	}
	
	public Interaction4 getInteraction(){
		return this.interaction;
	}

}
