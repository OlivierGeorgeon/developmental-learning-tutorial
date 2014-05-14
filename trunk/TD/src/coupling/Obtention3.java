package coupling;

import coupling.interaction.Interaction3;

public class Obtention3 implements Obtention {
	
	private Interaction3 interaction;
	
	public Obtention3(Interaction3 interaction){
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
