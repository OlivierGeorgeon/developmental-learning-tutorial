package existence;

import coupling.Experience;
import coupling.Result;

public class Existence6 extends Existence050 {

	public final String LABEL_STEP = ">";
	public final String LABEL_FEEL = "-";
	public final String LABEL_SWAP = "i";
	public final String LABEL_TRUE = "t";
	public final String LABEL_FALSE = "f";

	@Override
	protected void initExistence(){
		Experience e1 = addOrGetExperience(LABEL_STEP);
		Experience e2 = addOrGetExperience(LABEL_FEEL);
		Experience e3 = addOrGetExperience(LABEL_SWAP);
		Result r1 = createOrGetResult(LABEL_TRUE);
		Result r2 = createOrGetResult(LABEL_FALSE);
		addOrGetPrimitiveInteraction(e1, r1, 4);   // step_up
		addOrGetPrimitiveInteraction(e1, r2, -10); // step_down
		addOrGetPrimitiveInteraction(e2, r1, -4);  // feel_up
		addOrGetPrimitiveInteraction(e2, r2, -4);  // feel_down
		addOrGetPrimitiveInteraction(e3, r1, 4);   // swap
		addOrGetPrimitiveInteraction(e3, r2, -10); // not_swp
	}

	
}
