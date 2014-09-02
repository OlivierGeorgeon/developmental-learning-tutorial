package coupling;

import coupling.interaction.Interaction030;

/**
 * An Obtention2 consists of an Interaction2
 * @author Olivier.
 */
public class Obtention2 implements Obtention {
	
	protected Interaction030 interaction;
	
	public Obtention2(Interaction030 interaction){
		this.interaction = interaction;
	}
	
	public Interaction030 getInteraction(){
		return this.interaction;
	}

	@Override
	public String getLabel() {
		return this.interaction.toString();
	}

}
