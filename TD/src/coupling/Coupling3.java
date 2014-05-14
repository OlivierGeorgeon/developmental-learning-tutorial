package coupling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Environments.Environment2;
import Environments.Environment3;
import agent.Agent2;
import agent.decider.Decider3;
import agent.decider.Episode3;
import agent.decider.Proposition;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;
import coupling.interaction.Interaction3;

public class Coupling3 extends Coupling2 {

	@Override
	protected void initCoupling(){
		this.setDecider(new Decider3(this));
		this.setEnvironment(new Environment3(this));

		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createOrGetPrimitiveInteraction(e1, r1, -1);
		createOrGetPrimitiveInteraction(e1, r2, 1);
		createOrGetPrimitiveInteraction(e2, r1, -1);
		createOrGetPrimitiveInteraction(e2, r2, 1);
	}

	public Episode3 createEpisode(Interaction3 interaction) {
		return new Episode3(this, interaction);
	}

	@Override
	protected Interaction3 createNewInteraction(String label, int valence){
		return new Interaction3(label, valence);
	}

	public Experience createOrGetCompositeExperience(Interaction3 compositeInteraction) {
		Experience experience = this.createOrGetExperience(compositeInteraction.getLabel());
		experience.setInteraction(compositeInteraction);
		return experience;	
}

	public Interaction3 createOrGetInteraction(Experience experience,
			Result result, int valence) {
		Interaction3 interaction = (Interaction3)createOrGet(experience.getLabel() + result.getLabel(), valence); 
		interaction.setExperience(experience);
		interaction.setResult(result);
		return interaction;
	}

	public Interaction3 createOrReinforceCompositeInteraction(
			Interaction3 preInteraction, Interaction3 postInteraction) {
			
			Interaction3 interaction = createOrGetCompositeInteraction(preInteraction, postInteraction);
			interaction.incrementWeight();
			return interaction;
		}

	
	public Interaction3 createOrGetCompositeInteraction(
			Interaction3 preInteraction, Interaction3 postInteraction) {
			
			String label = "(" + preInteraction.getLabel() + postInteraction.getLabel() + ")";
			Interaction3 interaction = (Interaction3)getInteraction(label);
			if (interaction == null){
				int valence = preInteraction.getValence() + postInteraction.getValence();	
				interaction = (Interaction3)createOrGet(label, valence); 
				interaction.setPreInteraction(preInteraction);
				interaction.setPostInteraction(postInteraction);
				System.out.println("learn " + interaction.toString());
			}
			else
				interaction.incrementWeight();
			return interaction;
		}
		
	public List<Proposition> getDefaultPropositions(){
		List<Proposition> propositions = new ArrayList<Proposition>();
		for (Experience experience : this.getExperiences()){
			if (experience.isPrimitive()){
				Proposition proposition = new Proposition(experience, 0);
				propositions.add(proposition);
			}
		}
		return propositions;
	}
	
	public Experience propose(Interaction3 interaction){

		Experience experience = this.getOtherInteraction(null).getExperience();

		List<Proposition> propositions = this.getPropositions(interaction);

		if (propositions.size() > 0){
			Collections.sort(propositions);
			for (Proposition proposition : propositions)
				System.out.println("propose " + proposition.toString());
			Proposition selectedProposition = propositions.get(0);
			experience = selectedProposition.getExperience();
		}	
		
		System.out.println("select " + experience.getLabel());

		return experience;
	}
	
	protected List<Proposition> getPropositions(Interaction3 interaction){
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
	
	@Override
	public Obtention produceObtention(Intention intention) {
		Experience experience = ((Intention3)intention).getInteraction().getExperience();
		Result result = this.getEnvironment().provideObtention(experience);
		return new Obtention3(this.createOrGetInteraction(experience, result, 0));
	}
	
}
