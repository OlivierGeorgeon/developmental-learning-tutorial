package coupling;

import coupling.interaction.Interaction3;

/**
 * An intention3 consists of an Interaction3
 */
public class Intention3 implements Intention {
	
	private Interaction3 interaction;
	
	public Intention3(Interaction3 interaction){
		this.interaction = interaction;
	}

	public Interaction3 getInteraction(){
		return this.interaction;
	}
	
	@Override
	public String getLabel() {
		return this.interaction.getLabel();
	}

}
