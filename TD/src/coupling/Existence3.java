package coupling;

import coupling.interaction.Interaction;

/**
 * The possibilities of interaction between the agent and the environment.
 * @author Olivier
 */
public interface Existence3 {

	public static final String LABEL_E1 = "e1"; 
	public static final String LABEL_E2 = "e2"; 
	public static final String LABEL_R1 = "r1";
	public static final String LABEL_R2 = "r2";

	public Experience createOrGetExperience(String label);

	public Experience getOtherExperience(Experience experience);

	public Result createOrGetResult(String label);

	public Interaction createOrGetPrimitiveInteraction(Experience experience, Result result, int valence);
	
	public Interaction getInteraction(String label);
	
	public Intention decideIntention(Obtention obtention);
		
	public Obtention produceObtention(Intention intention);	
	
}
