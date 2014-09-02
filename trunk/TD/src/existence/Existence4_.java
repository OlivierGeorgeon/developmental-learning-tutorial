package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.decider.Proposition;
import coupling.interaction.Interaction;
//import coupling.interaction.Interaction2;
import coupling.interaction.Interaction4;
import coupling.Experience;
import coupling.Experience4;

public class Existence4_ extends Existence3_ {

	private Interaction4 interaction_1;
	private Interaction4 interaction_2;
	private Interaction4 superInteraction;

	@Override
	protected void learn(){

	}

	@Override
	public Interaction4 createOrReinforceCompositeInteraction(Interaction preInteraction, Interaction postInteraction) {
			
			String label = "(" + preInteraction.getLabel() + postInteraction.getLabel() + ")";
			Interaction4 interaction = (Interaction4)getInteraction(label);
			if (interaction == null){
				int valence = preInteraction.getValence() + postInteraction.getValence();	
				interaction = (Interaction4)this.createOrGet(label, valence); 
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

	public Experience4 createOrGetCompositeExperience(Interaction4 compositeInteraction) {
		Experience4 experience = (Experience4)this.createOrGetExperience(compositeInteraction.getLabel());
		experience.setInteraction(compositeInteraction);
		return experience;	
	}
	
	@Override
	protected List<Interaction> getActivatedInteractions(Interaction interaction) {
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
	
		for (Interaction activatedInteraction : this.INTERACTIONS.values())
			if (this.superInteraction != null && this.superInteraction.equals(((Interaction4)activatedInteraction).getPreInteraction()) ||
				interaction != null && interaction.equals(((Interaction4)activatedInteraction).getPreInteraction()) ||
				((Interaction4)interaction).getPostInteraction() != null && ((Interaction4)interaction).getPostInteraction().equals(((Interaction4)activatedInteraction).getPreInteraction())){
				activatedInteractions.add((Interaction4)activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}

}
