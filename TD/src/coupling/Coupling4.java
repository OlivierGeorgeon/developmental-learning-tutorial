package coupling;

import agent.decider.Episode4;
import coupling.interaction.Interaction3;

public class Coupling4 extends Coupling3 {

	public Episode4 createEpisode(Experience experience) {
		return new Episode4(this, experience);
	}

	@Override
	public Interaction3 createOrReinforceCompositeInteraction(
			Interaction3 preInteraction, Interaction3 postInteraction) {
			
			String label = "(" + preInteraction.getLabel();// + 
			label += postInteraction.getLabel() + ")";
			Interaction3 interaction = getInteraction(label);
			if (interaction == null){
				int valence = preInteraction.getValence() + postInteraction.getValence();	
				interaction = this.createOrGet(label, valence); 
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
}
