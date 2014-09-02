package coupling;

import coupling.interaction.Interaction031;

/**
 * An Obtention2 consists of an Interaction3
 * @author Olivier.
 */
public class Obtention3 extends Obtention2 {
	
	public Obtention3(Interaction031 interaction){
		super(interaction);
	}
	
	public void setInteraction(Interaction031 interaction){
		this.interaction = interaction;
	}
	
	@Override
	public Interaction031 getInteraction(){
		return (Interaction031)this.interaction;
	}

	@Override
	public String getLabel() {
		return this.interaction.getLabel();
	}

}
