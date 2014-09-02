package coupling;

import coupling.interaction.Interaction031;

/**
 * An intention3 consists of an Interaction3
 */
public class Intention3 implements Intention {
	
	private Interaction031 interaction;
	
	public Intention3(Interaction031 interaction){
		this.interaction = interaction;
	}

	public Interaction031 getInteraction(){
		return this.interaction;
	}
	
	@Override
	public String getLabel() {
		return this.interaction.getLabel();
	}

}
