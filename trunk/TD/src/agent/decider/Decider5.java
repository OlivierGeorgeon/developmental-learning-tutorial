package agent.decider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import coupling.Coupling5;
import coupling.Experience;
import coupling.interaction.Interaction3;

public class Decider5 {
	
	private Coupling5 coupling;
	
	private Interaction3 interaction_1;
	private Interaction3 interaction_2;
	private Interaction3 superInteraction;

	public Decider5(Coupling5 coupling){
		this.coupling = coupling;
	}
	
	public Episode4 step(Episode4 episode){
		this.store(episode);
		
		Episode4 nextEpisode;
		if (episode == null)
			nextEpisode = new Episode4(this.coupling, this.coupling.getFirstExperience());
		else
			nextEpisode = this.chooseEpisode(episode.getInteraction());
		
		return nextEpisode;
	}

	public void store(Episode4 currentEpisode){
		if (this.interaction_1 != null){
			// learn [previous current]
			Interaction3 superInteraction_1 = this.superInteraction;
			this.superInteraction = this.coupling.createOrReinforceCompositeInteraction(this.interaction_1, currentEpisode.getInteraction());

			if (currentEpisode.getExperience().isPrimitive() && 
				this.interaction_1.getExperience().isPrimitive() &&	
				this.interaction_2 != null ){
				// learn [penultimate [previous current]]
				this.coupling.createOrReinforceCompositeInteraction(this.interaction_2, this.superInteraction);
				// learn [[penultimate previous] current]
				this.coupling.createOrReinforceCompositeInteraction(superInteraction_1, currentEpisode.getInteraction());	
			
				//if (contextEpisode.getContextEpisode().getSuperInteraction() != null)
				//	this.createOrReinforceCompositeInteraction(contextEpisode.getContextEpisode().getSuperInteraction(), superInteraction);			
			}
		}
	}

	public Episode4 chooseEpisode(Interaction3 interaction) {
		
		Experience experience = propose(interaction);
		
		this.interaction_2 = this.interaction_1;
		this.interaction_1 = interaction;

		return new Episode4(this.coupling, experience);
	}

	private Experience propose(Interaction3 episode){

		Experience experience = this.coupling.getFirstExperience();

		List<Proposition> propositions = this.getPropositions(episode);

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
	
	private List<Proposition> getPropositions(Interaction3 interaction){
		List<Proposition> propositions = this.coupling.getDefaultPropositions(); 
			
		if (interaction != null){
			for (Interaction3 activatedInteraction : getActivatedInteractions(interaction)){
				Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
				int index = propositions.indexOf(proposition);
				if (index < 0)
					propositions.add(proposition);
				else
					propositions.get(index).addProclivity(activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
			}
		}
		return propositions;
	}

	private List<Interaction3> getActivatedInteractions(Interaction3 interaction) {
		List<Interaction3> activatedInteractions = new ArrayList<Interaction3>();
	
		for (Interaction3 activatedInteraction : this.coupling.getInteractions())
			if (this.superInteraction != null && this.superInteraction.equals(activatedInteraction.getPreInteraction()) ||
				interaction != null && interaction.equals(activatedInteraction.getPreInteraction()) ||
				interaction.getPostInteraction() != null && interaction.getPostInteraction().equals(activatedInteraction.getPreInteraction())){
				activatedInteractions.add(activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}

}
