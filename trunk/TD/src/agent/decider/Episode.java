package agent.decider;

import java.util.ArrayList;
import java.util.List;

import coupling.Coupling3;
import coupling.Experience;
import coupling.interaction.Interaction3;

public class Episode {
	
	private Coupling3 coupling;
	
	private Experience experience;

	Episode contextEpisode;

	List<Interaction3> enactedInteractions = new ArrayList<Interaction3>();
	
	public Episode(Coupling3 coupling){
		this.coupling = coupling;
		this.experience = this.coupling.createOrGetExperience(Coupling3.LABEL_E1);
	}

	public Episode(Episode episode){
		this.coupling = episode.coupling;
		this.experience = episode.experience;
		
		episode.setContextEpisode(null);
		this.contextEpisode = episode;
	}
	
	public List<Interaction3> getEnactedInteractions() {
		return enactedInteractions;
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	
	public void store(Interaction3 enactedInteraction){
		this.enactedInteractions = new ArrayList<Interaction3>();
		this.enactedInteractions.add(enactedInteraction);

		if (contextEpisode!= null )
			for (Interaction3 contextInteraction : contextEpisode.getEnactedInteractions()){
				Interaction3 interaction = this.coupling.createOrReinforceCompositeInteraction(contextInteraction, enactedInteraction);
				if (contextInteraction.getPreInteraction() == null)
					this.enactedInteractions.add(interaction);
			}
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
			if (contextEpisode.getEnactedInteractions().contains(activatedInteraction.getPreInteraction())){
				activatedInteractions.add(activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}

	public Episode getContextEpisode() {
		return contextEpisode;
	}

	public void setContextEpisode(Episode contextEpisode) {
		this.contextEpisode = contextEpisode;
	}
}
