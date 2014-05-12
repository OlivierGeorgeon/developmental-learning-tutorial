package agent.decider;

import coupling.Coupling3;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction3;

public class Episode3 implements Episode{
	
	private Coupling3 coupling;
	private Experience experience;
	private Interaction3 interaction;

	public Episode3(Coupling3 coupling, Experience experience){
		this.coupling = coupling;
		this.experience = experience;
	}

	public void record(Result result){
		this.interaction  = this.coupling.getInteraction(this.experience.getLabel() + result.getLabel());
	}
	
	public Interaction3 getInteraction() {
		return interaction;
	}

	public Experience getExperience() {
		return experience;
	}

	protected Coupling3 getCoupling(){
		return this.coupling;
	}
	
	protected void setInteraction(Interaction3 interaction){
		this.interaction = interaction;
	}
}
