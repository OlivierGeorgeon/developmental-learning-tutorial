package Existence;

import org.w3c.dom.Element;

import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;
import coupling.Experience;
import coupling.Result;

public class Existence01 extends Existence0 {

	@Override
	protected void initExistence(){
		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createPrimitiveInteraction(e1, r1, 1); // Change the valence of interactions to change the agent's motivation 
		createPrimitiveInteraction(e1, r2, -1);
		createPrimitiveInteraction(e2, r1, 1);
		createPrimitiveInteraction(e2, r2, -1);		
		Tracer<Element> tracer = new ConsoleTracer();
		Trace.init(tracer);		
	}
	
	@Override
	public Experience chooseExperience(Result result){
		
		if (this.getExperience() == null)
			this.setExperience(this.getOtherExperience(null));
		else{ 
			int mood = getInteraction(this.getExperience().getLabel() + result.getLabel()).getValence();
			if (mood > 0)
				Trace.addEventElement("mood", "PLEASED");
			else{
				Trace.addEventElement("mood", "PAINED");
				this.setExperience(this.getOtherExperience(this.getExperience()));
			}
		}
		return this.getExperience();
	}
	
	// Change the function giveResult(experience) to simulate a different environment
	@Override
	public Result giveResult(Experience experience){
		if (experience.equals(this.createOrGetExperience(LABEL_E1)))
			return this.createOrGetResult(LABEL_R2);
		else
			return this.createOrGetResult(LABEL_R1);
	}

}
