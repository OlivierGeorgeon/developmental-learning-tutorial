package coupling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;
import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;
import agent.decider.Episode20;
import coupling.interaction.Interaction2;

public class Coupling20 extends Coupling2 {


	@Override
	protected void init(){
		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createPrimitiveInteraction(e1, r1, 0);
		createPrimitiveInteraction(e1, r2, 0);
		createPrimitiveInteraction(e2, r1, 0);
		createPrimitiveInteraction(e2, r2, 0);

		Tracer<Element> tracer = new ConsoleTracer();
		Trace.init(tracer);
	}
	
	public Episode20 createEpisode(Interaction2 interaction) {
		return new Episode20(this, interaction);
	}
	
	public List<Interaction2> proposeInteractions(Interaction2 contextInteraction){
		List<Interaction2> interactions = new ArrayList<Interaction2>();
		for (Interaction2 activatedInteraction : this.getActivatedInteractions(contextInteraction)){
			interactions.add(activatedInteraction.getPostInteraction());
			System.out.println("propose " + activatedInteraction.getPostInteraction().getLabel());
		}
		Collections.sort(interactions);
		return interactions;
	}

}
