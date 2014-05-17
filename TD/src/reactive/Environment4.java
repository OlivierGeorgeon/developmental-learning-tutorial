package reactive;

import coupling.Experience;
import coupling.Intention;
import coupling.Intention4;
import coupling.Obtention3;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction3;
import existence.Existence4;

/**
 * A Reality4 is a sort of Reality3 that expects an Intention1 which specifies an experience,
 * and results in an Obtention3 which provides an enacted Interaction.
 * It results in R2 when the current experience equals the previous experience and differs from the penultimate experience.
 * and in R21 otherwise.
 * e1->r1 e1->r2 e2->r1 e2->r2 etc. 
 * 
 * Reality4 is used to demonstrate an Existence capable of anticipating two steps to make a decision.
 * 
 * @author Olivier
 */
public class Environment4 extends Environment3 {
	
	protected Experience penultimateExperience;

	public Environment4(Existence4 existence){
		super(existence);
	}
	
	@Override
	protected Existence4 getExistence(){
		return (Existence4)this.existence;
	}

	@Override
	public Obtention3 provideObtention(Intention intention){

		Intention4 episode = ((Intention4)intention);
		Experience experience = episode.getExperience();
		Interaction3 enactedInteraction = null;
		Obtention3 obtention= new Obtention3(null);
		
		if (experience.isPrimitive()){
			Result result = giveResult(experience);
			enactedInteraction = (Interaction3)this.getExistence().createOrGetPrimitiveInteraction(experience, result, 0);
		}			
		else {
			
			// Enact the preInteraction
			Experience primitiveExperience = episode.getExperience().getInteraction().getPreInteraction().getExperience(); 
			Result result = this.giveResult(primitiveExperience);
			Interaction enactedPrimitiveInteraction = this.getExistence().createOrGetPrimitiveInteraction(primitiveExperience, result, 0);
			if (!enactedPrimitiveInteraction.equals(episode.getExperience().getInteraction().getPreInteraction())){
				Interaction3 alternateInteraction = this.getExistence().createOrGetInteraction(episode.getExperience(), result, enactedPrimitiveInteraction.getValence());
				obtention.setInteraction(alternateInteraction);
				System.out.println("alternate interaction " + alternateInteraction.getLabel());
			}
			else{
				// Enact the postInteraction
				primitiveExperience = episode.getExperience().getInteraction().getPostInteraction().getExperience(); 
				result = this.giveResult(primitiveExperience);
				enactedPrimitiveInteraction = this.getExistence().createOrGetPrimitiveInteraction(primitiveExperience, result, 0);
				if (!enactedPrimitiveInteraction.equals(episode.getExperience().getInteraction().getPostInteraction())){
					obtention.setInteraction(episode.getExperience().getInteraction());
				}
				else{
					this.getExistence().createOrGetPrimitiveInteraction(episode.getExperience(), result, episode.getExperience().getInteraction().getPreInteraction().getValence() + enactedPrimitiveInteraction.getValence());
					Interaction3 alternateInteraction = (Interaction3)this.getExistence().getInteraction(primitiveExperience.getLabel() + result.getLabel());
					System.out.println("alternate interaction " + alternateInteraction.getLabel());
					obtention.setInteraction(alternateInteraction);
				}			
			}
		}
		return new Obtention3(enactedInteraction);
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
