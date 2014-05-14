package agent.decider;

import Existence.Existence2;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;

public class Episode2 implements Episode{

	private Existence2 existence;
	private Interaction interaction;
	
	public Episode2(Existence2 existence, Interaction interaction){
		this.existence = existence;
		this.interaction = interaction;
	}

	public void record(Result result){
		Experience experience = this.interaction.getExperience();
		this.interaction = this.existence.createOrGetPrimitiveInteraction(experience, result, 0);
	}
	
	public Interaction getInteraction() {
		return this.interaction;
	}
}
