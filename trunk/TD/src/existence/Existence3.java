package existence;

import java.util.ArrayList;
import java.util.List;
import coupling.Obtention2;
import reactive.Environment3;
import tracer.Trace;
import agent.decider.Decider3;
import agent.decider.Proposition;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;
import coupling.interaction.Interaction3;

public class Existence3 extends Existence2 {

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
	protected Interaction3 createNewInteraction(String label, int valence){
		return new Interaction3(label, valence);
	}

	public Interaction3 createOrReinforceCompositeInteraction(Interaction preInteraction, Interaction postInteraction) {
			
			Interaction3 interaction = (Interaction3)createOrGetCompositeInteraction(preInteraction, postInteraction);
			interaction.incrementWeight();
			return interaction;
		}

    public Interaction3 createOrGetInteraction(Experience experience,
            Result result, int valence) {
    Interaction3 interaction = (Interaction3)createOrGet(experience.getLabel() + result.getLabel(), valence);
    interaction.setExperience(experience);
    interaction.setResult(result);
    return interaction;
}

	public List<Proposition> getPropositions(){
		List<Proposition> propositions = this.getDefaultPropositions(); 
		
		if (this.currentInteraction != null){
			for (Interaction2 activatedInteraction : getActivatedInteractions(this.currentInteraction)){
				Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), ((Interaction3)activatedInteraction).getWeight() * activatedInteraction.getPostInteraction().getValence());
				int index = propositions.indexOf(proposition);
				if (index < 0)
					propositions.add(proposition);
				else
					propositions.get(index).addProclivity(((Interaction3)activatedInteraction).getWeight() * activatedInteraction.getPostInteraction().getValence());
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
}
