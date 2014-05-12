package agent.decider;

import coupling.Coupling2;
import coupling.Coupling20;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;

public class Episode2 implements Episode{

	private Coupling2 coupling;
	private Interaction interaction;
	
	public Episode2(Coupling2 coupling, Interaction interaction){
		this.coupling = coupling;
		this.interaction = interaction;
	}

	public void record(Result result){
		Experience experience = this.interaction.getExperience();
		this.interaction = this.coupling.createOrGetPrimitiveInteraction(experience, result, 0);
	}
	
	public Interaction getInteraction() {
		return this.interaction;
	}
}
