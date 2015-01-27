package existence;

import java.util.ArrayList;
import java.util.List;

import agent.Anticipation;
import agent.Anticipation031;
import coupling.Experiment;
import coupling.Experiment040;
import coupling.Experiment050;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction030;
import coupling.interaction.Interaction031;
import coupling.interaction.Interaction040;
import environment.Environment;
import environment.Environment050;
import environment.EnvironmentMaze;

public class Existence050 extends Existence040{

	private Environment environment;
	protected Environment getEnvironment(){
		return this.environment;
	}
	
	protected void setEnvironment(Environment environment){
		this.environment = environment;
	}

	@Override
	protected void initExistence(){
		/** You can instantiate another environment here. */
		this.setEnvironment(new Environment050(this));
		//this.setEnvironment(new EnvironmentMaze(this));
	}
	
	@Override
	public String step() {
		
		List<Anticipation> anticipations = anticipate();
		Experiment050 experiment =  (Experiment050)selectExperiment(anticipations);

		Interaction040 intendedInteraction = experiment.getIntendedInteraction();
		System.out.println("Intended "+ intendedInteraction.toString());

		Interaction040 enactedInteraction = enact(intendedInteraction);
		
		if (enactedInteraction != intendedInteraction)
			experiment.addEnactedInteraction(enactedInteraction);

		System.out.println("Enacted "+ enactedInteraction.toString());
		
		if (enactedInteraction.getValence() >= 0)
			this.setMood(Mood.PLEASED);
		else
			this.setMood(Mood.PAINED);

		this.learnCompositeInteraction(enactedInteraction);
		
		//this.setPreviousSuperInteraction(this.getLastSuperInteraction());
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
			for (Interaction interaction : ((Experiment050)((Anticipation031)anticipation).getExperience()).getEnactedInteractions()){
				for (Interaction activatedInteraction : activatedInteractions){
					if (interaction == ((Interaction031)activatedInteraction).getPostInteraction()){
						int proclivity = ((Interaction031)activatedInteraction).getWeight() * ((Interaction031)interaction).getValence(); 
						((Anticipation031)anticipation).addProclivity(proclivity);
					}
				}
			}
		}

		return anticipations;
	}

	@Override
    public Experiment050 addOrGetAbstractExperiment(Interaction040 interaction) {
        String label = interaction.getLabel().replace('e', 'E').replace('r', 'R').replace('>', '|');
        if (!EXPERIENCES.containsKey(label)){
        	Experiment050 abstractExperience =  new Experiment050(label);
        	abstractExperience.setIntendedInteraction(interaction);
			interaction.setExperience(abstractExperience);
            EXPERIENCES.put(label, abstractExperience);
        }
        return (Experiment050)EXPERIENCES.get(label);
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
