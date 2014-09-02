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
import coupling.interaction.Interaction031;
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
	protected Interaction031 createNewInteraction(String label, int valence){
		return new Interaction031(label, valence);
	}

	public Interaction031 createOrReinforceCompositeInteraction(Interaction preInteraction, Interaction postInteraction) {
			
			Interaction031 interaction = (Interaction031)learnCompositeInteraction(preInteraction, postInteraction);
			interaction.incrementWeight();
			return interaction;
		}

    public Interaction031 createOrGetInteraction(Experience experience,
            Result result, int valence) {
    Interaction031 interaction = (Interaction031)addOrGetIntearction(experience.getLabel() + result.getLabel(), valence);
    interaction.setExperience(experience);
    interaction.setResult(result);
    return interaction;
}

	public List<Proposition> getPropositions(){
		List<Proposition> propositions = this.getDefaultPropositions(); 
		
		if (this.currentInteraction != null){
			for (Interaction activatedInteraction : getActivatedInteractions(this.currentInteraction)){
				Proposition proposition = new Proposition(((Interaction031)activatedInteraction).getPostInteraction().getExperience(), ((Interaction031)activatedInteraction).getWeight() * ((Interaction031)activatedInteraction).getPostInteraction().getValence());
				int index = propositions.indexOf(proposition);
				if (index < 0)
					propositions.add(proposition);
				else
					propositions.get(index).addProclivity(((Interaction031)activatedInteraction).getWeight() * ((Interaction031)activatedInteraction).getPostInteraction().getValence());
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
	
	public Interaction031 enact(Interaction031 intendedInteraction){

		if (intendedInteraction.isPrimitive()){
			Result result = this.reactive.giveResult(intendedInteraction.getExperience());
			return (Interaction031)this.createOrGetPrimitiveInteraction(intendedInteraction.getExperience(), result, 0);
		}			
		else {			
			// Enact the pre interaction
			Interaction031 enactedPreInteraction = enact(intendedInteraction.getPreInteraction());
			if (!enactedPreInteraction.equals(intendedInteraction.getPreInteraction())){
				return enactedPreInteraction;
			}
			else
			{
				// Enact the post interaction
				Interaction031 enactedPostInteraction = enact(intendedInteraction.getPostInteraction());
				if (!enactedPostInteraction.equals(intendedInteraction.getPostInteraction())){
					return enactedPreInteraction;
				}
				return (Interaction031)this.learnCompositeInteraction(enactedPreInteraction, enactedPostInteraction);
			}
		}
	}
}
