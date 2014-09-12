package agent.decider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tracer.Trace;
import coupling.Coupling5;
import coupling.Experience;
import coupling.interaction.Interaction031;
import org.w3c.dom.*;

public class Decider6 {

	private Coupling5 coupling;

	private Slot slot1 = new Slot(); // Working memory slot used to store the previous interaction
	private Slot slot2 = new Slot(); // Working memory slot used to store the previous super-interaction
	
	public Decider6(Coupling5 coupling){
		this.coupling = coupling;
	}
	
	public Episode6 step(Episode6 episode){
		
		store(episode);
		
		List<Interaction031> flowInteractions = getFlowInteractions();
		Experience experience = flowExperience(flowInteractions);
		
		// TODO propose experiences based on something else than the flow.
					
		return new Episode6(this.coupling, experience);		
	}

	public void store(Episode6 episode){
		
		Element e = Trace.addEventElement("learn");

		Interaction031 superInteraction = null;
		
		if (!this.slot1.isEmpty()){
			// learn the new super-interaction [last current]
			superInteraction = this.coupling.createOrReinforceCompositeInteraction(this.slot1.getInteraction(), episode.getIntendedInteraction());
			Trace.addSubelement(e, "superInteraction" , superInteraction.toString());

			if (episode.getAlternateInteraction() != null){
				Interaction031 superAlternateInteraction = this.coupling.createOrReinforceCompositeInteraction(this.slot1.getInteraction(), episode.getAlternateInteraction());
				Trace.addSubelement(e, "superAlternateInteraction", superAlternateInteraction.toString());
			}
			if (!this.slot2.isEmpty()){
				// So far, Limit the learning to three-step interactions
				//if (//episode.getExperience().isPrimitive() && 
				//	this.slot1.getInteraction().getExperience().isPrimitive()){
					// learn [previous [last current]]
					Interaction031 superLeftInteraction = this.coupling.createOrReinforceCompositeInteraction((Interaction031)this.slot2.getInteraction().getPreInteraction(), superInteraction);
					Trace.addSubelement(e, "superLeftInteraction" , superLeftInteraction.toString());
					// learn [[previous last] current]
					Interaction031 superRightInteraction = this.coupling.createOrReinforceCompositeInteraction(this.slot2.getInteraction(), episode.getIntendedInteraction());	
					Trace.addSubelement(e, "superRightInteraction", superRightInteraction.toString());
				//}
			}
		}
		
		this.slot1.setIntendedInteraction(episode.getIntendedInteraction());
		this.slot2.setInteraction(superInteraction);
	}

	private Experience flowExperience(List<Interaction031> flowInteractions){

		Experience experience = this.coupling.getFirstExperience();
		List<Proposition> propositions = this.coupling.getDefaultAnticipations(); 
		
		for (Interaction031 activatedInteraction : flowInteractions){
			Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
			int index = propositions.indexOf(proposition);
			if (index < 0)
				propositions.add(proposition);
			else
				propositions.get(index).addProclivity(activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
		}
		
		Collections.sort(propositions);

		Element e = Trace.addEventElement("propositions");
		for (Proposition proposition : propositions)
			Trace.addSubelement(e, "proposition", proposition.toString());

		if (propositions.size() > 0)
			experience = propositions.get(0).getExperience();
		
		Trace.addEventElement("select", experience.getLabel());

		return experience;
	}
	
	private List<Interaction031> getFlowInteractions() {
		List<Interaction031> flowInteractions = new ArrayList<Interaction031>();

		Element e = Trace.addEventElement("activations");
		
		for (Interaction031 flowInteraction : this.coupling.getInteractions())
			if (slot2.activate(flowInteraction) ||
				this.slot1.activate(flowInteraction) ||
				this.slot1.getInteraction().getPostInteraction() != null && this.slot1.getInteraction().getPostInteraction().equals(flowInteraction.getPreInteraction())){
				flowInteractions.add(flowInteraction);
				Trace.addSubelement(e, "activate", flowInteraction.toString());
			}
		return flowInteractions;
	}

}
