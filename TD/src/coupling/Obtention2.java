package coupling;

import coupling.interaction.Interaction2_;

/**
 * An Obtention2 consists of an Interaction2
 * @author Olivier.
 */
public class Obtention2 implements Obtention {
	
	protected Interaction2_ interaction;
	
	public Obtention2(Interaction2_ interaction){
		this.interaction = interaction;
	}
	
	public Interaction2_ getInteraction(){
		return this.interaction;
	}

	@Override
	public String getLabel() {
		return this.interaction.toString();
	}

}
