package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import reactive.Environment2;
import agent.decider.Decider2;
import coupling.Experience;
import coupling.Obtention2;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;

/**
 * Existence2 is a sort of Existence1 that learns composite interactions (Interaction2). 
 * Additionally, Existence2 proposes a list of interactions to the Decider.
 * 
 * @author Olivier
 */
public class Existence2 extends Existence1 {

	protected Obtention2 obtention;
	private Interaction2 contextInteraction;
	private Interaction2 currentInteraction;

	@Override
	protected void initExistence(){
		this.decider = new Decider2(this);
		this.environment = new Environment2(this);

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
	protected void learn(){

		this.contextInteraction = this.currentInteraction;
		this.currentInteraction = this.obtention.getInteraction();
		
		if (this.contextInteraction != null )
			this.createOrGetCompositeInteraction(this.contextInteraction, this.currentInteraction);		
	}
	
	@Override
	protected Interaction2 createNewInteraction(String label, int valence){
		return new Interaction2(label, valence);
	}

	public Interaction2 createOrGetCompositeInteraction(
		Interaction preInteraction, Interaction postInteraction) {
		int valence = preInteraction.getValence() + postInteraction.getValence();
		Interaction2 interaction = (Interaction2)createOrGet(preInteraction.getLabel() + postInteraction.getLabel(), valence); 
		interaction.setPreInteraction(preInteraction);
		interaction.setPostInteraction(postInteraction);
		System.out.println("learn " + interaction.toString());
		return interaction;
	}

	public List<Interaction> proposeInteractions(){
		List<Interaction> interactions = new ArrayList<Interaction>();
		for (Interaction2 activatedInteraction : this.getActivatedInteractions(this.contextInteraction)){
			interactions.add(activatedInteraction.getPostInteraction());
			System.out.println("propose " + activatedInteraction.getPostInteraction().getLabel());
		}
		Collections.sort(interactions);
		return interactions;
	}

	protected List<Interaction2> getActivatedInteractions(Interaction interaction) {
		List<Interaction2> activatedInteractions = new ArrayList<Interaction2>();
		for (Interaction activatedInteraction : this.INTERACTIONS.values())
			if (interaction == ((Interaction2)activatedInteraction).getPreInteraction())
				activatedInteractions.add((Interaction2)activatedInteraction);
		return activatedInteractions;
	}	

}
