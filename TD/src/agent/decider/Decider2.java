package agent.decider;

import java.util.List;
import tracer.Trace;
import coupling.Intention;
import coupling.Intention1;
import coupling.Obtention;
import coupling.Obtention2;
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

	protected Existence2 existence;
	
	public Decider2(Existence2 existence){
		this.existence = existence;
	}
	
	public Intention chooseIntention(Obtention obtention){
					
		List<Interaction> affordedInteractions = this.existence.affordedInteractions();
		
		Interaction intendedInteraction = this.existence.getOtherInteraction(null);
		if (affordedInteractions.size() > 0)
			if (affordedInteractions.get(0).getValence() >= 0)
				intendedInteraction = affordedInteractions.get(0);
			else
				intendedInteraction = (Interaction2)this.existence.getOtherInteraction(affordedInteractions.get(0));
	
		return new Intention1(intendedInteraction.getExperience());
	}
}
