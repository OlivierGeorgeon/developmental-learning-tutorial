package Environments;

import Existence.Existence2;
import coupling.Existence3;
import coupling.Experience;
import coupling.Intention;
import coupling.Intention1;
import coupling.Intention3;
import coupling.Obtention;
import coupling.Obtention3;
import coupling.Result;
import coupling.interaction.Interaction3;

/**
 * A Reality4 is a sort of Reality that expects an Intention of type Intention3 
 * which specifies an intended Interaction.
 * It results in an Obtention of type Obtention3 which provides an enacted Interaction.
 * It results in R2 when the current experience equals the previous experience and differs from the penultimate experience.
 * and in R21 otherwise.
 * e1->r1 e1->r2 e2->r1 e2->r2 etc. 
 * @author Olivier
 */
public class Environment4 implements Environment {

	private Experience experience_1;
	private Experience experience_2;

	public Environment4(Existence4 existence){
		super(existence);
	}

	public Obtention provideObtention(Intention intention){

		Experience experience = ((Intention3)intention).getInteraction().getExperience();
		Result result = giveResult(experience);
		Interaction3 enactedInteraction = (Interaction3)this.existence.createOrGetPrimitiveInteraction(experience, result, 0);
		
		return new Obtention3(enactedInteraction);
	}


	@Override
	public Result giveResult(Experience experience){
		
		Result result = this.existence.createOrGetResult(Existence3.LABEL_R1);

		if (experience_2!=experience &&
			experience_1==experience)
			result =  this.existence.createOrGetResult(Existence3.LABEL_R2);
		
		experience_2 = experience_1;
		experience_1 = experience;
		
		return result;
	}
}
