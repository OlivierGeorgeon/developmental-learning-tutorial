package agent.decider;

import java.util.Collections;
import java.util.List;
import coupling.Experience;
import coupling.Intention;
import coupling.Intention1;
import coupling.Obtention;
import existence.Existence3;

/**
 * Decider3 can adapt to Reality1, Reality2 or Reality3.
 * Like Decider2, Decider3 seeks to enact interactions that have positive valence.
 * Decider3 illustrates the benefit of reinforcing the weight of composite interactions
 * and of using the weight of activated interactions to balance the decision.   
 * @author Olivier
 */
public class Decider3 implements Decider{

	protected Existence3 existence;
	
	public Decider3(Existence3 existence3){
		this.existence = existence3;
	}
	
	public Intention chooseIntention(Obtention obtention){

		Experience experience = null;

		List<Proposition> propositions = this.existence.getPropositions();

		if (propositions.size() > 0){
			Collections.sort(propositions);
			for (Proposition proposition : propositions)
				System.out.println("propose " + proposition.toString());
			Proposition selectedProposition = propositions.get(0);
			experience = selectedProposition.getExperience();
		}	
		
		return new Intention1(experience);
	}	
}
