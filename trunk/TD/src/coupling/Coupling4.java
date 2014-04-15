package coupling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.decider.Episode4;
import agent.decider.Proposition;
import coupling.interaction.Interaction3;

public class Coupling4 extends Coupling3 {

	private Interaction3 interaction_1;
	private Interaction3 interaction_2;
	private Interaction3 superInteraction;

	public Episode4 chooseEpisode(Interaction3 interaction) {
		
		Experience experience = propose(interaction);
		
		this.interaction_2 = this.interaction_1;
		this.interaction_1 = interaction;

		return new Episode4(this, experience);
	}

	@Override
	public Interaction3 createOrReinforceCompositeInteraction(
			Interaction3 preInteraction, Interaction3 postInteraction) {
			
			String label = "(" + preInteraction.getLabel() + postInteraction.getLabel() + ")";
			Interaction3 interaction = getInteraction(label);
			if (interaction == null){
				int valence = preInteraction.getValence() + postInteraction.getValence();	
				interaction = this.createOrGet(label, valence); 
				interaction.setPreInteraction(preInteraction);
				interaction.setPostInteraction(postInteraction);
				interaction.incrementWeight();
				interaction.setExperience(this.createOrGetCompositeExperience(interaction));
				interaction.setResult(this.createOrGetResult(label));
				System.out.println("learn " + interaction.toString());
			}
			else
				interaction.incrementWeight();
			return interaction;
		}

	public void store(Episode4 currentEpisode){
		if (this.interaction_1 != null){
			// learn [previous current]
			Interaction3 superInteraction_1 = this.superInteraction;
			this.superInteraction = this.createOrReinforceCompositeInteraction(this.interaction_1, currentEpisode.getInteraction());

			if (currentEpisode.getExperience().isPrimitive() && 
				this.interaction_1.getExperience().isPrimitive() &&	
				this.interaction_2 != null ){
				// learn [penultimate [previous current]]
				this.createOrReinforceCompositeInteraction(this.interaction_2, this.superInteraction);
				// learn [[penultimate previous] current]
				this.createOrReinforceCompositeInteraction(superInteraction_1, currentEpisode.getInteraction());	
			
				//if (contextEpisode.getContextEpisode().getSuperInteraction() != null)
				//	this.createOrReinforceCompositeInteraction(contextEpisode.getContextEpisode().getSuperInteraction(), superInteraction);			
			}
		}
	}
	
	private Experience propose(Interaction3 episode){

		Experience experience = this.getFirstExperience();

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
	
	protected List<Proposition> getPropositions(Interaction3 interaction){
		List<Proposition> propositions = this.getDefaultPropositions(); 
			
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
	
		for (Interaction3 activatedInteraction : this.getInteractions())
			if (this.superInteraction != null && this.superInteraction.equals(activatedInteraction.getPreInteraction()) ||
				interaction != null && interaction.equals(activatedInteraction.getPreInteraction()) ||
				interaction.getPostInteraction() != null && interaction.getPostInteraction().equals(activatedInteraction.getPreInteraction())){
				activatedInteractions.add(activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}

}
