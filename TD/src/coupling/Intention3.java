package coupling;

import coupling.interaction.Interaction3_;

/**
 * An intention3 consists of an Interaction3
 */
public class Intention3 implements Intention {
	
	private Interaction3_ interaction;
	
	public Intention3(Interaction3_ interaction){
		this.interaction = interaction;
	}

	public Interaction3_ getInteraction(){
		return this.interaction;
	}
	
	@Override
	public String getLabel() {
		return this.interaction.getLabel();
	}

}
