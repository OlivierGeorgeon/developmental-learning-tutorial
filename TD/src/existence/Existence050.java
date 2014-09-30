package existence;

import java.util.ArrayList;
import java.util.List;

import agent.Anticipation;
import agent.Anticipation031;
import coupling.Experiment;
import coupling.Experiment040;
import coupling.Result;
import coupling.interaction.Interaction030;
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
		this.environment = new Environment050(this);
		//this.environment = new EnvironmentMaze(this);
	}
	
	@Override
	public String step() {
		
		List<Anticipation> anticipations = anticipate();
		Experiment040 experience =  (Experiment040)selectExperience(anticipations);

		Interaction040 intendedInteraction = experience.getIntendedInteraction();
		System.out.println("Intended "+ intendedInteraction.toString());

		Interaction040 enactedInteraction = enact(intendedInteraction);
		
		if (enactedInteraction != intendedInteraction){
			Result failResult = createOrGetResult(enactedInteraction.getLabel().replace('e', 'E').replace('r', 'R') + ">");
			if (enactedInteraction.getExperience() == null){
				enactedInteraction.setExperience(experience);
				enactedInteraction.setResult(failResult);
			}
			else if (enactedInteraction.getExperience() != experience){
				int valence = enactedInteraction.getValence(); 
				enactedInteraction = (Interaction040)addOrGetPrimitiveInteraction(experience, failResult, valence);
			}
		}
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
	protected List<Anticipation> getDefaultAnticipations(){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		for (Experiment experience : this.EXPERIENCES.values()){
			Experiment040 defaultExperience = (Experiment040)experience;
			if (defaultExperience.getIntendedInteraction().isPrimitive()){
				Anticipation031 anticipation = new Anticipation031(experience, 0);
				anticipations.add(anticipation);
			}
		}
		return anticipations;
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
}
