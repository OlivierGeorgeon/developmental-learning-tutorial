package reactive;

import coupling.Experience;
import coupling.Intention;
import coupling.Intention1;
import coupling.Obtention;
import coupling.Obtention1;
import coupling.Result;
import existence.Existence021;

/**
 * A Reality1 is a sort of Reality that expects an Intention of type Intention1 
 * which specifies an intended Experiment.
 * It returns an Obtention of type Obtention1 which provides a Result.
 * E1 results in R2. E2 results in R1.
 * @author Olivier
 */
public class Environment1 implements Environment {
	
	protected Existence021 existence;
	
	public Environment1(Existence021 existence1){
		this.existence = existence1;
	}
	
	public Obtention provideObtention(Intention intention){
		
		Experience experience = ((Intention1)intention).getExperience();
		Result result = giveResult(experience);
		
		return new Obtention1(result);
	}
	
	public Result giveResult(Experience experience){
		Result result = null;
		
		if (experience.equals(this.existence.createOrGetExperience(this.existence.LABEL_E1)))
			result =  this.existence.createOrGetResult(this.existence.LABEL_R2);
		else
			result =  this.existence.createOrGetResult(this.existence.LABEL_R1);

		return result;
	}
	
	protected Existence021 getExistence(){
		return this.existence;
	}
}
