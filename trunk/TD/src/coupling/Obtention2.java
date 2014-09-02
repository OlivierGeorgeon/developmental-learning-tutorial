package coupling;

import coupling.interaction.Interaction2;

/**
 * An Obtention2 consists of an Interaction2
 * @author Olivier.
 */
public class Obtention2 implements Obtention {
	
	protected Interaction2 interaction;
	
	public Obtention2(Interaction2 interaction){
		this.interaction = interaction;
	}
	
	public Interaction2 getInteraction(){
		return this.interaction;
	}

	@Override
	public String getLabel() {
		return this.interaction.toString();
	}

}
