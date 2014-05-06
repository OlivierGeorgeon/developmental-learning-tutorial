package agent;

import tracer.Trace;
import agent.decider.Decider6;
import agent.decider.Episode6;
import coupling.Coupling5;
import coupling.Experience;
import coupling.Result;

/**
 * Exploratory Agent 6.
 * @author Olivier
 */
public class Agent6 implements Agent{
	
	private Decider6 decider;
	private Episode6 episode;
	private int clock;
	
	public Agent6(Coupling5 coupling){
		this.decider = new Decider6(coupling);
		this.episode = new Episode6(coupling, coupling.getFirstExperience());
	}

	public Experience chooseExperience(Result result){

		Trace.startNewEvent();
		Trace.addEventElement("clock", this.clock + "");
		this.clock++;

		if (result != null)
			this.episode.record(result);
		
		if (this.episode.isTerminated()){
			this.episode = this.decider.step(this.episode);
		}
		
		return this.episode.nextPrimitiveExperience();
	}

}
