package agent;

import java.util.Collections;
import java.util.List;

import agent.decider.Flow;
import agent.decider.Proposition;
import coupling.Coupling;
import coupling.Experience;
import coupling.Result;

public class Agent3 implements Agent{

	private Coupling coupling;
	private Flow flow;
	
	public Agent3(Coupling coupling){
		this.coupling = coupling;
		this.flow = new Flow(this.coupling);
		this.flow.setExperience(this.coupling.createOrGetExperience(Coupling.LABEL_E1));
	}
	
	public Experience chooseExperience(Result result){

		this.flow.learn(this.coupling.getInteraction(this.flow.getExperience().getLabel() + result.getLabel()));		

		List<Proposition> propositions = this.flow.getPropositions();
		
		if (propositions.size() > 0){
			Collections.sort(propositions);
			for (Proposition proposition : propositions)
				System.out.println("propose " + proposition.toString());
			Proposition selectedProposition = propositions.get(0);
			if (selectedProposition.getProclivity() >= 0 || propositions.size() > 1)
				this.flow.setExperience(selectedProposition.getExperience());
			else
				this.flow.setExperience(this.coupling.getOtherExperience(selectedProposition.getExperience()));
		}			
		
		return this.flow.getExperience();
	}
}
