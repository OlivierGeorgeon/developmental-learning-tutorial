package reactive;

import java.util.ArrayList;
import java.util.List;
import Environments.Environment3;
import agent.decider.Decider3;
import agent.decider.Proposition;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction2;
import coupling.interaction.Interaction3;

public class Existence3 extends Existence2 {

	@Override
	protected void initExistence(){
		this.decider = new Decider3(this);
		this.environment = new Environment3(this);

		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createOrGetPrimitiveInteraction(e1, r1, -1);
		createOrGetPrimitiveInteraction(e1, r2, 1);
		createOrGetPrimitiveInteraction(e2, r1, -1);
		createOrGetPrimitiveInteraction(e2, r2, 1);
	}

	protected Interaction3 createNewInteraction(String label, int valence){
		return new Interaction3(label, valence);
	}

	public Interaction3 createOrReinforceCompositeInteraction(
			Interaction3 preInteraction, Interaction3 postInteraction) {
			
			Interaction3 interaction = (Interaction3)createOrGetCompositeInteraction(preInteraction, postInteraction);
			interaction.incrementWeight();
			return interaction;
		}

	public List<Proposition> getPropositions(Interaction3 interaction){
		List<Proposition> propositions = this.getDefaultPropositions(); 
				
		for (Interaction2 activatedInteraction : getActivatedInteractions(interaction)){
			Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), ((Interaction3)activatedInteraction).getWeight() * activatedInteraction.getPostInteraction().getValence());
			int index = propositions.indexOf(proposition);
			if (index < 0)
				propositions.add(proposition);
			else
				propositions.get(index).addProclivity(((Interaction3)activatedInteraction).getWeight() * activatedInteraction.getPostInteraction().getValence());
		}
		return propositions;
	}

	public List<Proposition> getDefaultPropositions(){
		List<Proposition> propositions = new ArrayList<Proposition>();
		for (Experience experience : this.EXPERIENCES.values()){
			if (experience.isPrimitive()){
				Proposition proposition = new Proposition(experience, 0);
				propositions.add(proposition);
			}
		}
		return propositions;
	}
	

}
