package coupling;

public class CouplingString extends Coupling4 {

	@Override
	protected void init(){
		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Experience e3 = createOrGetExperience(LABEL_E3);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createPrimitiveInteraction(e1, r1, 4);   // step_up
		createPrimitiveInteraction(e1, r2, -10); // step_down
		createPrimitiveInteraction(e2, r1, -4);  // feel_up
		createPrimitiveInteraction(e2, r2, -4);  // feel_down
		createPrimitiveInteraction(e3, r1, 4);   // swap
		createPrimitiveInteraction(e3, r2, -10); // not_swp
	}

}
