package agent;

import agent.decider.Episode4;
import coupling.Coupling4;
import coupling.Experience;
import coupling.Result;

/**
 * @author Olivier
 */
public class Agent4 implements Agent{
	
	private Coupling4 coupling;
	private Episode4 episode;
	
	public Agent4(Coupling4 coupling){
		this.coupling = coupling;
		this.episode = coupling.chooseEpisode(null);
	}

	public Experience chooseExperience(Result result){

		if (result != null)
			this.episode.record(result);
		
		if (this.episode.isTerminated()){
			this.coupling.store(episode);			
			this.episode = this.coupling.chooseEpisode(this.episode.getInteraction());
		}
		
		return this.episode.getPrimitiveExperience();
	}

}
