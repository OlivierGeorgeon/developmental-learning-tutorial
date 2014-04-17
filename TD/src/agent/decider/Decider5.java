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
	
	public Episode5 step(Episode5 episode){
		
		store(episode);
		
		List<Interaction3> flowInteractions = getFlowInteractions();
		Experience experience = flowExperience(flowInteractions);
		
		// TODO propose experiences based on something else than the flow.
					
		return new Episode5(this.coupling, experience);		
	}

	public void store(Episode5 episode){
		
		Interaction3 superInteraction = null;
		
		if (!this.slot1.isEmpty()){
			// learn the new super-interaction [last current]
			superInteraction = this.coupling.createOrReinforceCompositeInteraction(this.slot1.getInteraction(), episode.getInteraction());

			if (episode.getAlternateInteraction() != null)
				this.coupling.createOrReinforceCompositeInteraction(this.slot1.getInteraction(), episode.getAlternateInteraction());
			
			if (!this.slot2.isEmpty()){
				// So far, Limit the learning to three-step interactions
				//if (episode.getExperience().isPrimitive() && 
				//	this.slot1.getInteraction().getExperience().isPrimitive()){
					// learn [previous [last current]]
					this.coupling.createOrReinforceCompositeInteraction(this.slot2.getInteraction().getPreInteraction(), superInteraction);
					// learn [[previous last] current]
					this.coupling.createOrReinforceCompositeInteraction(this.slot2.getInteraction(), episode.getInteraction());	
				//}
			}
		}
		
		this.slot1.setInteraction(episode.getInteraction());
		this.slot2.setInteraction(superInteraction);
	}

	private Experience flowExperience(List<Interaction3> flowInteractions){

		Experience experience = this.coupling.getFirstExperience();
		List<Proposition> propositions = this.coupling.getDefaultPropositions(); 
		
		for (Interaction3 activatedInteraction : flowInteractions){
			Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
			int index = propositions.indexOf(proposition);
			if (index < 0)
				propositions.add(proposition);
			else
				propositions.get(index).addProclivity(activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
		}
		
		Collections.sort(propositions);

		for (Proposition proposition : propositions)
			System.out.println("propose " + proposition.toString());

		if (propositions.size() > 0)
			experience = propositions.get(0).getExperience();
		
		System.out.println("select " + experience.getLabel());

		return experience;
	}
	
	private List<Interaction3> getFlowInteractions() {
		List<Interaction3> flowInteractions = new ArrayList<Interaction3>();
	
		for (Interaction3 flowInteraction : this.coupling.getInteractions())
			if (slot2.activate(flowInteraction) ||
				this.slot1.activate(flowInteraction) ||
				this.slot1.getInteraction().getPostInteraction() != null && this.slot1.getInteraction().getPostInteraction().equals(flowInteraction.getPreInteraction())){
				flowInteractions.add(flowInteraction);
				System.out.println("activated " + flowInteraction.toString());
			}
		return flowInteractions;
	}

}
