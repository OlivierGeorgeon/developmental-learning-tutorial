package coupling;

import coupling.interaction.Interaction;

/**
 * The possibilities of interaction between the agent and the environment.
 * @author Olivier
 */
public interface Coupling {

//	public static final String LABEL_E1 = "e1"; 
//	public static final String LABEL_E2 = "e2"; 
//	public static final String LABEL_R1 = "r1";
//	public static final String LABEL_R2 = "r2";

	// Labels used with the StringEnvironment
	public static final String LABEL_E1 = ">";
	public static final String LABEL_E2 = "-";
	public static final String LABEL_E3 = "o";
	public static final String LABEL_R1 = "t";
	public static final String LABEL_R2 = "f";

	public Experience createOrGetExperience(String label);

	public Result createOrGetResult(String label);

	public Experience getOtherExperience(Experience experience);

	public void createPrimitiveInteraction(Experience experience, Result result, int valence);
	
	public Interaction getInteraction(String label);

}
