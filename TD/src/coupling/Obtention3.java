package coupling;

import coupling.interaction.Interaction3_;

/**
 * An Obtention2 consists of an Interaction3
 * @author Olivier.
 */
public class Obtention3 extends Obtention2 {
	
	public Obtention3(Interaction3_ interaction){
		super(interaction);
	}
	
	public void setInteraction(Interaction3_ interaction){
		this.interaction = interaction;
	}
	
	@Override
	public Interaction3_ getInteraction(){
		return (Interaction3_)this.interaction;
	}

	@Override
	public String getLabel() {
		return this.interaction.getLabel();
	}

}
