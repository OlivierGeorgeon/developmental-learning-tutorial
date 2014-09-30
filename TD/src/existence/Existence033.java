package existence;

import java.util.Collections;
import java.util.List;
import tracer.Trace;
import agent.Anticipation;
import agent.Anticipation032;
import coupling.Experiment;
import coupling.Result;
import coupling.interaction.Interaction030;
import coupling.interaction.Interaction032;

/**
 * Like Existence030, Existence032 can adapt to Environment010, Environment020, and Environment030.
 * Again, it is PLEASED when the enacted Interaction has a positive or null Valence, and PAINED otherwise.
 * Additionally, like Existence010, Existence032 is SELF-SATISFIED when it correctly anticipated the result, and FRUSTRATED otherwise.
 * It is also BORED when it has been SELF-SATISFIED for too long.
 * Try to change the Valences of interactions and the reality defined in Existence2.initExistence(),
 * and observe that Existence032 tries to balance satisfaction and pleasure.
 * (when the valence of interaction are all set to 0, then only satisfaction/frustration/boredom drives Existence032's choices)
 * Existence032 illustrates the benefit of implementing different motivational dimensions.   
 */
public class Existence033 extends Existence032 {

	@Override
	public String step() {
		
		List<Anticipation> anticipations = anticipate();
		Interaction032 intendedInteraction =  (Interaction032)selectInteraction(anticipations);
		Experiment experience = intendedInteraction.getExperience();
		
		/** Change the call to the function returnResult to change the environment */
		//Result result = returnResult010(experience);
		//Result result = returnResult030(experience);
		Result result = returnResult031(experience);
	
		Interaction030 enactedInteraction = getInteraction(experience.getLabel() + result.getLabel());
		System.out.println("Enacted "+ enactedInteraction.toString());
		
		if (enactedInteraction != intendedInteraction){
			intendedInteraction.addAlternateInteraction(enactedInteraction);
			System.out.println("Alternate "+ enactedInteraction.getLabel());
		}
		
		if (enactedInteraction.getValence() >= 0)
			this.setMood(Mood.PLEASED);
		else
			this.setMood(Mood.PAINED);
		if (enactedInteraction == intendedInteraction){
			this.setMood(Mood.SELF_SATISFIED);
			this.incSelfSatisfactionCounter();
		}
		else{
			this.setMood(Mood.FRUSTRATED);
			this.setSelfSatisfactionCounter(0);
		}
		
		this.learnCompositeInteraction(enactedInteraction);

		this.setEnactedInteraction(enactedInteraction);
		
		return "" + this.getMood();
	}


	/**
	 * Compute the system's mood and
	 * and choose the next experience based on the previous interaction
	 * @return The next experience.
	 */
	@Override
	public Interaction032 selectInteraction(List<Anticipation> anticipations){

		Collections.sort(anticipations);
		Interaction032 intendedInteraction = (Interaction032)this.getOtherInteraction(null);
		if (this.getSelfSatisfactionCounter() < this.BOREDOME_LEVEL){
			if (anticipations.size() > 0){
				Interaction032 proposedInteraction = (Interaction032)((Anticipation032)anticipations.get(0)).getInteraction();
				if (proposedInteraction.getValence() >= 0)
					intendedInteraction = proposedInteraction;
				else
					intendedInteraction = (Interaction032)this.getOtherInteraction(proposedInteraction);
			}
		}
		else{
			Trace.addEventElement("mood", "BORED");
			this.setSelfSatisfactionCounter(0);
			if (anticipations.size() == 1)
				intendedInteraction = (Interaction032)this.getOtherInteraction(((Anticipation032)anticipations.get(0)).getInteraction());
			else if (anticipations.size() > 1)
				intendedInteraction = (Interaction032)((Anticipation032)anticipations.get(1)).getInteraction();
		}
		return intendedInteraction;
	}

}
