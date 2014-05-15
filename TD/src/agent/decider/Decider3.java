package agent.decider;

import java.util.Collections;
import java.util.List;

import coupling.Experience;
import coupling.Intention;
import coupling.Intention1;
import coupling.Obtention;
import coupling.Obtention1;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;
import coupling.interaction.Interaction3;
import existence.Existence3;

/**
 * Decider2 can adapt to Reality1, Reality2 or Reality3.
 * Like Decider2, Decider3 is PLEASED when the enacted Interaction has a positive or null Valence, and PAINED otherwise.
 * Decider3 illustrates the benefit of reinforcing the weight of composite interactions
 * and of using the weight of activated interactions to balance the decision.   
 * @author Olivier
 */
public class Decider3 implements Decider{

	private Existence3 existence;
	private Interaction contextInteraction;
	private Interaction currentInteraction;
	
	public Decider3(Existence3 existence3){
		this.existence = existence3;
		//this.currentEpisode = this.coupling.createEpisode(this.coupling.getOtherExperience(null));
	}
	
	public Intention chooseIntention(Obtention obtention){

		Result result = null; 
		if (obtention != null) result = ((Obtention1)obtention).getResult();
		
		if (result != null){
			Experience experience = this.currentInteraction.getExperience();
			this.currentInteraction = (Interaction2)this.existence.createOrGetPrimitiveInteraction(experience, result, 0);			
		}

		if (this.contextInteraction != null )
			this.existence.createOrReinforceCompositeInteraction((Interaction3)this.contextInteraction, (Interaction3)this.currentInteraction);
		
		Experience experience = this.existence.getOtherExperience(null);
		if (this.contextInteraction != null)
			experience = this.selectExperience(); 
			
		if (this.currentInteraction != null)
			this.contextInteraction = this.currentInteraction;

		return new Intention1(experience);
	}
	
	private Experience selectExperience(){

		Experience experience = null;

		List<Proposition> propositions = this.existence.getPropositions((Interaction3)this.currentInteraction);

		if (propositions.size() > 0){
			Collections.sort(propositions);
			for (Proposition proposition : propositions)
				System.out.println("propose " + proposition.toString());
			Proposition selectedProposition = propositions.get(0);
			experience = selectedProposition.getExperience();
		}	
		
		System.out.println("select " + experience.getLabel());

		return experience;
	}
}
