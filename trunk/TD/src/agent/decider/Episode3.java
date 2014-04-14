package agent.decider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import coupling.Coupling;
import coupling.Coupling3;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction3;

public class Episode3 implements Episode{
	
	private Coupling3 coupling;
	private Experience experience;
	private Interaction3 interaction;

	public Episode3(Coupling3 coupling, Experience experience){
		this.coupling = coupling;
		this.experience = experience;
	}

	public void record(Result result){
		this.interaction  = this.coupling.getInteraction(this.experience.getLabel() + result.getLabel());
	}
	
	public Interaction3 getInteraction() {
		return interaction;
	}

	public Experience propose(){

		Experience experience = coupling.createOrGetExperience(Coupling.LABEL_E1);

		List<Proposition> propositions = this.getPropositions();

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
	
	protected List<Proposition> getPropositions(){
		List<Proposition> propositions = this.getCoupling().getDefaultPropositions();//new ArrayList<Proposition>(); 
				
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
	
	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	
	public Experience getExperience() {
		return experience;
	}

	protected Coupling3 getCoupling(){
		return this.coupling;
	}
	
	protected List<Interaction3> getActivatedInteractions() {
		List<Interaction3> activatedInteractions = new ArrayList<Interaction3>();
		for (Interaction3 activatedInteraction : this.coupling.getInteractions())
			if (this.interaction != null && this.interaction.equals(activatedInteraction.getPreInteraction())){
				activatedInteractions.add(activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}
	
	protected void setInteraction(Interaction3 interaction){
		this.interaction = interaction;
	}
}
