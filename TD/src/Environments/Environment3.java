package Environments;

import coupling.Coupling;
import coupling.Experience;
import coupling.Result;

/**
 * The environment of Exercise 3.
 * Switch periodically between Environment 0 and Environment 1.
 * @author Olivier
 */
public class Environment3 implements Environment {

	private Coupling coupling;
	private int clock = 0;


	public Environment3(Coupling coupling){
		this.coupling = coupling;
	}
	
	public Result giveResult(Experience experience){

		this.clock++;
		
		if (clock <= 8 || clock > 15){
			if (experience.equals(this.coupling.createOrGetExperience(Coupling.LABEL_E1)))
				return this.coupling.createOrGetResult(Coupling.LABEL_R1);
			else
				return this.coupling.createOrGetResult(Coupling.LABEL_R2);
		} 
		else {
			if (experience.equals(this.coupling.createOrGetExperience(Coupling.LABEL_E1)))
				return this.coupling.createOrGetResult(Coupling.LABEL_R2);
			else
				return this.coupling.createOrGetResult(Coupling.LABEL_R1);
		}
	}
}
