package agent;

import java.util.ArrayList;
import java.util.List;

import tracer.Trace;
import agent.decider.Episode2;
import coupling.Coupling2;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;

public class Agent2 implements Agent{

	private Coupling2 coupling;
	private Episode2 contextEpisode;
	private Episode2 currentEpisode;
	
	public Agent2(Coupling2 coupling){
		this.coupling = coupling;
	}
	
	public Experience chooseExperience(Result result){

		if (result != null)
			this.currentEpisode.record(result);
		
		if (this.contextEpisode != null )
			this.coupling.createCompositeInteraction((Interaction2)this.contextEpisode.getInteraction(), (Interaction2)this.currentEpisode.getInteraction());

		List<Interaction> proposedInteractions = new ArrayList<Interaction>();
		if (this.currentEpisode != null)
			proposedInteractions = this.coupling.proposeInteractions((Interaction2)this.currentEpisode.getInteraction()); 

		Interaction intendedInteraction = this.coupling.getOtherInteraction(null);
		if (proposedInteractions.size() > 0)
			if (proposedInteractions.get(0).getValence() >= 0)
				intendedInteraction = proposedInteractions.get(0);
			else
				intendedInteraction = (Interaction2)this.coupling.getOtherInteraction(proposedInteractions.get(0));

		
		if (this.currentEpisode.getInteraction() != null)
			this.contextEpisode = this.currentEpisode;

		this.currentEpisode = this.coupling.createEpisode(intendedInteraction);
		Trace.addEventElement("intend", intendedInteraction.toString());

		return intendedInteraction.getExperience();
	}
}
