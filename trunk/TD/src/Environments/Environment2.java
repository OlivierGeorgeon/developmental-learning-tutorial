package Environments;

import Existence.Existence2;
import coupling.Experience;
import coupling.Result;

/**
 * A Reality2 is a sort of Reality1
 * which results in R1 when the current experience equals the previous experience
 * and in R2 when the current experience differs from the previous experience.
 * @author Olivier
 */
public class Environment2 extends Environment1 {

	private Experience experience_1;

	public Environment2(Existence2 existence){
		super(existence);
	}
	
	@Override
	protected Result giveResult(Experience experience){
		Result result = null;
		if (experience_1 == experience)
			result =  this.existence.createOrGetResult(this.existence.LABEL_R1);
		else
			result =  this.existence.createOrGetResult(this.existence.LABEL_R2);
		experience_1 = experience;

		return result;
	}
}
