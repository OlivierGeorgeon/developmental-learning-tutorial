package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import reactive.Environment2;
import tracer.Trace;
import agent.decider.Decider2;
import coupling.Experience;
import coupling.Obtention2;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction020;
import coupling.interaction.Interaction030;

/**
 * Existence030 is a sort of Existence020.
 * It learns composite interactions (Interaction030). 
 * It bases its next choice on the composite interactions that are reactivated
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
		
	}
	
	@Override
	protected void learn(){

		this.contextInteraction = this.enactedInteraction;
		this.enactedInteraction = ((Obtention2)this.obtention).getInteraction();
		
		if (this.enactedInteraction.getValence() >= 0)
			Trace.addEventElement("mood", "PLEASED");
		else{
			Trace.addEventElement("mood", "PAINED");
		}

		if (this.contextInteraction != null )
			this.createOrGetCompositeInteraction(this.contextInteraction, this.enactedInteraction);		
	}
	
	protected Interaction030 createNewInteraction(String label, int valence){
		return new Interaction030(label, valence);
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

	public List<Interaction> affordedInteractions(){
		List<Interaction> interactions = new ArrayList<Interaction>();
		if (this.enactedInteraction != null){
			for (Interaction activatedInteraction : this.getActivatedInteractions(this.enactedInteraction)){
				interactions.add(((Interaction030)activatedInteraction).getPostInteraction());
				System.out.println("afforded " + ((Interaction030)activatedInteraction).getPostInteraction().getLabel());
			}
		}
		Collections.sort(interactions);
		return interactions;
	}

	protected List<Interaction> getActivatedInteractions(Interaction interaction) {
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		for (Interaction activatedInteraction : this.INTERACTIONS.values())
			if (interaction == ((Interaction030)activatedInteraction).getPreInteraction())
				activatedInteractions.add((Interaction030)activatedInteraction);
		return activatedInteractions;
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
	
	@Override
	protected Interaction030 getInteraction(String label){
		return (Interaction030)INTERACTIONS.get(label);
	}

}
