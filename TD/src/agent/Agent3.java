package agent;

import agent.decider.Episode3;
import coupling.Coupling3;
import coupling.Experience;
import coupling.Result;

public class Agent3 implements Agent{

	private Coupling3 coupling;
	private Episode3 contextEpisode;
	private Episode3 currentEpisode;
	
	public Agent3(Coupling3 coupling){
		this.coupling = coupling;
		this.currentEpisode = this.coupling.createEpisode(this.coupling.getFirstExperience());
	}
	
	public Experience chooseExperience(Result result){

		if (result != null)
			this.currentEpisode.record(result);

		if (this.contextEpisode != null )
			this.coupling.createOrReinforceCompositeInteraction(this.contextEpisode.getInteraction(), this.currentEpisode.getInteraction());
		
		Experience experience = this.coupling.propose(this.currentEpisode); 
			
		if (this.currentEpisode.getInteraction() != null)
			this.contextEpisode = this.currentEpisode;

		this.currentEpisode = this.coupling.createEpisode(experience);
				
		return experience;
	}
	
}
