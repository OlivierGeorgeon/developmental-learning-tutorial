package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tracer.Trace;
import agent.Anticipation;
import agent.decider.Anticipation030;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction030;

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
public class Existence032 extends Existence030 {

	@Override
	protected void initExistence(){
		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		addOrGetPrimitiveInteraction(e1, r1, -1);
		addOrGetPrimitiveInteraction(e1, r2, 1);
		addOrGetPrimitiveInteraction(e2, r1, -1);
		addOrGetPrimitiveInteraction(e2, r2, 1);
	}
	
	@Override
	public Experience chooseExperience(Result result){
		
		Interaction030 previousEnactedInteraction = (Interaction030)this.getEnactedInteraction();
		if (previousEnactedInteraction.getValence() >= 0)
			Trace.addEventElement("mood", "PLEASED");
		else
			Trace.addEventElement("mood", "PAINED");
		if (this.getExpectedResult() != null){
			if (this.getExpectedResult().equals(previousEnactedInteraction.getResult())){			
				Trace.addEventElement("mood", "SELF-SATISFIED");
				this.incSelfSatisfactionCounter();
			}
			else{
				Trace.addEventElement("mood", "FRUSTRATED");
				this.setSelfSatisfactionCounter(0);
			}
		}
		if (this.getContextInteraction()!= null)
			learnCompositeInteraction(this.getContextInteraction(), previousEnactedInteraction);

		List<Anticipation> anticipations = computeAnticipations(previousEnactedInteraction);
		Interaction030 intendedInteraction = selectInteraction(anticipations);
		this.setExpectedResult(intendedInteraction.getResult());
		
		return intendedInteraction.getExperience();
	}
		
	/**
	 * Records a composite interaction in memory
	 * @param preInteraction: The composite interaction's pre-interaction
	 * @param postInteraction: The composite interaction's post-interaction
	 * @return the learned composite interaction
	 */
	public Interaction030 addOrGetCompositeInteraction(
		Interaction030 preInteraction, Interaction030 postInteraction) {
		int valence = preInteraction.getValence() + postInteraction.getValence();
		Interaction030 interaction = (Interaction030)addOrGetInteraction(preInteraction.getLabel() + postInteraction.getLabel()); 
		interaction.setPreInteraction(preInteraction);
		interaction.setPostInteraction(postInteraction);
		interaction.setValence(valence);
		System.out.println("learn " + interaction.toString());
		return interaction;
	}

	@Override
	protected Interaction030 createInteraction(String label){
		return new Interaction030(label);
	}

	public Interaction030 selectInteraction(List<Anticipation> anticipations){

		Collections.sort(anticipations);
		Interaction030 intendedInteraction = (Interaction030)this.getOtherInteraction(null);
		if (this.getSelfSatisfactionCounter() < this.BOREDOME_LEVEL){
			if (anticipations.size() > 0){
				Interaction030 proposedInteraction = ((Anticipation030)anticipations.get(0)).getInteraction();
				if (proposedInteraction.getValence() >= 0)
					intendedInteraction = proposedInteraction;
				else
					intendedInteraction = (Interaction030)this.getOtherInteraction(proposedInteraction);
			}
		}
		else{
			Trace.addEventElement("mood", "BORED");
			this.setSelfSatisfactionCounter(0);
			if (anticipations.size() == 1)
				intendedInteraction = (Interaction030)this.getOtherInteraction(((Anticipation030)anticipations.get(0)).getInteraction());
			else if (anticipations.size() > 1)
				intendedInteraction = ((Anticipation030)anticipations.get(1)).getInteraction();
		}
		return intendedInteraction;
	}

	protected List<Interaction> getActivatedInteractions(Interaction interaction) {
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		for (Interaction activatedInteraction : this.INTERACTIONS.values())
			if (interaction == ((Interaction030)activatedInteraction).getPreInteraction())
				activatedInteractions.add((Interaction030)activatedInteraction);
		return activatedInteractions;
	}	

	@Override
	protected Interaction030 getInteraction(String label){
		return (Interaction030)INTERACTIONS.get(label);
	}

	public Interaction getOtherInteraction(Interaction interaction) {
		Interaction otherInteraction = (Interaction)INTERACTIONS.values().toArray()[0];
		if (interaction != null)
			for (Interaction e : INTERACTIONS.values()){
				if (e.getExperience() != null && e.getExperience()!=interaction.getExperience()){
					otherInteraction =  e;
					break;
				}
			}		
		return otherInteraction;
	}

	/**
	 * Environment030
	 * Results in R1 when the current experience equals the previous experience
	 * and in R2 when the current experience differs from the previous experience.
	 */
	private Experience previousExperience;

	public Result returnResult030(Experience experience){
		Result result = null;
		if (previousExperience == experience)
			result =  this.createOrGetResult(this.LABEL_R1);
		else
			result =  this.createOrGetResult(this.LABEL_R2);
		previousExperience = experience;

		return result;
	}
	
}
