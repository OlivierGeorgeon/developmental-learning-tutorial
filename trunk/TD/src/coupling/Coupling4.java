package coupling;

import agent.decider.Episode4;
import coupling.interaction.Interaction3;

public class Coupling4 extends Coupling3 {
	
	public Episode4 createEpisode(Episode4 contextEpisode) {
		Experience experience; 
		if (contextEpisode == null)
			experience = this.createOrGetExperience(Coupling.LABEL_E1); 
		else
			experience = contextEpisode.propose();
		
		Episode4 episode = new Episode4(this, experience);
		episode.setContextEpisode(contextEpisode);
		return episode;
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
	
	public void store(Episode4 currentEpisode){
		Episode4 contextEpisode = currentEpisode.getContextEpisode();
		if (contextEpisode != null){
			// learn [previous current]
			Interaction3 superInteraction = this.createOrReinforceCompositeInteraction(contextEpisode.getInteraction(), currentEpisode.getInteraction());

			if (currentEpisode.getExperience().isPrimitive() && 
					contextEpisode.getExperience().isPrimitive() &&	contextEpisode.getContextEpisode()!= null ){
				// learn [penultimate [previous current]]
				this.createOrReinforceCompositeInteraction(contextEpisode.getContextEpisode().getInteraction(), superInteraction);
				// learn [[penultimate previous] current]
				this.createOrReinforceCompositeInteraction(contextEpisode.getSuperInteraction(), currentEpisode.getInteraction());	
			
				//if (contextEpisode.getContextEpisode().getSuperInteraction() != null)
				//	this.createOrReinforceCompositeInteraction(contextEpisode.getContextEpisode().getSuperInteraction(), superInteraction);			
			}
			currentEpisode.setSuperInteraction(superInteraction);
		}
	}
}
