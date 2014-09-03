package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.decider.Proposition;
import coupling.interaction.Interaction;
//import coupling.interaction.Interaction2;
import coupling.interaction.Interaction040;
import coupling.Experience;
import coupling.Experience4;

public class Existence4_ extends Existence3_ {

	private Interaction040 interaction_1;
	private Interaction040 interaction_2;
	private Interaction040 superInteraction;

	@Override
	protected void learn(){

	}

	@Override
	public Interaction040 createOrReinforceCompositeInteraction(Interaction preInteraction, Interaction postInteraction) {
			
			String label = "(" + preInteraction.getLabel() + postInteraction.getLabel() + ")";
			Interaction040 interaction = (Interaction040)getInteraction(label);
			if (interaction == null){
				int valence = preInteraction.getValence() + postInteraction.getValence();	
				interaction = (Interaction040)this.addOrGetIntearction(label, valence); 
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

	public Experience4 createOrGetCompositeExperience(Interaction040 compositeInteraction) {
		Experience4 experience = (Experience4)this.createOrGetExperience(compositeInteraction.getLabel());
		experience.setInteraction(compositeInteraction);
		return experience;	
	}
	
	@Override
	protected List<Interaction> getActivatedInteractions(Interaction interaction) {
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
	
		for (Interaction activatedInteraction : this.INTERACTIONS.values())
			if (this.superInteraction != null && this.superInteraction.equals(((Interaction040)activatedInteraction).getPreInteraction()) ||
				interaction != null && interaction.equals(((Interaction040)activatedInteraction).getPreInteraction()) ||
				((Interaction040)interaction).getPostInteraction() != null && ((Interaction040)interaction).getPostInteraction().equals(((Interaction040)activatedInteraction).getPreInteraction())){
				activatedInteractions.add((Interaction040)activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}

}
