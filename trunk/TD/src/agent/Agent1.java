package agent;

import tracer.Trace;
import coupling.Existence3;
import coupling.Experience;
import coupling.Result;

public class Agent1 implements Agent{

	private Existence3 coupling;
	private Experience experience;
	
	public Agent1(Existence3 coupling){
		this.coupling = coupling;
	}
	
	public Experience chooseExperience(Result result){
		
		if (this.experience == null)
			this.experience = this.coupling.getOtherExperience(null);
		else{ 
			int mood = this.coupling.getInteraction(this.experience.getLabel() + result.getLabel()).getValence();
			if (mood >= 0)
				Trace.addEventElement("mood", "PLEASED");
			else{
				Trace.addEventElement("mood", "PAINED");
				this.experience = this.coupling.getOtherExperience(this.experience);
			}
		}
		return this.experience;
	}
	
	protected Existence3 getCoupling(){
		return this.coupling;
	}
}
