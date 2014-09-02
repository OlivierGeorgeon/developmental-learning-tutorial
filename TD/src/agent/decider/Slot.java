package agent.decider;

import coupling.interaction.Interaction031;

public class Slot {
	
	private Interaction031 interaction;
	
	public void setInteraction(Interaction031 interaction){
		this.interaction = interaction;
	}

	public Interaction031 getInteraction(){
		return this.interaction;
	}
	
	public boolean activate(Interaction031 interaction){
		if (interaction.getPreInteraction() == null)
			return false;
		else
			return (interaction.getPreInteraction().equals(this.interaction));
	}
	
	public boolean isEmpty(){
		return this.interaction == null;
	}
}
