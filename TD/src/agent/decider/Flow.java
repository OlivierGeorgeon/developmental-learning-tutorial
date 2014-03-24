package agent.decider;

import java.util.ArrayList;
import java.util.List;

import coupling.Experience;
import coupling.Interaction;

public class Flow {
	
	private Experience experience;

	Interaction enactedInteraction;
	
	List<Interaction> contextInteractions = new ArrayList<Interaction>();

	public Interaction getEnactedInteraction() {
		return enactedInteraction;
	}

	public void setEnactedInteraction(Interaction enactedInteraction) {
		this.enactedInteraction = enactedInteraction;
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
}
