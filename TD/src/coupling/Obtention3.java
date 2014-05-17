package coupling;

import coupling.interaction.Interaction3;

public class Obtention3 extends Obtention2 {
	
	public Obtention3(Interaction3 interaction){
		super(interaction);
	}
	
	public void setInteraction(Interaction3 interaction){
		this.interaction = interaction;
	}
	
	@Override
	public Interaction3 getInteraction(){
		return (Interaction3)this.interaction;
	}

	@Override
	public String getLabel() {
		return this.interaction.getLabel();
	}

}
