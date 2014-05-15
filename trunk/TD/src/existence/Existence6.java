package existence;

import coupling.Experience;
import coupling.Result;

public class Existence6 extends Existence5 {

	public final String LABEL_STEP = ">";
	public final String LABEL_FEEL = "-";
	public final String LABEL_SWAP = "i";
	public final String LABEL_TRUE = "t";
	public final String LABEL_FALSE = "f";

	@Override
	protected void initExistence(){
		Experience e1 = createOrGetExperience(LABEL_STEP);
		Experience e2 = createOrGetExperience(LABEL_FEEL);
		Experience e3 = createOrGetExperience(LABEL_SWAP);
		Result r1 = createOrGetResult(LABEL_TRUE);
		Result r2 = createOrGetResult(LABEL_FALSE);
		createOrGetPrimitiveInteraction(e1, r1, 4);   // step_up
		createOrGetPrimitiveInteraction(e1, r2, -10); // step_down
		createOrGetPrimitiveInteraction(e2, r1, -4);  // feel_up
		createOrGetPrimitiveInteraction(e2, r2, -4);  // feel_down
		createOrGetPrimitiveInteraction(e3, r1, 4);   // swap
		createOrGetPrimitiveInteraction(e3, r2, -10); // not_swp
	}

	
}
