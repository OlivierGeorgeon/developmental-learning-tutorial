package agent;

import coupling.Coupling;
import coupling.Experience;
import coupling.Result;

public class Agent1 implements Agent{

	private Coupling coupling;
	private Experience experience;
	
	public Agent1(Coupling coupling){
		this.coupling = coupling;
		this.experience = coupling.getFirstExperience();
	}
	
	public Experience chooseExperience(Result result){
		
		if (coupling.getInteraction(this.experience.getLabel() + result.getLabel()).getValence() < 0)
			this.experience = coupling.getOtherExperience(this.experience);		
		return this.experience;
	}
}
