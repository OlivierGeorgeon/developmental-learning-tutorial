package existence;

import java.util.Collections;
import java.util.List;
import tracer.Trace;
import agent.Anticipation;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction031;

/** 
* Existence040 is used to demonstrate an Existence capable of anticipating two steps to make a decision.
*/
public class Existence040 extends Existence031 {

	@Override
	public String step() {
		
		Interaction031 intendedInteraction = chooseInteraction(this.getEnactedInteraction());
		Interaction031 enactedInteraction = enact(intendedInteraction);
		
		this.setContextInteraction(this.getEnactedInteraction());
		this.setEnactedInteraction(enactedInteraction);
		
		return enactedInteraction.getLabel();
	}
	
	public Interaction031 chooseInteraction(Interaction031 enactedInteraction){
		Interaction031 previousEnactedInteraction = this.getEnactedInteraction();
		if (previousEnactedInteraction.getValence() >= 0)
			Trace.addEventElement("mood", "PLEASED");
		else
			Trace.addEventElement("mood", "PAINED");
		if (this.getContextInteraction()!= null)
			learnCompositeInteraction(this.getContextInteraction(), previousEnactedInteraction);
		List<Anticipation> anticipations = computeAnticipations(previousEnactedInteraction);
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
		Experience experience = intendedPrimitiveInteraction.getExperience();
		/** Change the returnResult() to change the environment */		
		//Result result = returnResult010(experience);
		//Result result = returnResult030(experience);
		//Result result = returnResult031(experience);
		Result result = returnResult040(experience);
		return (Interaction031)this.addOrGetPrimitiveInteraction(experience, result);
	}

	/**
	 * Environment040
	 * Results in R2 when the current experience equals the previous experience and differs from the penultimate experience.
	 * and in R1 otherwise.
	 * e1->r1 e1->r2 e2->r1 e2->r2 etc. 
	 */
	private Experience penultimateExperience;
	protected void setPenultimateExperience(Experience penultimateExperience){
		this.penultimateExperience = penultimateExperience;
	}
	protected Experience getPenultimateExperience(){
		return this.penultimateExperience;
	}

	public Result returnResult040(Experience experience){
		
		Result result = this.createOrGetResult(this.LABEL_R1);

		if (this.penultimateExperience != experience &&
			this.getPreviousExperience() == experience)
			result =  this.createOrGetResult(this.LABEL_R2);
		
		this.setPenultimateExperience(this.getPreviousExperience());
		this.setPreviousExperience(experience);
		
		return result;
	}
}
