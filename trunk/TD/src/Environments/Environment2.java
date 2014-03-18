package Environments;

import coupling.Coupling;
import coupling.Experience;
import coupling.Result;

public class Environment2 implements Environment {
	
	private Coupling coupling;
	
	public Environment2(Coupling coupling){
		this.coupling = coupling;
	}
	
	public Result giveResult(Experience experience){
		if (experience.equals(this.coupling.createOrGetExperience(Coupling.LABEL_E1)))
			return this.coupling.createOrGetResult(Coupling.LABEL_R2);
		else
			return this.coupling.createOrGetResult(Coupling.LABEL_R1);
	}
}
