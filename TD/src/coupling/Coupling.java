package coupling;

import java.util.Collection;

import coupling.interaction.Interaction1;

public interface Coupling {

	public static final String LABEL_E1 = "e1";
	public static final String LABEL_E2 = "e2";
	public static final String LABEL_R1 = "r1";
	public static final String LABEL_R2 = "r2";

	public Experience createOrGetExperience(String label);

	public Experience getOtherExperience(Experience experience);

	public Result createOrGetResult(String label);

	public void createPrimitiveInteraction(Experience experience, Result result, int valence);
	
	public void createOrReinforceCompositeInteraction(Interaction1 preInteraction, Interaction1 postInteraction);
	
	public Interaction1 getInteraction(String label);
	
	public Collection<Interaction1> getInteractions();
}
