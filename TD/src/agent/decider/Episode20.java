package agent.decider;

import coupling.Coupling20;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction2;

public class Episode20 implements Episode{

	private Coupling20 coupling;
	private Interaction2 interaction;
	
	public Episode20(Coupling20 coupling, Interaction2 interaction){
		this.coupling = coupling;
		this.interaction = interaction;
	}

	public void record(Result result){
		Experience experience = this.interaction.getExperience();
		this.coupling.createPrimitiveInteraction(experience, result, 0);
		this.interaction = this.coupling.getInteraction(experience.getLabel() + result.getLabel());
	}
	
	public Interaction2 getInteraction() {
		return this.interaction;
	}
}
