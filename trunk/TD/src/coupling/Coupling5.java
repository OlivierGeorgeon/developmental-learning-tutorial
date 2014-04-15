package coupling;

import coupling.interaction.Interaction3;

public class Coupling5 extends Coupling3 {

	@Override
	public Interaction3 createOrReinforceCompositeInteraction(
			Interaction3 preInteraction, Interaction3 postInteraction) {
			
			String label = "(" + preInteraction.getLabel() + postInteraction.getLabel() + ")";
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
	
	public Experience getFirstExperience() {
		return createOrGetExperience(LABEL_E1);
	}

}
