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
 * Like Decider2, Decider21 can adapt to Reality1 or Reality2.
 * Again, it is PLEASED when the enacted Interaction has a positive or null Valence, and PAINED otherwise.
 * Additionally, like Existence0, Decider21 is SELF-SATISFIED when it correctly anticipated the result, and FRUSTRATED otherwise.
 * It is also BORED when it has been SELF-SATISFIED for too long.
 * Try to change the Valences of interactions and the reality defined in Existence2.initExistence(),
 * and observe that Decider21 tries to balance satisfaction and pleasure.
 * (when the valence of interaction are all set to 0, then only satisfaction/frustration/boredom drives Decider21's choices)
 * Decider21 illustrates the benefit of implementing different motivational dimensions.   
 * @author Olivier
 */
public class Decider21 implements Decider{

	public static int BOREDOME_LEVEL = 5;
	
	private Existence2 existence;
	private Interaction contextInteraction;
	private Interaction currentInteraction;	
	private int selfSatsfactionCounter;
	
	public Decider21(Existence2 coupling){
		this.existence = coupling;
	}
	
	public Intention chooseIntention(Obtention obtention){

		Result result = null; 
		if (obtention != null) result = ((Obtention1)obtention).getResult();
		
		if (this.currentInteraction != null){
			if (this.currentInteraction.getValence() >= 0)
				Trace.addEventElement("mood", "PLEASED");
			else
				Trace.addEventElement("mood", "PAINED");
			if (this.currentInteraction.getResult().equals(result)){			
				Trace.addEventElement("mood", "SELF-SATISFIED");
				this.selfSatsfactionCounter++;
			}
			else{
				Trace.addEventElement("mood", "FRUSTRATED");
				this.selfSatsfactionCounter = 0;
			}
		}
		
		if (result != null){
			Experience experience = this.currentInteraction.getExperience();
			this.currentInteraction = (Interaction2)this.existence.createOrGetPrimitiveInteraction(experience, result, 0);			
		}

		if (this.contextInteraction != null )
			this.existence.createOrGetCompositeInteraction((Interaction2)this.contextInteraction, (Interaction2)this.currentInteraction);

		List<Interaction> proposedInteractions = new ArrayList<Interaction>();
		if (this.currentInteraction != null)
			proposedInteractions = this.existence.proposeInteractions((Interaction2)this.currentInteraction); 
		
		Interaction intendedInteraction = (Interaction2)this.existence.getOtherInteraction(null);
		if (this.selfSatsfactionCounter < BOREDOME_LEVEL){
			if (proposedInteractions.size() > 0)
				if (proposedInteractions.get(0).getValence() >= 0)
					intendedInteraction = proposedInteractions.get(0);
				else
					intendedInteraction = (Interaction2)this.existence.getOtherInteraction(proposedInteractions.get(0));
		}
		else{
			Trace.addEventElement("mood", "BORED");
			this.selfSatsfactionCounter = 0;
			if (proposedInteractions.size() == 1)
				intendedInteraction = (Interaction2)this.existence.getOtherInteraction(proposedInteractions.get(0));
			else if (proposedInteractions.size() > 1)
				intendedInteraction = proposedInteractions.get(1);
		}
					
		this.contextInteraction = this.currentInteraction;

		this.currentInteraction = intendedInteraction;
		Trace.addEventElement("intend", intendedInteraction.toString());

		return new Intention1 (intendedInteraction.getExperience());
	}
}
