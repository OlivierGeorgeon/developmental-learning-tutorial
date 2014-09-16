package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.Anticipation;
import agent.Anticipation030;
import agent.Anticipation031;
import agent.Anticipation032;
import coupling.Experience;
import coupling.Experience040;
import coupling.Experience050;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction010;
import coupling.interaction.Interaction020;
import coupling.interaction.Interaction030;
import coupling.interaction.Interaction031;
import coupling.interaction.Interaction032;
import coupling.interaction.Interaction040;
import environment.Environment;
import environment.Environment050;
import environment.EnvironmentMaze;
import existence.Existence010.Mood;

/** 
* Existence050 implements radical interactionism.
*/
public class Existence050 extends Existence040 {

	private Environment environment;
	protected Environment getEnvironment(){
		return this.environment;
	}

	@Override
	protected void initExistence(){
		/** You can instantiate another environment here. */
		//this.environment = new Environment050(this);
		this.environment = new EnvironmentMaze(this);
	}
	
	@Override
	public String step() {
		
		List<Anticipation> anticipations = computeAnticipations();
		Interaction040 intendedInteraction = (Interaction040)selectInteraction(anticipations); 
			
		Interaction040 enactedInteraction = enact(intendedInteraction);
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

		this.setPreviousSuperInteraction(this.getLastSuperInteraction());
		this.setEnactedInteraction(enactedInteraction);
		
		return "" + this.getMood();
	}
	
	/**
	 * Create an interaction from its label.
	 * @param label: This interaction's label.
	 * @param valence: the interaction's valence.
	 * @return The created interaction
	 */
	public Interaction040 addOrGetPrimitiveInteraction(String label, int valence) {
		if (!INTERACTIONS.containsKey(label)){
			Interaction040 interaction = createInteraction(label);
			interaction.setValence(valence);
			INTERACTIONS.put(label, interaction);			
		}
		Interaction040 interaction = (Interaction040)INTERACTIONS.get(label);
		return interaction;
	}
	
	@Override
	public Interaction040 enact(Interaction030 intendedInteraction){

		if (intendedInteraction.isPrimitive())
			return (Interaction040)this.getEnvironment().enact(intendedInteraction);
		else {			
			// Enact the pre-interaction
			Interaction040 enactedPreInteraction = enact(intendedInteraction.getPreInteraction());
			if (!enactedPreInteraction.equals(intendedInteraction.getPreInteraction()))
				// if the preInteraction failed then the enaction of the intendedInteraction is interrupted here.
				return enactedPreInteraction;
			else{
				// Enact the post-interaction
				Interaction040 enactedPostInteraction = enact(intendedInteraction.getPostInteraction());
				return (Interaction040)addOrGetCompositeInteraction(enactedPreInteraction, enactedPostInteraction);
			}
		}
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
	public List<Anticipation> computeAnticipations(){

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

	
//	@Override
//    public Experience050 addOrGetAbstractExperience(Interaction040 compositeInteraction) {
//        String label = compositeInteraction.getLabel().replace('e', 'E').replace('r', 'R').replace('*', '|');
//        if (!EXPERIENCES.containsKey(label)){
//        	Experience050 abstractExperience =  new Experience050(label);
//        	abstractExperience.setIntendedInteraction(compositeInteraction);
//            EXPERIENCES.put(label, abstractExperience);
//        }
//        return (Experience050)EXPERIENCES.get(label);
//    }

//	/**
//	 * Computes the list of anticipations
//	 * @return the list of anticipations
//	 */
//	@Override
//	public List<Anticipation> computeAnticipations(){
//		List<Anticipation> anticipations = this.getDefaultAnticipations(); 
//		
//		List<Interaction> activatedInteractions = getActivatedInteractions(); 
//		
//		for (Interaction interaction : activatedInteractions){
//			Interaction031 activatedInteraction = (Interaction031)interaction; 
//			Experience050 proposedExperience = (Experience050)activatedInteraction.getPostInteraction().getExperience();
//			if (proposedExperience != null){
//				int proclivity = ((Interaction031)activatedInteraction).getWeight() * ((Interaction031)activatedInteraction).getPostInteraction().getValence();
//				Anticipation031 proposition = new Anticipation031(proposedExperience, proclivity);
//				int index = anticipations.indexOf(proposition);
//				if (index < 0)
//					anticipations.add(proposition);
//				else
//					((Anticipation031)anticipations.get(index)).addProclivity(proclivity);
//			}
//		}
//		
//		for (Anticipation anticipation : anticipations){
//			Experience050 experience = (Experience050)((Anticipation031)anticipation).getExperience();
//			for (Interaction interaction : experience.getEnactedInteractions()){
//				for (Interaction activatedInteraction : activatedInteractions){
//					if (interaction == ((Interaction031)activatedInteraction).getPostInteraction()){
//						int proclivity = ((Interaction031)activatedInteraction).getWeight() * ((Interaction031)interaction).getValence(); 
//						((Anticipation031)anticipation).addProclivity(proclivity);
//					}
//				}
//			}
//		}
//		
//		return anticipations;
//	}

//	@Override
//	public List<Interaction> getActivatedInteractions() {
//		
//		List<Interaction> contextInteractions = new ArrayList<Interaction>();
//		
//		if (this.getEnactedInteraction()!=null){
//			contextInteractions.add(this.getEnactedInteraction());
//			if (!this.getEnactedInteraction().isPrimitive()){
//				contextInteractions.add(this.getEnactedInteraction().getPostInteraction());
////				if (!this.getEnactedInteraction().getPostInteraction().isPrimitive())
////					contextInteractions.add(this.getEnactedInteraction().getPostInteraction().getPostInteraction());
//			}
//			if (this.getLastSuperInteraction() != null){
//				contextInteractions.add(this.getLastSuperInteraction());
//				if (!this.getLastSuperInteraction().getPreInteraction().isPrimitive()){
//					Interaction test = this.addOrGetCompositeInteraction(this.getLastSuperInteraction().getPreInteraction().getPostInteraction(), this.getLastSuperInteraction().getPostInteraction());
//					contextInteractions.add(test);				
//				}
//			}
//		}
//		
//		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
//		for (Interaction interaction : this.INTERACTIONS.values()){
//			Interaction040 activatedInteraction = (Interaction040)interaction;
//			if (!activatedInteraction.isPrimitive())
//				if (contextInteractions.contains(activatedInteraction.getPreInteraction()))
//					activatedInteractions.add(activatedInteraction);
//		}
//		return activatedInteractions;
//	}	

//	/**
//	 * Experiences corresponding to 
//	 * primitive interactions that have a positive or null valence 
//	 * are proposed by default
//	 * @return the list of anticipations
//	 */
//	@Override
//	protected List<Anticipation> getDefaultAnticipations(){
//		List<Anticipation> anticipations = new ArrayList<Anticipation>();
//		for (Interaction proposedInteraction: this.INTERACTIONS.values()){
//			if (((Interaction040)proposedInteraction).isPrimitive() && ((Interaction040)proposedInteraction).getValence() >= 0){
//				Experience050 experience = this.addOrGetAbstractExperience((Interaction040)proposedInteraction);
//				Anticipation031 anticipation = new Anticipation031(experience, 0);
//				anticipations.add(anticipation);
//			}
//		}
//		return anticipations;
//	}

}
