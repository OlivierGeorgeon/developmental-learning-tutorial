package reactive;

import coupling.Experience;
import coupling.Intention;
import coupling.Intention4;
import coupling.Obtention3;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction031;
import existence.Existence4_;

/**
 * A Reality4 is a sort of Reality3 that expects an Intention1 which specifies an experience,
 * and results in an Obtention3 which provides an enacted Interaction.
 * It results in R2 when the current experience equals the previous experience and differs from the penultimate experience.
 * and in R1 otherwise.
 * e1->r1 e1->r2 e2->r1 e2->r2 etc. 
 * 
 * Reality4 is used to demonstrate an Existence capable of anticipating two steps to make a decision.
 * 
 * @author Olivier
 */
public class Environment4 extends Environment3 {
	
	protected Experience penultimateExperience;

	public Environment4(Existence4_ existence){
		super(existence);
	}
	
	@Override
	protected Existence4_ getExistence(){
		return (Existence4_)this.existence;
	}


	public Result giveResult(Experience experience){
		
		Result result = this.existence.createOrGetResult(this.existence.LABEL_R1);

		if (this.penultimateExperience != experience &&
			this.previousExperience == experience)
			result =  this.existence.createOrGetResult(this.existence.LABEL_R2);
		
		this.penultimateExperience = this.previousExperience;
		this.previousExperience = experience;
		
		return result;
	}
}
