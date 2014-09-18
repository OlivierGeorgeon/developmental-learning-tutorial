package existence;

import java.util.List;

import agent.Anticipation;
import agent.Anticipation031;
import coupling.Experience050;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction031;
import coupling.interaction.Interaction032;
import coupling.interaction.Interaction040;

public class Existence051 extends Existence050 {

	@Override
	public String step() {
		
		List<Anticipation> anticipations = anticipate();
		Experience050 experience =  (Experience050)selectExperience(anticipations);

		Interaction040 intendedInteraction = experience.getIntendedInteraction();
		System.out.println("Intended "+ intendedInteraction.toString());

		Interaction040 enactedInteraction = enact(intendedInteraction);
		
		if (enactedInteraction != intendedInteraction)
			experience.addEnactedInteraction(enactedInteraction);

		System.out.println("Enacted "+ enactedInteraction.toString());
		
		if (enactedInteraction.getValence() >= 0)
			this.setMood(Mood.PLEASED);
		else
			this.setMood(Mood.PAINED);

		this.learnCompositeInteraction(enactedInteraction);
		
		this.setPreviousSuperInteraction(this.getLastSuperInteraction());
		this.setEnactedInteraction(enactedInteraction);
		
		return "" + this.getMood();
	}

	/**
	 * Computes the list of anticipations
	 * @return the list of anticipations
	 */
	@Override
	public List<Anticipation> anticipate(){

		List<Anticipation> anticipations = getDefaultAnticipations();
		List<Interaction> activatedInteractions =  this.getActivatedInteractions();
		
		if (this.getEnactedInteraction() != null){
			for (Interaction activatedInteraction : activatedInteractions){
				if (((Interaction031)activatedInteraction).getPostInteraction().getExperience() != null){
					Anticipation031 anticipation = new Anticipation031(((Interaction031)activatedInteraction).getPostInteraction().getExperience(), ((Interaction031)activatedInteraction).getWeight() * ((Interaction031)activatedInteraction).getPostInteraction().getValence());
					int index = anticipations.indexOf(anticipation);
					if (index < 0)
						anticipations.add(anticipation);
					else
						((Anticipation031)anticipations.get(index)).addProclivity(((Interaction031)activatedInteraction).getWeight() * ((Interaction031)activatedInteraction).getPostInteraction().getValence());
				}
			}
		}
		
		for (Anticipation anticipation : anticipations){
			for (Interaction interaction : ((Experience050)((Anticipation031)anticipation).getExperience()).getEnactedInteractions()){
				for (Interaction activatedInteraction : activatedInteractions){
					if (interaction == ((Interaction032)activatedInteraction).getPostInteraction()){
						int proclivity = ((Interaction032)activatedInteraction).getWeight() * ((Interaction032)interaction).getValence(); 
						((Anticipation031)anticipation).addProclivity(proclivity);
					}
				}
			}
		}

		return anticipations;
	}

	@Override
    public Experience050 addOrGetAbstractExperience(Interaction040 interaction) {
        String label = interaction.getLabel().replace('e', 'E').replace('r', 'R').replace('>', '|');
        if (!EXPERIENCES.containsKey(label)){
        	Experience050 abstractExperience =  new Experience050(label);
        	abstractExperience.setIntendedInteraction(interaction);
			interaction.setExperience(abstractExperience);
            EXPERIENCES.put(label, abstractExperience);
        }
        return (Experience050)EXPERIENCES.get(label);
    }

}
