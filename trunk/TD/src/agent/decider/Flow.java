package agent.decider;

import java.util.ArrayList;
import java.util.List;

import coupling.Coupling;
import coupling.Experience;
import coupling.interaction.Interaction1;

public class Flow {
	
	private Coupling coupling;
	
	private Experience experience;

	Interaction1 enactedInteraction;
	
	List<Interaction1> contextInteractions = new ArrayList<Interaction1>();
	
	public Flow(Coupling coupling){
		this.coupling = coupling;
		this.experience = this.coupling.createOrGetExperience(Coupling.LABEL_E1);
	}

	public Interaction1 getEnactedInteraction() {
		return enactedInteraction;
	}

	public List<Interaction1> getContextInteractions() {
		return contextInteractions;
	}

	public void setContextInteractions(List<Interaction1> contextInteractions) {
		this.contextInteractions = contextInteractions;
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	
	public void learn(Interaction1 enactedInteraction){
		if (this.enactedInteraction != null)
			this.coupling.createOrReinforceCompositeInteraction(this.enactedInteraction, enactedInteraction);	
		this.enactedInteraction = enactedInteraction;
		
		this.contextInteractions = new ArrayList<Interaction1>();
		this.contextInteractions.add(enactedInteraction);
	}
	
	public List<Proposition> getPropositions(){
		List<Proposition> propositions = new ArrayList<Proposition>(); 
		for (Interaction1 activatedInteraction : getActivatedInteractions()){
			Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
			int index = propositions.indexOf(proposition);
			if (index < 0)
				propositions.add(proposition);
			else
				propositions.get(index).addProclivity(activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
		}
		return propositions;
	}
	
	private List<Interaction1> getActivatedInteractions() {
		List<Interaction1> activatedInteractions = new ArrayList<Interaction1>();
		for (Interaction1 activatedInteraction : this.coupling.getInteractions())
			if (contextInteractions.contains(activatedInteraction.getPreIntearction()))
				activatedInteractions.add(activatedInteraction);
		return activatedInteractions;
	}
}
