package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import agent.Anticipation;
import agent.Anticipation030;
import coupling.Experiment;
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

	private Interaction030 enactedInteraction;

	@Override
	protected void initExistence(){
		Experiment e1 = addOrGetExperience(LABEL_E1);
		Experiment e2 = addOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		addOrGetPrimitiveInteraction(e1, r1, -1);
		addOrGetPrimitiveInteraction(e1, r2, 1);
		addOrGetPrimitiveInteraction(e2, r1, -1);
		addOrGetPrimitiveInteraction(e2, r2, 1);
	}
	
	@Override
	public String step() {
		
		List<Anticipation> anticipations = anticipate();
		Experiment experience =  selectInteraction(anticipations).getExperience();
		
		/** Change the call to the function returnResult to change the environment */
		//Result result = returnResult010(experience);
		Result result = returnResult030(experience);
	
		Interaction030 enactedInteraction = getInteraction(experience.getLabel() + result.getLabel());
		System.out.println("Enacted "+ enactedInteraction.toString());
		
		if (enactedInteraction.getValence() >= 0)
			this.setMood(Mood.PLEASED);
		else
			this.setMood(Mood.PAINED);
		
		this.learnCompositeInteraction(enactedInteraction);
		
		this.setEnactedInteraction(enactedInteraction);
		
		return "" + this.getMood();
	}

	/**
	 * Learn the composite interaction from the previous enacted interaction and the current enacted interaction
	 */
	public void learnCompositeInteraction(Interaction030 interaction){
		Interaction030 preInteraction = this.getEnactedInteraction();
		Interaction030 postInteraction = interaction;
		if (preInteraction != null)
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
	 * @return the list of anticipations
	 */
	public List<Anticipation> anticipate(){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		if (this.getEnactedInteraction() != null){
			for (Interaction activatedInteraction : this.getActivatedInteractions()){
				Interaction030 proposedInteraction = ((Interaction030)activatedInteraction).getPostInteraction();
				anticipations.add(new Anticipation030(proposedInteraction));
				System.out.println("afforded " + proposedInteraction.toString());
			}
		}
		return anticipations;
	}
	
	protected Interaction030 selectInteraction(List<Anticipation> anticipations){
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
		return (Interaction030)intendedInteraction;
	}

	/**
	 * Get the list of activated interactions
	 * An activated interaction is a composite interaction whose preInteraction matches the enactedInteraction
	 * @return the list of anticipations
	 */
	public List<Interaction> getActivatedInteractions() {
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		if (this.getEnactedInteraction() != null)
			for (Interaction activatedInteraction : this.INTERACTIONS.values())
				if (((Interaction030)activatedInteraction).getPreInteraction() == this.getEnactedInteraction())
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
	
	protected void setEnactedInteraction(Interaction030 enactedInteraction){
		this.enactedInteraction = enactedInteraction;
	}
	protected Interaction030 getEnactedInteraction(){
		return this.enactedInteraction;
	}

	/**
	 * Environment030
	 * Results in R1 when the current experiment equals the previous experiment
	 * and in R2 when the current experiment differs from the previous experiment.
	 */
	protected Result returnResult030(Experiment experiment){
		Result result = null;
		if (this.getPreviousExperiment() == experiment)
			result =  this.createOrGetResult(this.LABEL_R1);
		else
			result =  this.createOrGetResult(this.LABEL_R2);
		this.setPreviousExperiment(experiment);

		return result;
	}	
}
