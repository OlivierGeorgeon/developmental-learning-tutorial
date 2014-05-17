package reactive;

import coupling.Experience;
import coupling.Result;
import existence.Existence1;
import existence.Existence4;

/**
 * The environment of Exercise 5.
 * The agent must alternate experiences e1 and e2 every third cycle to get one r2 result the third time:
 * e1->r1 e1->r1 e1->r2 e2->r1 e2->r1 e2->r2 etc. 
 * @author Olivier
 */
public class Environment5 extends Environment4 {

	protected Experience antepenultimateExperience;

	public Environment5(Existence4 existence){
		super(existence);
	}
	
	public Result giveResult(Experience experience){
		
		Result result = this.existence.createOrGetResult(this.existence.LABEL_R1);

		if (this.antepenultimateExperience != experience &&
				this.penultimateExperience == experience &&
			this.previousExperience == experience)
			result =  this.existence.createOrGetResult(this.existence.LABEL_R2);
		
		this.antepenultimateExperience = this.penultimateExperience;
		this.penultimateExperience = this.previousExperience;
		this.previousExperience = experience;
		
		return result;
	}
}
