package agent.decider;

import java.util.ArrayList;
import java.util.List;

import tracer.Trace;
import coupling.Experience;
import coupling.Intention;
import coupling.Intention1;
import coupling.Obtention;
import coupling.Obtention1;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;
import existence.Existence2;

/**
 * Decider2 can adapt to Reality1 or Reality2.
 * Like Decider1, Decider2 is PLEASED when the enacted Interaction has a positive or null Valence, and PAINED otherwise.
 * Try to change the Valences of interactions and the reality defined in Existence2.initExistence(),
 * and observe that Decider2 still learns to enact interactions that have positive valences.
 * Decider2 illustrates the benefit of basing the next decision upon the previous enacted Interaction.   
 * @author Olivier
 */
public class Decider2 implements Decider{

	private Existence2 existence;
	private Interaction contextInteraction;
	private Interaction currentInteraction;
	
	public Decider2(Existence2 existence){
		this.existence = existence;
	}
	
	public Intention chooseIntention(Obtention obtention){

		Result result = null; 
		if (obtention != null) result = ((Obtention1)obtention).getResult();

		if (result != null){
			Experience experience = this.currentInteraction.getExperience();
			this.currentInteraction = (Interaction2)this.existence.createOrGetPrimitiveInteraction(experience, result, 0);
		}
		
		if (this.contextInteraction != null )
			this.existence.createOrGetCompositeInteraction((Interaction2)this.contextInteraction, (Interaction2)this.currentInteraction);

		List<Interaction> proposedInteractions = new ArrayList<Interaction>();
		if (this.currentInteraction != null){
			if (this.currentInteraction.getValence() >= 0)
				Trace.addEventElement("mood", "PLEASED");
			else{
				Trace.addEventElement("mood", "PAINED");
			}
			proposedInteractions = this.existence.proposeInteractions(this.currentInteraction); 
		}
			
		Interaction intendedInteraction = this.existence.getOtherInteraction(null);
		if (proposedInteractions.size() > 0)
			if (proposedInteractions.get(0).getValence() >= 0)
				intendedInteraction = proposedInteractions.get(0);
			else
				intendedInteraction = (Interaction2)this.existence.getOtherInteraction(proposedInteractions.get(0));
	
		this.contextInteraction = this.currentInteraction;

		this.currentInteraction = intendedInteraction;
		Trace.addEventElement("intend", intendedInteraction.toString());

		return new Intention1(intendedInteraction.getExperience());
	}
}
