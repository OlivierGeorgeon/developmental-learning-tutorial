package existence;

import java.util.Collections;
import java.util.List;
import tracer.Trace;
import agent.decider.Anticipation;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction031;

public class Existence040 extends Existence031 {

	Interaction031 enactedInteraction;
	Interaction031 contextInteraction;
	
	@Override
	public String step() {
		
		Interaction031 intendedInteraction = chooseInteraction(this.enactedInteraction);
		this.enactedInteraction = enact(intendedInteraction);
		
		return this.enactedInteraction.getLabel();
	}
	
	public Interaction031 chooseInteraction(Interaction031 enactedInteraction){
		this.contextInteraction = enactedInteraction;
		if (enactedInteraction.getValence() >= 0)
			Trace.addEventElement("mood", "PLEASED");
		else
			Trace.addEventElement("mood", "PAINED");
		learnCompositeInteraction(this.contextInteraction, this.enactedInteraction);
		List<Anticipation> anticipations = computeAnticipations(this.enactedInteraction);
		return selectInteraction(anticipations);
	}
	
	public Interaction031 selectInteraction(List<Anticipation> anticipations){
		Interaction031 intendedInteraction = null;
		// TODO
		return intendedInteraction;
	}

	public Interaction031 enact(Interaction031 intendedInteraction){

		if (intendedInteraction.isPrimitive())
			return enactPrimitiveIntearction(intendedInteraction);
		else {			
			// Enact the pre-interaction
			Interaction031 enactedPreInteraction = enact(intendedInteraction.getPreInteraction());
			if (!enactedPreInteraction.equals(intendedInteraction.getPreInteraction())){
				// TODO interrupt
				return enactedPreInteraction;
			}
			else
			{
				// Enact the post-interaction
				Interaction031 enactedPostInteraction = enact(intendedInteraction.getPostInteraction());
				if (!enactedPostInteraction.equals(intendedInteraction.getPostInteraction())){
					// TODO interrupt
				}
				return (Interaction031)addOrGetCompositeInteraction(enactedPreInteraction, enactedPostInteraction);
			}
		}
	}

	/**
	 * Implements the cognitive coupling between the agent and the environment
	 * @param intendedPrimitiveInteraction: The intended primitive interaction to try to enact against the environment
	 * @param The actually enacted primitive interaction.
	 */
	public Interaction031 enactPrimitiveIntearction(Interaction031 intendedPrimitiveInteraction){
		/** Change the returnResult() to change the environment */		
		//Result result = returnResult010(experience);
		//Result result = returnResult020(experience);
		//Result result = returnResult030(experience);
		//Result result = returnResult031(experience);
		Result result = returnResult040(experience);
		return (Interaction031)this.addOrGetPrimitiveInteraction(experience, result);
	}

	/**
	 * Environment040
	 */
	private Experience previousExperience;
	private Experience penultimateExperience;

	public Result returnResult040(Experience experience){
		
		Result result = this.createOrGetResult(this.LABEL_R1);

		if (this.penultimateExperience != experience &&
			this.previousExperience == experience)
			result =  this.createOrGetResult(this.LABEL_R2);
		
		this.penultimateExperience = this.previousExperience;
		this.previousExperience = experience;
		
		return result;
	}
}
