package coupling;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;

import Environments.Environment2;
import agent.Agent2;
import agent.decider.Decider2;
import agent.decider.Episode2;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;

/**
 * A Coupling2 
 * - can learn composite interactions (Interaction2)
 * - Can instantiate an Episode2 .
 * @author Olivier
 */
public class Coupling2 extends Coupling1 {
	
	@Override
	protected void initCoupling(){
		this.setDecider(new Decider2(this));
		this.setEnvironment(new Environment2(this));

		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createOrGetPrimitiveInteraction(e1, r1, -1);
		createOrGetPrimitiveInteraction(e1, r2, 1);
		createOrGetPrimitiveInteraction(e2, r1, -1);
		createOrGetPrimitiveInteraction(e2, r2, 1);
	}
	
	@Override
	protected Interaction2 createNewInteraction(String label, int valence){
		return new Interaction2(label, valence);
	}

	public Episode2 createEpisode(Interaction interaction) {
		return new Episode2(this, interaction);
	}

	public void createCompositeInteraction(
		Interaction2 preInteraction, Interaction2 postInteraction) {
		int valence = preInteraction.getValence() + postInteraction.getValence();
		Interaction2 interaction = (Interaction2)createOrGet(preInteraction.getLabel() + postInteraction.getLabel(), valence); 
		interaction.setPreInteraction(preInteraction);
		interaction.setPostInteraction(postInteraction);
		System.out.println("learn " + interaction.toString());
	}

	public List<Interaction> proposeInteractions(Interaction contextInteraction){
		List<Interaction> interactions = new ArrayList<Interaction>();
		for (Interaction2 activatedInteraction : this.getActivatedInteractions(contextInteraction)){
			interactions.add(activatedInteraction.getPostInteraction());
			System.out.println("propose " + activatedInteraction.getPostInteraction().getLabel());
		}
		Collections.sort(interactions);
		return interactions;
	}

	public List<Interaction2> getActivatedInteractions(Interaction interaction) {
		List<Interaction2> activatedInteractions = new ArrayList<Interaction2>();
		for (Interaction activatedInteraction : getInteractions())
			if (interaction == ((Interaction2)activatedInteraction).getPreInteraction())
				activatedInteractions.add((Interaction2)activatedInteraction);
		return activatedInteractions;
	}	
}
