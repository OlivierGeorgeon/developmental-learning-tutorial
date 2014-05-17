package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.decider.Proposition;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;
import coupling.interaction.Interaction3;
import coupling.Experience;

public class Existence4 extends Existence3 {

	private Interaction3 interaction_1;
	private Interaction3 interaction_2;
	private Interaction3 superInteraction;

	@Override
	protected void learn(){

	}

	@Override
	public Interaction3 createOrReinforceCompositeInteraction(Interaction preInteraction, Interaction postInteraction) {
			
			String label = "(" + preInteraction.getLabel() + postInteraction.getLabel() + ")";
			Interaction3 interaction = (Interaction3)getInteraction(label);
			if (interaction == null){
				int valence = preInteraction.getValence() + postInteraction.getValence();	
				interaction = (Interaction3)this.createOrGet(label, valence); 
				interaction.setPreInteraction(preInteraction);
				interaction.setPostInteraction(postInteraction);
				interaction.incrementWeight();
				interaction.setExperience(this.createOrGetCompositeExperience(interaction));
				interaction.setResult(this.createOrGetResult(label));
				System.out.println("learn " + interaction.toString());
			}
			else
				interaction.incrementWeight();
			return interaction;
		}

	public Experience createOrGetCompositeExperience(Interaction3 compositeInteraction) {
		Experience experience = this.createOrGetExperience(compositeInteraction.getLabel());
		experience.setInteraction(compositeInteraction);
		return experience;	
	}
	
	@Override
	protected List<Interaction2> getActivatedInteractions(Interaction interaction) {
		List<Interaction2> activatedInteractions = new ArrayList<Interaction2>();
	
		for (Interaction activatedInteraction : this.INTERACTIONS.values())
			if (this.superInteraction != null && this.superInteraction.equals(((Interaction3)activatedInteraction).getPreInteraction()) ||
				interaction != null && interaction.equals(((Interaction3)activatedInteraction).getPreInteraction()) ||
				((Interaction3)interaction).getPostInteraction() != null && ((Interaction3)interaction).getPostInteraction().equals(((Interaction3)activatedInteraction).getPreInteraction())){
				activatedInteractions.add((Interaction3)activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}

}
