package agent;

import agent.decider.Decider5;
import agent.decider.Episode4;
import coupling.Coupling5;
import coupling.Experience;
import coupling.Result;

/**
 * Solution to Exercise 4.
 * Agent4 works similarly as Agent3. The difference resides in the class Episode4 which is instanciated by Coupling4. 
 * @author Olivier
 */
public class Agent5 implements Agent{
	
	private Decider5 decider;
	private Episode4 episode;
	
	public Agent5(Coupling5 coupling){
		this.decider = new Decider5(coupling);
		this.episode = this.decider.step(null);
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
