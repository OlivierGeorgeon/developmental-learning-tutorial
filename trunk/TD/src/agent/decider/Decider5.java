package agent.decider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import coupling.Coupling5;
import coupling.Experience;
import coupling.interaction.Interaction3;

public class Decider5 {

	private Coupling5 coupling;

	private Slot slot1 = new Slot(); // previous interaction
	private Slot slot2 = new Slot(); // previous super-interaction
	
	public Decider5(Coupling5 coupling){
		this.coupling = coupling;
	}
	
	public Episode4 step(Episode4 episode){
		
		store(episode);
			
		return new Episode4(this.coupling, propose());		
	}

	public void store(Episode4 episode){
		
		Interaction3 superInteraction = null;
		
		if (!this.slot1.isEmpty()){
			// learn the new super-interaction [last current]
			superInteraction = this.coupling.createOrReinforceCompositeInteraction(this.slot1.getInteraction(), episode.getInteraction());

			if (episode.getExperience().isPrimitive() && 
				this.slot1.getInteraction().getExperience().isPrimitive() &&	
				!this.slot2.isEmpty()){
				// learn [previous [last current]]
				this.coupling.createOrReinforceCompositeInteraction(this.slot2.getInteraction().getPreInteraction(), superInteraction);
				// learn [[previous last] current]
				this.coupling.createOrReinforceCompositeInteraction(this.slot2.getInteraction(), episode.getInteraction());	
			}
		}
		
		this.slot1.setInteraction(episode.getInteraction());
		this.slot2.setInteraction(superInteraction);
	}

	private Experience propose(){

		Experience experience = this.coupling.getFirstExperience();

		List<Proposition> propositions = getPropositions();

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
	
	private List<Proposition> getPropositions(){
		List<Proposition> propositions = this.coupling.getDefaultPropositions(); 
			
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
			if (slot2.activate(activatedInteraction) ||
				this.slot1.activate(activatedInteraction) ||
				this.slot1.getInteraction().getPostInteraction() != null && this.slot1.getInteraction().getPostInteraction().equals(activatedInteraction.getPreInteraction())){
				activatedInteractions.add(activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}

}
