package coupling;

import org.w3c.dom.Element;

import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;
import agent.decider.Episode2;
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
	
	public Interaction2 proposeInteraction(Interaction2 contextInteraction){
		Interaction2 interaction = this.getOtherInteraction(null);
		for (Interaction2 activatedInteraction : this.getActivatedInteractions(contextInteraction))
			if (activatedInteraction.getPostInteraction().getValence() >= 0){
				interaction = activatedInteraction.getPostInteraction();
				System.out.println("propose " + interaction.getLabel());
			}
			else{
				interaction = this.getOtherInteraction(activatedInteraction.getPostInteraction());						
			}
		return interaction;
	}

}
