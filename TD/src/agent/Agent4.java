package agent;

import agent.decider.Episode4;
import coupling.Coupling4;
import coupling.Experience;
import coupling.Result;

/**
 * Solution to Exercise 4.
 * Agent4 works similarly as Agent3. The difference resides in the class Episode4 which is instanciated by Coupling4. 
 * @author Olivier
 */
public class Agent4 implements Agent{
	
	private Coupling4 coupling;
	private Episode4 currentEpisode;
	
	public Agent4(Coupling4 coupling){
		this.coupling = coupling;
		Episode4 contextEpisode = null;
		this.currentEpisode = coupling.createEpisode(contextEpisode);
	}

	public Experience chooseExperience(Result result){

		if (result != null)
			this.currentEpisode.record(result);
		
		if (this.currentEpisode.isTerminated()){
			this.coupling.store(currentEpisode);			
			this.currentEpisode = this.coupling.createEpisode(this.currentEpisode);
		}
		
		return this.currentEpisode.getPrimitiveExperience();
	}

}
