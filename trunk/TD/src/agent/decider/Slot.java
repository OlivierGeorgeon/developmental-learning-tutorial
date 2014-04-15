package agent.decider;

import coupling.interaction.Interaction3;

public class Slot {
	
	private Interaction3 interaction;
	
	public void setInteraction(Interaction3 interaction){
		this.interaction = interaction;
	}

	public Interaction3 getInteraction(){
		return this.interaction;
	}
	
	public boolean activate(Interaction3 interaction){
		if (interaction.getPreInteraction() == null)
			return false;
		else
			return (interaction.getPreInteraction().equals(this.interaction));
	}
	
	public boolean isEmpty(){
		return this.interaction == null;
	}
}
