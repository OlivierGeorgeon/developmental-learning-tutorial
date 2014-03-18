package Environments;

import coupling.Coupling;
import coupling.Experience;
import coupling.Result;

public class Environment3 implements Environment {

	private Coupling coupling;
	private Experience experience_1;

	public Environment3(Coupling coupling){
		this.coupling = coupling;
	}
	
	public Result giveResult(Experience experience){
		
		Result result;
		if (experience_1==experience)
			result =  this.coupling.createOrGetResult(Coupling.LABEL_R1);
		else
			result =  this.coupling.createOrGetResult(Coupling.LABEL_R2);
		experience_1 = experience;
		
		return result;
	}
}
