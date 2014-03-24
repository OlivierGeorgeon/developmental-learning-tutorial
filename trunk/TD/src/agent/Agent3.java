package agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.decider.Flow;
import agent.decider.Proposition;
import coupling.Coupling3;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction3;

public class Agent3 implements Agent{

	private Coupling3 coupling;
	private Flow flow;
	
	public Agent3(Coupling3 coupling){
		this.coupling = coupling;
		this.flow = new Flow(this.coupling);
	}
	
	public Experience chooseExperience(Result result){

		Flow newFlow = new Flow(this.flow);
		
		Experience preExperience = this.flow.getExperience();
		Interaction3 enactedInteraction = this.coupling.getInteraction(preExperience.getLabel() + result.getLabel());
		this.flow.learn(enactedInteraction);		

		List<Interaction3> contextInteractions = new ArrayList<Interaction3>();
		contextInteractions.add(enactedInteraction);

		newFlow.setExperience(preExperience);
		newFlow.setContextInteractions(contextInteractions);
		newFlow.setEnactedInteraction(enactedInteraction);
		
		List<Proposition> propositions = newFlow.getPropositions();
		
		if (propositions.size() > 0){
			Collections.sort(propositions);
			for (Proposition proposition : propositions)
				System.out.println("propose " + proposition.toString());
			Proposition selectedProposition = propositions.get(0);
			if (selectedProposition.getProclivity() >= 0 || propositions.size() > 1)
				newFlow.setExperience(selectedProposition.getExperience());
			else
				newFlow.setExperience(this.coupling.getOtherExperience(selectedProposition.getExperience()));
		}			
		
		this.flow = newFlow;
		return this.flow.getExperience();
	}
}
