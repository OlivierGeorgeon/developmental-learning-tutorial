package agent.decider;

import tracer.Trace;
import coupling.Experience;
import coupling.Intention;
import coupling.Intention1;
import coupling.Obtention;
import coupling.Obtention1;
import coupling.Result;
import existence.Existence1_;

/**
 * Decider1 is a re-implementation of the decisional mechanism of Existence01.
 * Decider1 is PLEASED when the enacted Interaction has a positive or null Valence, and PAINED otherwise.
 * It demonstrates a rudimentary decisional mechanism and a rudimentary learning mechanism.
 * It learns to choose the Experience that induces an Interaction that has a positive valence.  
 * Try to change the Valences of interactions and the reality defined in Existence1.initExistence(),
 * and observe that the Decider1 still learns to enact interactions that have positive valences.  
 * @author Olivier
 */
public class Decider1 implements Decider{

	private Existence1_ existence;
	private Experience experience;
	
	public Decider1(Existence1_ existence){
		this.existence = existence;
	}
	
	public Intention chooseIntention(Obtention obtention){
		
		Result result = null; 
		if (obtention != null) result = ((Obtention1)obtention).getResult();

		if (this.experience == null)
			this.experience = this.existence.getOtherExperience(null);
		else{ 
			int mood = this.existence.getInteraction(this.experience.getLabel() + result.getLabel()).getValence();
			if (mood >= 0)
				Trace.addEventElement("mood", "PLEASED");
			else{
				Trace.addEventElement("mood", "PAINED");
				this.experience = this.existence.getOtherExperience(this.experience);
			}
		}
		return new Intention1(this.experience);
	}	
}
