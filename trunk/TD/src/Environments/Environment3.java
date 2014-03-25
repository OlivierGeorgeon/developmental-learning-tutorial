package Environments;

import coupling.Coupling;
import coupling.Experience;
import coupling.Result;

public class Environment3 implements Environment {

	private Coupling coupling;
	private int clock = 0;


	public Environment3(Coupling coupling){
		this.coupling = coupling;
	}
	
	public Result giveResult(Experience experience){

		this.clock++;
		
		if (clock <= 10){
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
