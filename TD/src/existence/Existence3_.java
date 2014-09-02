package existence;

import java.util.ArrayList;
import java.util.List;
import coupling.Obtention2;
import reactive.Environment3;
import tracer.Trace;
import agent.decider.Decider3;
import agent.decider.Proposition;
import coupling.Experience;
import coupling.Intention;
import coupling.Intention3;
import coupling.Intention4;
import coupling.Obtention3;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction3_;
import coupling.interaction.Interaction4;

public class Existence3_ extends Existence030 {

	protected Interaction contextInteraction;
	protected Interaction currentInteraction;

	@Override
	protected void initExistence(){
		this.proactive = new Decider3(this);
		this.reactive = new Environment3(this);

		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createOrGetPrimitiveInteraction(e1, r1, -1);
		createOrGetPrimitiveInteraction(e1, r2, 1);
		createOrGetPrimitiveInteraction(e2, r1, -1);
		createOrGetPrimitiveInteraction(e2, r2, 1);
	}

	@Override
	protected void learn(){

		this.contextInteraction = this.currentInteraction;
		this.currentInteraction = ((Obtention2)this.obtention).getInteraction();
		
		if (this.currentInteraction.getValence() >= 0)
			Trace.addEventElement("mood", "PLEASED");
		else{
			Trace.addEventElement("mood", "PAINED");
		}
		
		if (this.contextInteraction != null )
			this.createOrReinforceCompositeInteraction(this.contextInteraction, this.currentInteraction);		
	}
	
	@Override
	protected Interaction3_ createNewInteraction(String label, int valence){
		return new Interaction3_(label, valence);
	}

	public Interaction3_ createOrReinforceCompositeInteraction(Interaction preInteraction, Interaction postInteraction) {
			
			Interaction3_ interaction = (Interaction3_)createOrGetCompositeInteraction(preInteraction, postInteraction);
			interaction.incrementWeight();
			return interaction;
		}

    public Interaction3_ createOrGetInteraction(Experience experience,
            Result result, int valence) {
    Interaction3_ interaction = (Interaction3_)createOrGet(experience.getLabel() + result.getLabel(), valence);
    interaction.setExperience(experience);
    interaction.setResult(result);
    return interaction;
}

	public List<Proposition> getPropositions(){
		List<Proposition> propositions = this.getDefaultPropositions(); 
		
		if (this.currentInteraction != null){
			for (Interaction activatedInteraction : getActivatedInteractions(this.currentInteraction)){
				Proposition proposition = new Proposition(((Interaction3_)activatedInteraction).getPostInteraction().getExperience(), ((Interaction3_)activatedInteraction).getWeight() * ((Interaction3_)activatedInteraction).getPostInteraction().getValence());
				int index = propositions.indexOf(proposition);
				if (index < 0)
					propositions.add(proposition);
				else
					propositions.get(index).addProclivity(((Interaction3_)activatedInteraction).getWeight() * ((Interaction3_)activatedInteraction).getPostInteraction().getValence());
			}
		}
		return propositions;
	}

	protected List<Proposition> getDefaultPropositions(){
		List<Proposition> propositions = new ArrayList<Proposition>();
		for (Experience experience : this.EXPERIENCES.values()){
			//if (experience.isPrimitive()){
				Proposition proposition = new Proposition(experience, 0);
				propositions.add(proposition);
			//}
		}
		return propositions;
	}
	
	public Interaction3_ enact(Interaction3_ intendedInteraction){

		if (intendedInteraction.isPrimitive()){
			Result result = this.reactive.giveResult(intendedInteraction.getExperience());
			return (Interaction3_)this.createOrGetPrimitiveInteraction(intendedInteraction.getExperience(), result, 0);
		}			
		else {			
			// Enact the pre interaction
			Interaction3_ enactedPreInteraction = enact(intendedInteraction.getPreInteraction());
			if (!enactedPreInteraction.equals(intendedInteraction.getPreInteraction())){
				return enactedPreInteraction;
			}
			else
			{
				// Enact the post interaction
				Interaction3_ enactedPostInteraction = enact(intendedInteraction.getPostInteraction());
				if (!enactedPostInteraction.equals(intendedInteraction.getPostInteraction())){
					return enactedPreInteraction;
				}
				return (Interaction3_)this.createOrGetCompositeInteraction(enactedPreInteraction, enactedPostInteraction);
			}
		}
	}
}
