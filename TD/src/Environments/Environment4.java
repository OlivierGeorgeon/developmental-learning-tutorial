package Environments;

import coupling.Coupling;
import coupling.Experience;
import coupling.Result;

/**
 * The environment of Exercise 4.
 * The agent must alternate experiences e1 and e2 every second cycle to get one r2 result the second time:
 * e1->r1 e1->r2 e2->r1 e2->r2 etc. 
 * @author Olivier
 */
public class Environment4 implements Environment {

	private Coupling coupling;
	private Experience experience_1;
	private Experience experience_2;

	public Environment4(Coupling coupling){
		this.coupling = coupling;
	}
	
	public Result giveResult(Experience experience){
		
		Result result = this.coupling.createOrGetResult(Coupling.LABEL_R1);

		if (experience_2!=experience &&
			experience_1==experience)
			result =  this.coupling.createOrGetResult(Coupling.LABEL_R2);
		
		experience_2 = experience_1;
		experience_1 = experience;
		
		return result;
	}
}
