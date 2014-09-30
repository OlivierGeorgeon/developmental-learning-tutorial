package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.Anticipation;
import agent.Anticipation030;
import agent.Anticipation032;
import coupling.Experiment;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction030;
import coupling.interaction.Interaction031;
import coupling.interaction.Interaction032;

public class Existence032 extends Existence031 {

	@Override
	public String step() {
		
		List<Anticipation> anticipations = anticipate();
		Interaction032 intendedInteraction =  selectInteraction(anticipations);
		Experiment experience = intendedInteraction.getExperience();
		
		/** Change the call to the function returnResult to change the environment */
		//Result result = returnResult010(experience);
		//Result result = returnResult030(experience);
		Result result = returnResult031(experience);
	
		Interaction030 enactedInteraction = getInteraction(experience.getLabel() + result.getLabel());
		System.out.println("Enacted "+ enactedInteraction.toString());
		
		if (enactedInteraction != intendedInteraction){
			intendedInteraction.addAlternateInteraction(enactedInteraction);
			System.out.println("Alternate "+ enactedInteraction.getLabel());
		}
		
		if (enactedInteraction.getValence() >= 0)
			this.setMood(Mood.PLEASED);
		else
			this.setMood(Mood.PAINED);
		
		this.learnCompositeInteraction(enactedInteraction);

		this.setEnactedInteraction(enactedInteraction);
		
		return "" + this.getMood();
	}

	@Override
	protected Interaction032 selectInteraction(List<Anticipation> anticipations){
		
		Collections.sort(anticipations);
		for (Anticipation anticipation : anticipations)
			System.out.println("anticipate " + anticipation.toString());
		
		Anticipation030 selectedAnticipation = (Anticipation030)anticipations.get(0);
		Interaction032 intendedInteraction = (Interaction032)selectedAnticipation.getInteraction();

		return intendedInteraction;
	}
	
	/**
	 * Computes the list of anticipations
	 * @return the list of anticipations
	 */
	@Override
	public List<Anticipation> anticipate(){

		List<Anticipation> anticipations = getDefaultAnticipations();
		List<Interaction> activatedInteractions =  this.getActivatedInteractions();
		
		for (Interaction activatedInteraction : activatedInteractions){
			Interaction032 proposedInteraction = (Interaction032)((Interaction032)activatedInteraction).getPostInteraction();
			int proclivity = ((Interaction032)activatedInteraction).getWeight() * proposedInteraction.getValence();
			Anticipation032 anticipation = new Anticipation032(proposedInteraction, proclivity);
			int index = anticipations.indexOf(anticipation);
			if (index < 0)
				anticipations.add(anticipation);
			else
				((Anticipation032)anticipations.get(index)).addProclivity(proclivity);
		}
		
		for (Anticipation anticipation : anticipations){
			for (Interaction interaction : ((Interaction032)((Anticipation032)anticipation).getInteraction()).getAletnerateInteractions()){
				for (Interaction activatedInteraction : activatedInteractions){
					if (interaction == ((Interaction031)activatedInteraction).getPostInteraction()){
						int proclivity = ((Interaction031)activatedInteraction).getWeight() * ((Interaction031)interaction).getValence(); 
						((Anticipation032)anticipation).addProclivity(proclivity);
					}
				}
			}
		}

		return anticipations;
	}
	
	/**
	 * all primitive interactions are proposed by default with a proclivity of 0
	 * @return the list of anticipations
	 */
	@Override
	protected List<Anticipation> getDefaultAnticipations(){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		for (Interaction i : this.INTERACTIONS.values()){
			Interaction032 interaction = (Interaction032)i;
			if (interaction.isPrimitive()){
				Anticipation032 anticipation = new Anticipation032(interaction, 0);
				anticipations.add(anticipation);
			}
		}
		return anticipations;
	}

	@Override
	protected Interaction032 createInteraction(String label){
		return new Interaction032(label);
	}
}
