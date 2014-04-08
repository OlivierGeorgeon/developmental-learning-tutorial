package agent;

import agent.decider.Episode4;
import coupling.Coupling;
import coupling.Coupling4;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction3;

/**
 * Solution to Exercise 4.
 * Agent4 works similarly as Agent3. The difference resides in the class Episode4 which is instanciated by Coupling4. 
 * @author Olivier
 */
public class Agent4 implements Agent{
	
	private Coupling4 coupling;
	private Episode4 episode;
	
	public Agent4(Coupling4 coupling){
		this.coupling = coupling;
		this.episode = coupling.createEpisode(coupling.createOrGetExperience(Coupling.LABEL_E1));
	}

	public Experience chooseExperience(Result result){

		Interaction3 enactedInteraction = null;
		if (result !=null)
			 enactedInteraction = this.coupling.getInteraction(this.episode.getExperience().getLabel() + result.getLabel());

		this.episode.track(enactedInteraction);
		
		if (this.episode.isTerminated()){
			//this.episode.store(enactedInteraction);		
			//this.episode = this.episode.createNext();
		}
		
		return this.episode.getPrimitiveExperience();
	}

}
