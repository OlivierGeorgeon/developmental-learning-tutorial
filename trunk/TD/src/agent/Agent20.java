package agent;

import agent.decider.Episode20;
import tracer.Trace;
import coupling.Coupling20;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction2;

public class Agent20 implements Agent{

	public static int BOREDOME_LEVEL = 5;
	
	private Coupling20 coupling;
	private Episode20 contextEpisode;
	private Episode20 currentEpisode;

	
	private int state;
	//private Experience experience;
	
	public Agent20(Coupling20 coupling){
		this.coupling = coupling;
		this.currentEpisode = this.coupling.createEpisode(coupling.getOtherInteraction(null));
	}
	
	public Experience chooseExperience(Result result){

		if (this.currentEpisode.getInteraction() != null && this.currentEpisode.getInteraction().getResult().equals(result))
			Trace.addEventElement("status", "happy");
		else
			Trace.addEventElement("status", "sad");

		if (result != null)
			this.currentEpisode.record(result);

		if (this.contextEpisode != null )
			this.coupling.createCompositeInteraction(this.contextEpisode.getInteraction(), this.currentEpisode.getInteraction());

		
		Interaction2 intendedInteraction = this.coupling.proposeInteraction(this.currentEpisode.getInteraction()); 
		
		if (this.currentEpisode.getInteraction() != null)
			this.contextEpisode = this.currentEpisode;

		if (this.state > BOREDOME_LEVEL){
			Trace.addEventElement("status", "bored");
			intendedInteraction = coupling.getOtherInteraction(intendedInteraction);		
			this.state = 0;
		}
		
		this.state++;
		
		//Interaction intendedInteraction = this.coupling.predict(this.experience);
		//if (intendedInteraction != null)
		//	this.expectedResult = intendedInteraction.getResult();

		this.currentEpisode = this.coupling.createEpisode(intendedInteraction);
		Trace.addEventElement("intend", intendedInteraction.toString());

		return intendedInteraction.getExperience();
	}
}
