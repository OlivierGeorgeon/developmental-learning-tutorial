package agent;

import agent.decider.Episode2;
import coupling.Coupling;
import coupling.Coupling2;
import coupling.Experience;
import coupling.Result;

public class Agent2 implements Agent{

	private Coupling2 coupling;
	private Episode2 contextEpisode;
	private Episode2 currentEpisode;
	
	public Agent2(Coupling2 coupling){
		this.coupling = coupling;
		this.currentEpisode = this.coupling.createEpisode(coupling.createOrGetExperience(Coupling.LABEL_E1));
	}
	
	public Experience chooseExperience(Result result){

		if (result != null)
			this.currentEpisode.record(result);
		
		if (this.contextEpisode != null )
			this.coupling.createCompositeInteraction(this.contextEpisode.getInteraction(), this.currentEpisode.getInteraction());

		Experience experience = this.currentEpisode.propose(); 
			
		if (this.currentEpisode.getInteraction() != null)
			this.contextEpisode = this.currentEpisode;

		this.currentEpisode = this.coupling.createEpisode(experience);
		
		return experience;
	}
}