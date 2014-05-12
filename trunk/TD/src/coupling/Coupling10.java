package coupling;

import org.w3c.dom.Element;
import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;
import coupling.interaction.Interaction;

/**
 * A Coupling1.0 is a variation of a Coupling1
 * - In which primitive interactions are not predefined.
 * - Which learns to predict the primitive interaction resulting from an experiment.
 * @author Olivier
 */
public class Coupling10 extends Coupling1 {
	
	protected void init(){
		createOrGetExperience(LABEL_E1);
		createOrGetExperience(LABEL_E2);
		createOrGetResult(LABEL_R1);
		createOrGetResult(LABEL_R2);

		Tracer<Element> tracer = new ConsoleTracer();
		Trace.init(tracer);
	}
	
	public Interaction predict(Experience experience){
		Interaction interaction = null;
		
		for (Interaction i : this.getInteractions())
			if (i.getExperience().equals(experience))
				interaction = i;
		
		return interaction;
	}
}
