package coupling;

import java.util.List;

public interface Coupling {

	public Experience createOrGetExperience(String label);

	public Experience getOtherExperience(Experience experience);

	public Result createOrGetResult(String label);

	public Interaction createPrimitiveInteraction(Experience experience, Result result, int value);
	
	public Interaction createOrGetCompositeInteraction(Interaction preInteraction, Interaction postInteraction);
	
	public Interaction getInteraction(String label);
	
	public  List<Interaction> getActivatedInteractions(Interaction interaction);

}
