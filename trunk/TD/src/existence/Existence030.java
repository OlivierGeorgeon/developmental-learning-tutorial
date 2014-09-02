package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import tracer.Trace;
import agent.decider.Anticipation;
import agent.decider.Anticipation030;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction020;
import coupling.interaction.Interaction030;

/**
 * Existence030 is a sort of Existence020.
 * It learns composite interactions (Interaction030). 
 * It bases its next choice on anticipations that can be made from reactivated composite interactions.
 */
public class Existence030 extends Existence020 {

	private Interaction030 contextInteraction;
	private Interaction030 enactedInteraction;

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
		
		this.experience = chooseExperience(result);
		
		/** Change the returnResult() to change the environment */		
		//this.result = returnResult010(experience);
		//this.result = returnResult020(experience);
		this.result = returnResult030(experience);
		
		return this.experience.getLabel() + this.result.getLabel();
	}

	@Override
	public Experience chooseExperience(Result result){
		this.contextInteraction = this.enactedInteraction;
		this.enactedInteraction = getInteraction(this.experience.getLabel() + result.getLabel());
		if (this.enactedInteraction.getValence() >= 0)
			Trace.addEventElement("mood", "PLEASED");
		else
			Trace.addEventElement("mood", "PAINED");
		learnCompositeInteraction(this.contextInteraction, this.enactedInteraction);
		List<Anticipation> anticipations = computeAnticipations(this.enactedInteraction);
		Experience experience = chooseExperience(anticipations);
		return experience;
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
		System.out.println("learn " + interaction.toString());
		return interaction;
	}

	@Override
	protected Interaction030 createInteraction(String label){
		return new Interaction030(label);
	}

	public List<Anticipation> computeAnticipations(Interaction030 enactedInteraction){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		if (enactedInteraction != null){
			for (Interaction activatedInteraction : this.getActivatedInteractions(this.enactedInteraction)){
				Interaction030 proposedInteraction = ((Interaction030)activatedInteraction).getPostInteraction();
				anticipations.add(new Anticipation030(proposedInteraction));
				System.out.println("afforded " + ((Interaction030)activatedInteraction).getPostInteraction().getLabel());
			}
		}
		return anticipations;
	}
	
	public Experience chooseExperience(List<Anticipation> anticipations){
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

	/**
	 * Environment030
	 */
	protected Experience previousExperience;

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
