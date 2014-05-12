package agent;

import tracer.Trace;
import coupling.Coupling;
import coupling.Experience;
import coupling.Result;

public class Agent1 implements Agent{

	private Coupling coupling;
	private Experience experience;
	
	public Agent1(Coupling coupling){
		this.coupling = coupling;
	}
	
	public Experience chooseExperience(Result result){
		
		if (this.experience == null)
			this.experience = this.coupling.getOtherExperience(null);
		else{ 
			int mood = this.coupling.getInteraction(this.experience.getLabel() + result.getLabel()).getValence();
			if (mood > 0)
				Trace.addEventElement("mood", "PLEASED");
			else{
				Trace.addEventElement("mood", "PAINED");
				this.experience = this.coupling.getOtherExperience(this.experience);
			}
		}
		return this.experience;
	}
	
	protected Coupling getCoupling(){
		return this.coupling;
	}
}
