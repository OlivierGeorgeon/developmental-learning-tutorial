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
 * Existence030 is a sort of Existence020.
 * It learns composite interactions (Interaction030). 
 * It bases its next choice on anticipations that can be made from reactivated composite interactions.
 * Existence030 illustrates the benefit of basing the next decision upon the previous enacted Interaction.   
 */
public class Existence030 extends Existence020 {

	private Interaction030 contextInteraction = null;
	private Interaction030 enactedInteraction = null;

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
	public String step() {
		
		Experience experience = chooseExperience(this.getPreviousResult());
		
		/** Change the call to the function returnResult to change the environment */
		//Result result = returnResult010(experience);
		Result result = returnResult030(experience);
	
		Interaction030 enactedInteraction = getInteraction(experience.getLabel() + result.getLabel());
		this.setContextInteraction(this.getEnactedInteraction());
		this.setEnactedInteraction(enactedInteraction);
		
		return enactedInteraction.toString();
	}

	@Override
	public Experience chooseExperience(Result result){
		
		Interaction030 previousEnactedInteraction = this.getEnactedInteraction();
		if (previousEnactedInteraction != null){
			if (previousEnactedInteraction.getValence() >= 0)
				Trace.addEventElement("mood", "PLEASED");
			else
				Trace.addEventElement("mood", "PAINED");
			if (this.getContextInteraction() != null)
				learnCompositeInteraction(this.getContextInteraction(), previousEnactedInteraction);
		}
		List<Anticipation> anticipations = computeAnticipations(previousEnactedInteraction);
		return selectExperience(anticipations);
	}
		
	public void learnCompositeInteraction(Interaction030 preInteraction, Interaction030 postInteraction){
		addOrGetCompositeInteraction(preInteraction, postInteraction);
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
		System.out.println("learn " + interaction.getLabel());
		return interaction;
	}

	@Override
	protected Interaction030 createInteraction(String label){
		return new Interaction030(label);
	}

	/**
	 * Computes the list of anticipations
	 * @param the enacted interaction
	 * @return the list of anticipations
	 */
	public List<Anticipation> computeAnticipations(Interaction030 enactedInteraction){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		if (enactedInteraction != null){
			for (Interaction activatedInteraction : this.getActivatedInteractions(enactedInteraction)){
				Interaction030 proposedInteraction = ((Interaction030)activatedInteraction).getPostInteraction();
				anticipations.add(new Anticipation030(proposedInteraction));
				System.out.println("afforded " + proposedInteraction.toString());
			}
		}
		return anticipations;
	}
	
	public Experience selectExperience(List<Anticipation> anticipations){
		Collections.sort(anticipations);
		Interaction intendedInteraction;
		if (anticipations.size() > 0){
			Interaction030 affordedInteraction = ((Anticipation030)anticipations.get(0)).getInteraction();
			if (affordedInteraction.getValence() >= 0)
				intendedInteraction = affordedInteraction;
			else
				intendedInteraction = (Interaction030)this.getOtherInteraction(affordedInteraction);
		}
		else 
			intendedInteraction = this.getOtherInteraction(null);
		return intendedInteraction.getExperience();
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
	
	protected void setContextInteraction(Interaction030 contextInteraction){
		this.contextInteraction = contextInteraction;
	}
	protected Interaction030 getContextInteraction(){
		return this.contextInteraction;
	}
	protected void setEnactedInteraction(Interaction030 enactedInteraction){
		this.enactedInteraction = enactedInteraction;
	}
	protected Interaction030 getEnactedInteraction(){
		return this.enactedInteraction;
	}

	/**
	 * Environment030
	 * Results in R1 when the current experience equals the previous experience
	 * and in R2 when the current experience differs from the previous experience.
	 */
	protected Result returnResult030(Experience experience){
		Result result = null;
		if (this.getPreviousExperience() == experience)
			result =  this.createOrGetResult(this.LABEL_R1);
		else
			result =  this.createOrGetResult(this.LABEL_R2);
		this.setPreviousExperience(experience);

		return result;
	}	
}
