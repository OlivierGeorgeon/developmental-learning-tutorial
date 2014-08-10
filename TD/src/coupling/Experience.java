package coupling;

import coupling.interaction.Interaction3;

/**
 * An experience (aka experiment) that can be performed by the system
 */
public class Experience {
	
	/**
	 * The experience's label.
	 */
	private String label;

	/**
	 * The experience's interaction.
	 * Not used until Existence 4.
	 */
	private Interaction3 interaction;
	
	public Experience(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
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
