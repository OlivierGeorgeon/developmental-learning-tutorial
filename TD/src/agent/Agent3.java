package agent;

import java.util.Collections;
import java.util.List;
import agent.decider.Episode;
import agent.decider.Episode3;
import agent.decider.Proposition;
import coupling.Coupling3;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction3;

public class Agent3 implements Agent{

	private Coupling3 coupling;
	private Episode episode;
	
	public Agent3(Coupling3 coupling){
		this.coupling = coupling;
		this.episode = coupling.createEpisode();
	}
	
	public Experience chooseExperience(Result result){

		Interaction3 enactedInteraction = this.coupling.getInteraction(this.episode.getExperience().getLabel() + result.getLabel());
		this.episode.store(enactedInteraction);		

		this.episode = this.episode.createNext();
		
		List<Proposition> propositions = this.episode.getPropositions();
		
		if (propositions.size() > 0){
			Collections.sort(propositions);
			for (Proposition proposition : propositions)
				System.out.println("propose " + proposition.toString());
			Proposition selectedProposition = propositions.get(0);
			if (selectedProposition.getProclivity() >= 0 || propositions.size() > 1)
				this.episode.setExperience(selectedProposition.getExperience());
			else
				this.episode.setExperience(this.coupling.getOtherExperience(selectedProposition.getExperience()));
		}			
		
		return this.episode.getExperience();
	}
	
	protected Episode createEpisode(){
		return new Episode3(this.coupling);
	}	
}
