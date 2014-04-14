package Environments;

import coupling.Coupling;
import coupling.Experience;
import coupling.Result;

public class Environment1 implements Environment {
	
	private Coupling coupling;
	
	public Environment1(Coupling coupling){
		this.coupling = coupling;
	}
	
	public Result giveResult(Experience experience){
		if (experience.equals(this.coupling.createOrGetExperience(this.coupling.LABEL_E1)))
			return this.coupling.createOrGetResult(this.coupling.LABEL_R2);
		else
			return this.coupling.createOrGetResult(this.coupling.LABEL_R1);
	}
}
