package agent.decider;

import java.util.List;
import tracer.Trace;
import coupling.Intention;
import coupling.Intention1;
import coupling.Obtention;
import coupling.Obtention2;
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
public class Decider21 extends Decider2{

	public static int BOREDOME_LEVEL = 5;
	
	private int selfSatisfactionCounter;
	
	private Result expectedResult;
	
	public Decider21(Existence2 existence){
		super(existence);
	}
	
	@Override
	public Intention chooseIntention(Obtention obtention){

		Interaction2 interaction = null;
		if (obtention != null) interaction = (((Obtention2)obtention).getInteraction());
		
		if (interaction != null){
			if (interaction.getValence() >= 0)
				Trace.addEventElement("mood", "PLEASED");
			else
				Trace.addEventElement("mood", "PAINED");
			if (this.expectedResult != null){
				if (this.expectedResult.equals(interaction.getResult())){			
					Trace.addEventElement("mood", "SELF-SATISFIED");
					this.selfSatisfactionCounter++;
				}
				else{
					Trace.addEventElement("mood", "FRUSTRATED");
					this.selfSatisfactionCounter = 0;
				}
			}
		}
		
		List<Interaction> proposedInteractions = this.existence.affordedInteractions();
		
		Interaction intendedInteraction = (Interaction2)this.existence.getOtherInteraction(null);
		if (this.selfSatisfactionCounter < BOREDOME_LEVEL){
			if (proposedInteractions.size() > 0)
				if (proposedInteractions.get(0).getValence() >= 0)
					intendedInteraction = proposedInteractions.get(0);
				else
					intendedInteraction = (Interaction2)this.existence.getOtherInteraction(proposedInteractions.get(0));
		}
		else{
			Trace.addEventElement("mood", "BORED");
			this.selfSatisfactionCounter = 0;
			if (proposedInteractions.size() == 1)
				intendedInteraction = (Interaction2)this.existence.getOtherInteraction(proposedInteractions.get(0));
			else if (proposedInteractions.size() > 1)
				intendedInteraction = proposedInteractions.get(1);
		}
					
		Trace.addEventElement("intend", intendedInteraction.toString());

		return new Intention1 (intendedInteraction.getExperience());
	}
}
