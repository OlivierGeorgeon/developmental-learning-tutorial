package Environments;

import coupling.Coupling;
import coupling.Experience;
import coupling.Result;

public class Environment4 implements Environment {

	private Coupling coupling;
	private Experience experience_1;
	private Experience experience_2;

	public Environment4(Coupling coupling){
		this.coupling = coupling;
	}
	
	public Result giveResult(Experience experience){
		
		Result result;
		if (experience_2!=experience &&
			experience_1==experience)
			result =  this.coupling.createOrGetResult(Coupling.LABEL_R2);
		else
			result =  this.coupling.createOrGetResult(Coupling.LABEL_R1);
		experience_2 = experience_1;
		experience_1 = experience;
		
		return result;
	}
}