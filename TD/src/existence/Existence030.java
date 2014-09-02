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
		createPrimitiveInteraction(e1, r1, -1);
		createPrimitiveInteraction(e1, r2, 1);
		createPrimitiveInteraction(e2, r1, -1);
		createPrimitiveInteraction(e2, r2, 1);
	}
	
	@Override
	public Experience chooseExperience(Result result){
		this.contextInteraction = this.enactedInteraction;
		this.enactedInteraction = getInteraction(this.experience.getLabel() + result.getLabel());
		if (this.enactedInteraction.getValence() >= 0)
			Trace.addEventElement("mood", "PLEASED");
		else
			Trace.addEventElement("mood", "PAINED");
		createOrGetCompositeInteraction(this.contextInteraction, this.enactedInteraction);
		List<Anticipation> anticipations = computeAnticipations(this.enactedInteraction);
		Experience experience = chooseExperience(anticipations);
		return experience;
	}
		
	public Interaction030 createOrGetCompositeInteraction(
		Interaction030 preInteraction, Interaction030 postInteraction) {
		int valence = preInteraction.getValence() + postInteraction.getValence();
		Interaction030 interaction = (Interaction030)createOrGet(preInteraction.getLabel() + postInteraction.getLabel(), valence); 
		interaction.setPreInteraction(preInteraction);
		interaction.setPostInteraction(postInteraction);
		System.out.println("learn " + interaction.toString());
		return interaction;
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

	@Override
	public Result returnResult(Experience experience){
		Result result = null;
		if (previousExperience == experience)
			result =  this.createOrGetResult(this.LABEL_R1);
		else
			result =  this.createOrGetResult(this.LABEL_R2);
		previousExperience = experience;

		return result;
	}
	
}
