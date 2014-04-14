package Environments;

import coupling.Coupling;
import coupling.Experience;
import coupling.Result;

public class Environment0 implements Environment {
	
	private Coupling coupling;
	
	public Environment0(Coupling coupling){
		this.coupling = coupling;
	}
	
	public Result giveResult(Experience experience){
		if (experience.equals(this.coupling.createOrGetExperience(this.coupling.LABEL_E1)))
			return this.coupling.createOrGetResult(this.coupling.LABEL_R1);
		else
			return this.coupling.createOrGetResult(this.coupling.LABEL_R2);
	}
}
