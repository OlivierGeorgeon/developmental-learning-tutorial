package existence;

import coupling.Experience;
import coupling.Experience040;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction010;
import coupling.interaction.Interaction030;
import coupling.interaction.Interaction040;
import environment.Environment;
import environment.Environment050;

/** 
* Existence050 implements radical interactionism.
*/
public class Existence050 extends Existence040 {

	protected final String LABEL_I11 = "i11"; 
	protected final String LABEL_I12 = "i12"; 
	protected final String LABEL_I21 = "i21";
	protected final String LABEL_I22 = "i22";
	
	private Environment environment;
	protected Environment getEnvironment(){
		return this.environment;
	}

	@Override
	protected void initExistence(){
		this.environment = new Environment050(this);
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

	@Override
	public Interaction040 enact(Interaction030 intendedInteraction){

		if (intendedInteraction.isPrimitive())
			return (Interaction040)this.getEnvironment().enact(intendedInteraction);
		else {			
			// Enact the pre-interaction
			Interaction040 enactedPreInteraction = enact(intendedInteraction.getPreInteraction());
			if (!enactedPreInteraction.equals(intendedInteraction.getPreInteraction()))
				// if the preInteraction failed then the enaction of the intendedInteraction is interrupted here.
				return enactedPreInteraction;
			else{
				// Enact the post-interaction
				Interaction040 enactedPostInteraction = enact(intendedInteraction.getPostInteraction());
				return (Interaction040)addOrGetCompositeInteraction(enactedPreInteraction, enactedPostInteraction);
			}
		}
	}


}
