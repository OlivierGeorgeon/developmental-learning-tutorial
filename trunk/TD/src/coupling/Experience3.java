package coupling;

import coupling.interaction.Interaction3;

public class Experience3 extends Experience {

	/**
	 * The experience's sub-interaction.
	 */
	private Interaction3 interaction;
	
	public Experience3(String label){
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
