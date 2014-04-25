package coupling;

import java.util.HashMap;
import java.util.Map;

import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;

import coupling.interaction.Interaction3;
import coupling.phenomenon.Phenomenon6;
import org.w3c.dom.*;

public class Coupling6 extends Coupling5 {

	//private Map<String ,Phenomenon6> PHENOMENA = new HashMap<String ,Phenomenon6>();

	@Override
	public Interaction3 createOrReinforceCompositeInteraction(
			Interaction3 preInteraction, Interaction3 postInteraction) {
			
			String label = "(" + preInteraction.getLabel() + postInteraction.getLabel() + ")";
			Interaction3 interaction = getInteraction(label);
			if (interaction == null){
				int valence = preInteraction.getValence() + postInteraction.getValence();	
				interaction = this.createOrGet(label, valence); 
				interaction.setPreInteraction(preInteraction);
				interaction.setPostInteraction(postInteraction);
				interaction.incrementWeight();
				interaction.setExperience(this.createOrGetCompositeExperience(interaction));
				interaction.setResult(this.createOrGetResult(label));
			}
			else
				interaction.incrementWeight();
			return interaction;
		}
	
	@Override
	protected void init(){
		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createPrimitiveInteraction(e1, r1, -1);
		createPrimitiveInteraction(e1, r2, 1);
		createPrimitiveInteraction(e2, r1, -1);
		createPrimitiveInteraction(e2, r2, 1);
		
		Tracer<Element> tracer = new ConsoleTracer();
		Trace.init(tracer);
	}

//		public Phenomenon6 createOrGetPhenomenon(String label) {
//		if (!PHENOMENA.containsKey(label))
//			PHENOMENA.put(label, new Phenomenon6(label));			
//		return PHENOMENA.get(label);
//	}
//
//	public Interaction3 createOrGetInteraction(Experience experience,
//			Result result, Interaction3 affordance) {
//		Interaction3 interaction = createOrGet(experience.getLabel() + result.getLabel(), affordance.getValence()); 
//		interaction.setExperience(experience);
//		interaction.setResult(result);
//		return interaction;
//	}

}
