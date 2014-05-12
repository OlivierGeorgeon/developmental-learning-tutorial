package agent.decider;

import coupling.Coupling2;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction2;

public class Episode2<I extends Interaction2> implements Episode<Interaction2>{

	private Coupling2 coupling;
	private Experience experience;
	private I interaction;
	
	public Episode2(Coupling2 coupling, Experience experience){
		this.coupling = coupling;
		this.experience = experience;
	}

	@Override
	public void record(Result result){
		this.interaction  = (I) this.coupling.getInteraction(this.experience.getLabel() + result.getLabel());
	}
	
	@Override
	public Interaction2 getInteraction() {
		return interaction;
	}
}
