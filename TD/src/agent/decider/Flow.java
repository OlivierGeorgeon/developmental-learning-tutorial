package agent.decider;

import java.util.ArrayList;
import java.util.List;

import coupling.Coupling3;
import coupling.Experience;
import coupling.interaction.Interaction3;

public class Flow {
	
	private Coupling3 coupling;
	
	private Experience experience;

	Interaction3 enactedInteraction;
	
	List<Interaction3> contextInteractions = new ArrayList<Interaction3>();
	
	public Flow(Coupling3 coupling){
		this.coupling = coupling;
		this.experience = this.coupling.createOrGetExperience(Coupling3.LABEL_E1);
	}

	public Interaction3 getEnactedInteraction() {
		return enactedInteraction;
	}

	public List<Interaction3> getContextInteractions() {
		return contextInteractions;
	}

	public void setContextInteractions(List<Interaction3> contextInteractions) {
		this.contextInteractions = contextInteractions;
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	
	public void learn(Interaction3 enactedInteraction){
		if (this.enactedInteraction != null)
			this.coupling.createOrReinforceCompositeInteraction(this.enactedInteraction, enactedInteraction);	
		this.enactedInteraction = enactedInteraction;
		
		this.contextInteractions = new ArrayList<Interaction3>();
		this.contextInteractions.add(enactedInteraction);
	}
	
	public List<Proposition> getPropositions(){
		List<Proposition> propositions = new ArrayList<Proposition>(); 
		for (Interaction3 activatedInteraction : getActivatedInteractions()){
			Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
			int index = propositions.indexOf(proposition);
			if (index < 0)
				propositions.add(proposition);
			else
				propositions.get(index).addProclivity(activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
		}
		return propositions;
	}
	
	private List<Interaction3> getActivatedInteractions() {
		List<Interaction3> activatedInteractions = new ArrayList<Interaction3>();
		for (Interaction3 activatedInteraction : this.coupling.getInteractions())
			if (contextInteractions.contains(activatedInteraction.getPreInteraction()))
				activatedInteractions.add(activatedInteraction);
		return activatedInteractions;
	}
}
