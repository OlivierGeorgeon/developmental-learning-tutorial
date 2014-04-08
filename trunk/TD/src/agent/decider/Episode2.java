package agent.decider;

import java.util.ArrayList;
import java.util.List;
import coupling.Coupling;
import coupling.Coupling2;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction2;

public class Episode2 implements Episode{

	private Coupling2 coupling;
	private Experience experience;
	private Interaction2 interaction;
	
	public Episode2(Coupling2 coupling, Experience experience){
		this.coupling = coupling;
		this.experience = experience;
	}

	public void record(Result result){
		this.interaction  = this.coupling.getInteraction(this.experience.getLabel() + result.getLabel());
	}
	
	public Interaction2 getInteraction() {
		return interaction;
	}

	public Experience propose(){
		Experience experience = coupling.createOrGetExperience(Coupling.LABEL_E1);
		for (Interaction2 activatedInteraction : this.getActivatedInteractions())
			if (activatedInteraction.getPostInteraction().getValence() > 0){
				experience = activatedInteraction.getPostInteraction().getExperience();
				System.out.println("propose " + experience.getLabel());
			}
			else{
				experience = this.coupling.getOtherExperience(activatedInteraction.getPostInteraction().getExperience());						
			}
		return experience;
	}
	
	protected List<Interaction2> getActivatedInteractions() {
		List<Interaction2> activatedInteractions = new ArrayList<Interaction2>();
		if (this.interaction != null)
			for (Interaction2 activatedInteraction : this.coupling.getInteractions())
				if (this.interaction == activatedInteraction.getPreInteraction())
					activatedInteractions.add(activatedInteraction);
		return activatedInteractions;
	}

	protected Coupling2 getCoupling() {
		return coupling;
	}
}
