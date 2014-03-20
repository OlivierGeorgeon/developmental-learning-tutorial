package agent;

import java.util.List;
import coupling.Coupling;
import coupling.Experience;
import coupling.Interaction;
import coupling.Result;

public class Agent3 implements Agent{

	private Coupling coupling;
	private Experience experience;
	private Interaction preInteraction;
	
	public Agent3(Coupling coupling){
		this.coupling = coupling;
		this.experience = coupling.createOrGetExperience(Coupling.LABEL_E1);
	}
	
	public Experience chooseExperience(Result result){

		Interaction enactedInteraction  = this.coupling.getInteraction(this.experience.getLabel() + result.getLabel());
		
		if (preInteraction != null)
			this.coupling.createOrReinforceCompositeInteraction(preInteraction, enactedInteraction);

		this.preInteraction = enactedInteraction;

		List<Interaction> activatedInteractions = this.coupling.getActivatedInteractions(enactedInteraction);
		for (Interaction activatedInteraction : activatedInteractions)
			if (activatedInteraction.getPostInteraction().getValue() > 0){
				this.experience = activatedInteraction.getPostInteraction().getExperience();
				System.out.println("propose " + this.experience.getLabel());
			}
			else{
				this.experience = this.coupling.getOtherExperience(activatedInteraction.getPostInteraction().getExperience());						
			}

		return this.experience;
	}
}
