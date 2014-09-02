package existence;

import tracer.Trace;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;

/**
 * An Existence2 is a sort of Existence1 in which each Interaction has a predefined Valence.
 * When a given Experience is performed and a given Result is obtained, the corresponding Interaction is considered enacted.
 * The Existence2 is PLEASED when the enacted Interaction has a positive or null Valence, and PAINED otherwise.
 * An Existence2 is still a single entity rather than being split into an explicit Agent and Environment. 
 * An Existence2 demonstrates a rudimentary decisional mechanism and a rudimentary learning mechanism.
 * It learns to choose the Experience that induces an Interaction that has a positive valence.  
 * Try to change the Valences of interactions and the method giveResult(experience) 
 * and observe that the Existence01 still learns to enact interactions that have positive valences.  
 * 
 * @author Olivier
 */
public class Existence01 extends Existence010 {

	@Override
	protected void initExistence(){
		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		/** Change the valence of interactions to change the agent's motivation */
		createPrimitiveInteraction(e1, r1, -1);  
		createPrimitiveInteraction(e1, r2, 1);
		createPrimitiveInteraction(e2, r1, -1);
		createPrimitiveInteraction(e2, r2, 1);		
	}
	
	@Override
	public Experience chooseExperience(Result result){
		
		if (this.experience == null)
			this.experience = this.getOtherExperience(null);
		else{ 
			int mood = getInteraction(this.experience.getLabel() + result.getLabel()).getValence();
			if (mood > 0)
				Trace.addEventElement("mood", "PLEASED");
			else{
				Trace.addEventElement("mood", "PAINED");
				this.experience = this.getOtherExperience(this.experience);
			}
		}
		return this.experience;
	}
	
	/** Change the function giveResult(experience) to produce different results of experiences */
	@Override
	public Result returnResult(Experience experience){
		if (experience.equals(this.createOrGetExperience(LABEL_E1)))
			return this.createOrGetResult(LABEL_R1);
		else
			return this.createOrGetResult(LABEL_R2);
	}

	/**
	 * Create an interaction as a tuple <experience, result>.
	 * @param experience: The experience.
	 * @param result: The result.
	 * @param valence: the interaction's valence
	 * @return The created interaction
	 */
	protected Interaction createPrimitiveInteraction(Experience experience, Result result, int valence) {
		Interaction interaction = createOrGet(experience.getLabel() + result.getLabel(), valence); 
		interaction.setExperience(experience);
		interaction.setResult(result);
		return interaction;
	}

	/**
	 * Records an interaction in memory.
	 * @param label: The label of this interaction.
	 * @param valence: the interaction's valence
	 * @return The interaction.
	 */
	protected Interaction createOrGet(String label, int valence) {
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, new Interaction2(label, valence));			
		return INTERACTIONS.get(label);
	}
	
	@Override
	protected Interaction2 getInteraction(String label){
		return (Interaction2)INTERACTIONS.get(label);
	}

}
