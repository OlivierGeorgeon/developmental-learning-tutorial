package coupling;

import coupling.interaction.Interaction3;

public class Experience {
	
	private String label;
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
