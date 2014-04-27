package coupling;

import java.util.HashMap;
import java.util.Map;

import tracer.AbstractLiteTracer;
import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;

import coupling.interaction.Interaction3;
import coupling.phenomenon.Phenomenon6;
import org.w3c.dom.*;

public class Coupling6 extends Coupling5 {

	public static final String LABEL_STEP = ">";
	public static final String LABEL_FEEL = "-";
	public static final String LABEL_SWAP = "i";
	public static final String LABEL_TRUE = "t";
	public static final String LABEL_FALSE = "f";
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
		
		Tracer<Element> tracer = new ConsoleTracer();
		//Tracer<Element> tracer = new AbstractLiteTracer("http://134.214.128.53/abstract/lite/php/stream/","l-kHWqeLDlSZT-TdBrLSoXVeBRCRsw");
		//Tracer<Element> tracer = new AbstractLiteTracer("http://macbook-pro-de-olivier-2.local/alite/php/stream/","BGKGGBbdjxbYzYAlvXrjbVMjOwyXEA");
		
		Trace.init(tracer);
	}
	
	public Experience getFirstExperience() {
		return createOrGetExperience(LABEL_STEP);
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
