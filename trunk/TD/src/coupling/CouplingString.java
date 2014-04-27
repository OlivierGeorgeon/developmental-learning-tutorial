package coupling;

public class CouplingString extends Coupling4 {
	
	public static final String LABEL_STEP = ">";
	public static final String LABEL_FEEL = "-";
	public static final String LABEL_SWAP = "i";
	public static final String LABEL_TRUE = "t";
	public static final String LABEL_FALSE = "f";
	

	@Override
	protected void init(){
		Experience e1 = createOrGetExperience(LABEL_STEP);
		Experience e2 = createOrGetExperience(LABEL_FEEL);
		Experience e3 = createOrGetExperience(LABEL_SWAP);
		Result r1 = createOrGetResult(LABEL_TRUE);
		Result r2 = createOrGetResult(LABEL_FALSE);
		createPrimitiveInteraction(e1, r1, 4);   // step_up
		createPrimitiveInteraction(e1, r2, -10); // step_down
		createPrimitiveInteraction(e2, r1, -4);  // feel_up
		createPrimitiveInteraction(e2, r2, -4);  // feel_down
		createPrimitiveInteraction(e3, r1, 4);   // swap
		createPrimitiveInteraction(e3, r2, -10); // not_swp
	}

}
