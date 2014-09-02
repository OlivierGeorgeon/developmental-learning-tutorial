package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import agent.decider.Anticipation;
import agent.decider.Anticipation031;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction030;
import coupling.interaction.Interaction031;

public class Existence031 extends Existence030 {

	@Override
	public String step() {
		
		this.experience = chooseExperience(result);
		
		/** Change the returnResult() to change the environment */		
		//this.result = returnResult010(experience);
		//this.result = returnResult020(experience);
		//this.result = returnResult030(experience);
		this.result = returnResult031(experience);
		
		return this.experience.getLabel() + this.result.getLabel();
	}

	/**
	 * Records a composite interaction in memory
	 * or increment its weight
	 * @param preInteraction: The composite interaction's pre-interaction
	 * @param postInteraction: The composite interaction's post-interaction
	 * @return the learned composite interaction
	 */
	@Override
	public void learnCompositeInteraction(Interaction030 preInteraction, Interaction030 postInteraction){
		Interaction031 interaction = (Interaction031)addOrGetCompositeInteraction(preInteraction, postInteraction);
		interaction.incrementWeight();
	}

	@Override
	protected Interaction031 createInteraction(String label){
		return new Interaction031(label);
	}

	/**
	 * Computes the list of anticipations
	 * @param the enacted interaction
	 * @return the list of anticipations
	 */
	@Override
	public List<Anticipation> computeAnticipations(Interaction030 enactedInteraction){
		List<Anticipation> anticipations = this.getDefaultPropositions(); 
		
		if (enactedInteraction != null){
			for (Interaction activatedInteraction : getActivatedInteractions(enactedInteraction)){
				Anticipation031 proposition = new Anticipation031(((Interaction031)activatedInteraction).getPostInteraction().getExperience(), ((Interaction031)activatedInteraction).getWeight() * ((Interaction031)activatedInteraction).getPostInteraction().getValence());
				int index = anticipations.indexOf(proposition);
				if (index < 0)
					anticipations.add(proposition);
				else
					((Anticipation031)anticipations.get(index)).addProclivity(((Interaction031)activatedInteraction).getWeight() * ((Interaction031)activatedInteraction).getPostInteraction().getValence());
			}
		}
		return anticipations;
	}

	protected List<Anticipation> getDefaultPropositions(){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		for (Experience experience : this.EXPERIENCES.values()){
			Anticipation031 anticipation = new Anticipation031(experience, 0);
			anticipations.add(anticipation);
		}
		return anticipations;
	}
	

	@Override
	public Experience selectExperience(List<Anticipation> anticipations){
		// The list of anticipations is never empty because all the experiences are proposed by default with a proclivity of 0
		Collections.sort(anticipations);
		for (Anticipation anticipation : anticipations)
			System.out.println("propose " + anticipation.toString());
		
		Anticipation031 selectedAnticipation = (Anticipation031)anticipations.get(0);
		return selectedAnticipation.getExperience();
	}

	/**
	 * Environment031
	 */
	private final int T1 = 8;
	private final int T2 = 15;
	private int clock = 0;

	public Result returnResult031(Experience experience){

		Result result = null;

		this.clock++;
		
		if (clock <= T1 || clock > T2){
			if (experience.equals(this.createOrGetExperience(this.LABEL_E1)))
				result =  this.createOrGetResult(this.LABEL_R1);
			else
				result = this.createOrGetResult(this.LABEL_R2);
		} 
		else {
			if (experience.equals(this.createOrGetExperience(this.LABEL_E1)))
				result = this.createOrGetResult(this.LABEL_R2);
			else
				result = this.createOrGetResult(this.LABEL_R1);
		}
		return result;
	}

	
}
