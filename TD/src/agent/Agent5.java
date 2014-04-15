package agent;

import agent.decider.Decider5;
import agent.decider.Episode4;
import coupling.Coupling5;
import coupling.Experience;
import coupling.Result;

/**
 * Exploratory Agent 5.
 * Agent5 has a class Decider5 that implements the agent's working memory and decisional mechanism. 
 * @author Olivier
 */
public class Agent5 implements Agent{
	
	private Decider5 decider;
	private Episode4 episode;
	
	public Agent5(Coupling5 coupling){
		this.decider = new Decider5(coupling);
		this.episode = new Episode4(coupling, coupling.getFirstExperience());
	}

	public Experience chooseExperience(Result result){

		if (result != null)
			this.episode.record(result);
		
		if (this.episode.isTerminated()){
			this.episode = this.decider.step(this.episode);
		}
		
		return this.episode.getPrimitiveExperience();
	}

}
