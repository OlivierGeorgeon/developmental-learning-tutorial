package agent.decider;

import coupling.Coupling2;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;

public class Episode2 implements Episode{

	private Coupling2 coupling;
	private Experience experience;
	private Interaction interaction;
	
	public Episode2(Coupling2 coupling, Experience experience){
		this.coupling = coupling;
		this.experience = experience;
	}

	@Override
	public void record(Result result){
		this.interaction  = this.coupling.getInteraction(this.experience.getLabel() + result.getLabel());
	}
	
	@Override
	public Interaction getInteraction() {
		return this.interaction;
	}
}
