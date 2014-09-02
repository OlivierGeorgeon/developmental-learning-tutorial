package agent.decider;

import coupling.interaction.Interaction3_;

public class Slot {
	
	private Interaction3_ interaction;
	
	public void setInteraction(Interaction3_ interaction){
		this.interaction = interaction;
	}

	public Interaction3_ getInteraction(){
		return this.interaction;
	}
	
	public boolean activate(Interaction3_ interaction){
		if (interaction.getPreInteraction() == null)
			return false;
		else
			return (interaction.getPreInteraction().equals(this.interaction));
	}
	
	public boolean isEmpty(){
		return this.interaction == null;
	}
}
