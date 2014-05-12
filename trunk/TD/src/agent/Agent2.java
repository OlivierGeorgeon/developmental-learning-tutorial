package agent;

import agent.decider.Episode2;
import coupling.Coupling2;
import coupling.Experience;
import coupling.Result;

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
			this.coupling.createCompositeInteraction(this.contextEpisode.getInteraction(), this.currentEpisode.getInteraction());

		Experience experience;
		if (this.currentEpisode != null)
			experience = this.coupling.getOtherExperience(null);
		else			
			experience = this.coupling.propose(this.currentEpisode); 
				
		if (this.currentEpisode.getInteraction() != null)
			this.contextEpisode = this.currentEpisode;

		this.currentEpisode = this.coupling.createEpisode(experience);
		
		return experience;
	}
}
