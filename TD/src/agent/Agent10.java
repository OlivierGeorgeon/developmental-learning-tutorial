package agent;

import tracer.Trace;
import coupling.Coupling10;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;

public class Agent10 implements Agent{

	private Coupling10 coupling;

	public static int BOREDOME_LEVEL = 5;
	
	private int state = BOREDOME_LEVEL;
	private Experience experience;
	private Result expectedResult;
	
	public Agent10(Coupling10 coupling){
		this.coupling = coupling;
	}
	
	public Experience chooseExperience(Result result){

		if (this.expectedResult != null && this.expectedResult.equals(result))
			Trace.addEventElement("status", "happy");
		else
			Trace.addEventElement("status", "sad");
		
		if (this.experience != null && result != null)
			this.coupling.createOrGetPrimitiveInteraction(this.experience, result, 0);

		if (this.state >= BOREDOME_LEVEL){
			Trace.addEventElement("status", "bored");
			this.experience = coupling.getOtherExperience(this.experience);		
			this.state = 0;
		}
		
		this.state++;
		
		Interaction intendedInteraction = this.coupling.predict(this.experience);
		if (intendedInteraction != null)
			this.expectedResult = intendedInteraction.getResult();
		
		return this.experience;
	}
}
