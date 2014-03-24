package agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import agent.decider.Proposition;
import coupling.Coupling;
import coupling.Coupling3;
import coupling.Experience;
import coupling.Interaction;
import coupling.Result;

public class Agent3 implements Agent{

	private Coupling3 coupling;
	private Experience experience;
	private Interaction preInteraction;
	
	public Agent3(Coupling3 coupling){
		this.coupling = coupling;
		this.experience = coupling.createOrGetExperience(Coupling.LABEL_E1);
	}
	
	public Experience chooseExperience(Result result){

		Interaction enactedInteraction  = this.coupling.getInteraction(this.experience.getLabel() + result.getLabel());
		
		if (preInteraction != null)
			this.coupling.createOrReinforceCompositeInteraction(preInteraction, enactedInteraction);
		
		this.preInteraction = enactedInteraction;

		List<Interaction> contextInteractions = new ArrayList<Interaction>();
		contextInteractions.add(enactedInteraction);
		List<Interaction> activatedInteractions = this.coupling.getActivatedInteractions(contextInteractions);
		
		List<Proposition> propositions = new ArrayList<Proposition>(); 
		for (Interaction activatedInteraction : activatedInteractions){
			Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
			int index = propositions.indexOf(proposition);
			if (index < 0)
				propositions.add(proposition);
			else
				propositions.get(index).addProclivity(activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
		}
		
		if (propositions.size() > 0){
			Collections.sort(propositions);
			for (Proposition proposition : propositions)
				System.out.println("propose " + proposition.toString());
			Proposition selectedProposition = propositions.get(0);
			if (selectedProposition.getProclivity() >= 0 || propositions.size() > 1)
				this.experience = selectedProposition.getExperience();
			else
				this.experience = this.coupling.getOtherExperience(selectedProposition.getExperience());
				//this.experience = this.coupling.getOtherExperience(this.experience);
		}			
		
		return this.experience;
	}
}
