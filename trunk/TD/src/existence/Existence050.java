package existence;

import java.util.ArrayList;
import java.util.List;

import agent.Anticipation;
import agent.Anticipation031;
import coupling.Experience;
import coupling.Experience040;
import coupling.Experience050;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction010;
import coupling.interaction.Interaction030;
import coupling.interaction.Interaction031;
import coupling.interaction.Interaction040;
import environment.Environment;
import environment.Environment050;

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
		/** You can instanciate another environment here. */
		this.environment = new Environment050(this);
	}
	
	@Override
	public String step() {
		
		Experience050 experience = (Experience050)chooseExperience();
		Interaction040 intendedInteraction = experience.getIntendedInteraction();

		Interaction040 enactedInteraction = enact(intendedInteraction);
		
		if (enactedInteraction != intendedInteraction && experience.isAbstract()){
			//Result failResult = createOrGetResult(enactedInteraction.getLabel().replace('e', 'E').replace('r', 'R') + ">");
			experience.addEnactedInteraction(enactedInteraction);
		}
		
		this.setPreviousSuperInteraction(this.getLastSuperInteraction());
		this.setContextInteraction(this.getEnactedInteraction());
		this.setEnactedInteraction(enactedInteraction);
		
		return "Enacted " + enactedInteraction.getLabel() + " valence " +  enactedInteraction.getValence();
	}
	
	/**
	 * Create an interaction from its label.
	 * @param label: This interaction's label.
	 * @return The created interaction
	 */
	public Interaction040 addOrGetPrimitiveInteraction(String label, int valence) {
		Interaction040 interaction = (Interaction040)addOrGetInteraction(label); 
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
    public Experience050 addOrGetAbstractExperience(Interaction040 compositeInteraction) {
        String label = compositeInteraction.getLabel().replace('e', 'E').replace('r', 'R').replace('>', '|');
        if (!EXPERIENCES.containsKey(label)){
        	Experience050 abstractExperience =  new Experience050(label);
        	abstractExperience.setIntendedInteraction(compositeInteraction);
            EXPERIENCES.put(label, abstractExperience);
        }
        return (Experience050)EXPERIENCES.get(label);
    }

	/**
	 * Computes the list of anticipations
	 * @return the list of anticipations
	 */
	@Override
	public List<Anticipation> computeAnticipations(){
		List<Anticipation> anticipations = this.getDefaultPropositions(); 
		
		if (this.getEnactedInteraction() != null){
			for (Interaction activatedInteraction : getActivatedInteractions()){
				Anticipation031 proposition = new Anticipation031(((Interaction031)activatedInteraction).getPostInteraction().getExperience(), ((Interaction031)activatedInteraction).getWeight() * ((Interaction031)activatedInteraction).getPostInteraction().getValence());
				int index = anticipations.indexOf(proposition);
				if (index < 0)
					anticipations.add(proposition);
				else
					((Anticipation031)anticipations.get(index)).addProclivity(((Interaction031)activatedInteraction).getWeight() * ((Interaction031)activatedInteraction).getPostInteraction().getValence());
			}
		}
		return anticipations;
	}

	/**
	 * Experiences corresponding to 
	 * primitive interactions that have a positive or null valence 
	 * are proposed by default
	 * @return the list of anticipations
	 */
	@Override
	protected List<Anticipation> getDefaultPropositions(){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		for (Interaction interaction: this.INTERACTIONS.values()){
			Interaction040 proposedInteraction = (Interaction040)interaction; 
			if (proposedInteraction.isPrimitive() && proposedInteraction.getValence() >= 0){
				Experience050 experience = this.addOrGetAbstractExperience(proposedInteraction);
				Anticipation031 anticipation = new Anticipation031(experience, 0);
				anticipations.add(anticipation);
			}
		}
		return anticipations;
	}

}
