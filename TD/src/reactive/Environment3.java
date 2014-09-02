package reactive;

import coupling.Experience;
import coupling.Result;
import existence.Existence022;

/**
 * A Reality2 is a sort of Reality1
 * Before time T1 and after time T2: E1 results in R1; E2 results in R2
 * between time T1 and time T2: E1 results R2; E2results in R1.
 * @author Olivier
 */
public class Environment3 extends Environment2 {

	private final int T1 = 8;
	private final int T2 = 15;
	private int clock = 0;

	public Environment3(Existence022 existence){
		super(existence);
	}
	
	@Override
	public Result giveResult(Experience experience){

		Result result = null;

		this.clock++;
		
		if (clock <= T1 || clock > T2){
			if (experience.equals(this.existence.createOrGetExperience(this.existence.LABEL_E1)))
				result =  this.existence.createOrGetResult(this.existence.LABEL_R1);
			else
				result = this.existence.createOrGetResult(this.existence.LABEL_R2);
		} 
		else {
			if (experience.equals(this.existence.createOrGetExperience(this.existence.LABEL_E1)))
				result = this.existence.createOrGetResult(this.existence.LABEL_R2);
			else
				result = this.existence.createOrGetResult(this.existence.LABEL_R1);
		}
		return result;
	}
}
