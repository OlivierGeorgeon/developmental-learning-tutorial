package coupling;

import coupling.interaction.Interaction3;

public class Experience {
	
	private String label;
	private Interaction3 interaction;
	
	Experience(String label){
		this.label = label;
	}
	
	Experience(Interaction3 interaction){
		this.label = interaction.getLabel();
		this.interaction = interaction;
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public boolean isPrimitive(){
		return (this.interaction == null);
	}
	
	public Interaction3 getInteraction(){
		return this.interaction;
	}

}
