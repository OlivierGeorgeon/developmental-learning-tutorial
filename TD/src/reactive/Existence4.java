package reactive;

import coupling.interaction.Interaction3;
import coupling.Experience;

public class Existence4 extends Existence3 {

	@Override
	public Interaction3 createOrReinforceCompositeInteraction(
			Interaction3 preInteraction, Interaction3 postInteraction) {
			
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

}
