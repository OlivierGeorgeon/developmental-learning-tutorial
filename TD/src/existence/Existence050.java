package existence;

import coupling.Experience;
import coupling.Experience040;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction010;
import coupling.interaction.Interaction040;

/** 
* Existence050 implements radical interactionism.
*/
public class Existence050 extends Existence040 {

	protected final String LABEL_I11 = "i11"; 
	protected final String LABEL_I12 = "i12"; 
	protected final String LABEL_I21 = "i21";
	protected final String LABEL_I22 = "i22";

	@Override
	protected void initExistence(){
		addOrGetPrimitiveInteraction(LABEL_I11, -1);
		addOrGetPrimitiveInteraction(LABEL_I12, 1);
		addOrGetPrimitiveInteraction(LABEL_I21, -1);
		addOrGetPrimitiveInteraction(LABEL_I22, 1);
	}
	
	/**
	 * Create an interaction from its label.
	 * @param label: This interaction's label.
	 * @return The created interaction
	 */
	public Interaction040 addOrGetPrimitiveInteraction(String label, int valence) {
		Interaction040 interaction = (Interaction040)addOrGetInteraction(label); 
		return interaction;
	}


}
