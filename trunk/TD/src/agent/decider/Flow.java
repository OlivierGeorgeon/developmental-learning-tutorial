package agent.decider;

import java.util.ArrayList;
import java.util.List;

import coupling.Coupling;
import coupling.Experience;
import coupling.Interaction;

public class Flow {
	
	private Coupling coupling;
	
	private Experience experience;

	Interaction enactedInteraction;
	
	List<Interaction> contextInteractions = new ArrayList<Interaction>();
	
	public Flow(Coupling coupling){
		this.coupling = coupling;
		this.experience = this.coupling.createOrGetExperience(Coupling.LABEL_E1);
	}

	public Interaction getEnactedInteraction() {
		return enactedInteraction;
	}

	public List<Interaction> getContextInteractions() {
		return contextInteractions;
	}

	public void setContextInteractions(List<Interaction> contextInteractions) {
		this.contextInteractions = contextInteractions;
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	
	public void learn(Interaction enactedInteraction){
		if (this.enactedInteraction != null)
			this.coupling.createOrReinforceCompositeInteraction(this.enactedInteraction, enactedInteraction);	
		this.enactedInteraction = enactedInteraction;
		
		this.contextInteractions = new ArrayList<Interaction>();
		this.contextInteractions.add(enactedInteraction);
	}
	
	public List<Proposition> getPropositions(){
		List<Proposition> propositions = new ArrayList<Proposition>(); 
		for (Interaction activatedInteraction : getActivatedInteractions()){
			Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
			int index = propositions.indexOf(proposition);
			if (index < 0)
				propositions.add(proposition);
			else
				propositions.get(index).addProclivity(activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
		}
		return propositions;
	}
	
	private List<Interaction> getActivatedInteractions() {
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		for (Interaction activatedInteraction : this.coupling.getInteractions())
			if (contextInteractions.contains(activatedInteraction.getPreIntearction()))
				activatedInteractions.add(activatedInteraction);
		return activatedInteractions;
	}
}
