package agent;

import java.util.List;
import coupling.Coupling;
import coupling.Coupling2;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction1;

public class Agent2 implements Agent{

	private Coupling2 coupling;
	private Experience experience;
	private Interaction1 preInteraction;
	
	public Agent2(Coupling2 coupling){
		this.coupling = coupling;
		this.experience = coupling.createOrGetExperience(Coupling.LABEL_E1);
	}
	
	public Experience chooseExperience(Result result){

		Interaction1 enactedInteraction  = this.coupling.getInteraction(this.experience.getLabel() + result.getLabel());
		
		if (preInteraction != null)
			this.coupling.createOrReinforceCompositeInteraction(preInteraction, enactedInteraction);

		this.preInteraction = enactedInteraction;

		List<Interaction1> activatedInteractions = this.coupling.getActivatedInteractions(enactedInteraction);
		for (Interaction1 activatedInteraction : activatedInteractions)
			if (activatedInteraction.getPostInteraction().getValence() > 0){
				this.experience = activatedInteraction.getPostInteraction().getExperience();
				System.out.println("propose " + this.experience.getLabel());
			}
			else{
				this.experience = this.coupling.getOtherExperience(activatedInteraction.getPostInteraction().getExperience());						
			}

		return this.experience;
	}
}
