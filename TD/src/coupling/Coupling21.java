package coupling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;
import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;
import Environments.Environment1;
import Environments.Environment2;
import agent.Agent2;
import agent.Agent21;
import agent.decider.Decider21;
import agent.decider.Episode2;
import coupling.interaction.Interaction2;

public class Coupling21 extends Coupling2 {


	@Override
	protected void initCoupling(){
		this.setDecider(new Decider21(this));
		this.setEnvironment(new Environment2(this));

		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createOrGetPrimitiveInteraction(e1, r1, 0);
		createOrGetPrimitiveInteraction(e1, r2, 0);
		createOrGetPrimitiveInteraction(e2, r1, 0);
		createOrGetPrimitiveInteraction(e2, r2, 0);

	}
	
	public Episode2 createEpisode(Interaction2 interaction) {
		return new Episode2(this, interaction);
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
