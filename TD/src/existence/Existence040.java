package existence;

import java.util.ArrayList;
import java.util.List;
import agent.Anticipation;
import agent.Anticipation031;
import coupling.Experiment;
import coupling.Experiment040;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction030;
import coupling.interaction.Interaction040;

/** 
* Existence040 implements two-step self-programming.
*/
public class Existence040 extends Existence031 {

	private Interaction040 superInteraction;

	@Override
	protected void initExistence(){
		Experiment040 e1 = (Experiment040)addOrGetExperience(LABEL_E1);
		Experiment040 e2 = (Experiment040)addOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		/** Change the valence depending on the environment to obtain better behaviors */
		Interaction040 e11 = (Interaction040)addOrGetPrimitiveInteraction(e1, r1, -1);
		Interaction040 e12 = (Interaction040)addOrGetPrimitiveInteraction(e1, r2, 1); // Use valence 1 for Environment040 and 2 for Environment041
		Interaction040 e21 = (Interaction040)addOrGetPrimitiveInteraction(e2, r1, -1);
		Interaction040 e22 = (Interaction040)addOrGetPrimitiveInteraction(e2, r2, 1); // Use valence 1 for Environment040 and 2 for Environment041
		e1.setIntendedInteraction(e12); e1.resetAbstract();
		e2.setIntendedInteraction(e22); e2.resetAbstract();
	}
	
	@Override
	public String step() {
		
		List<Anticipation> anticipations = anticipate();
		Experiment040 experiment =  (Experiment040)selectExperiment(anticipations);

		Interaction040 intendedInteraction = experiment.getIntendedInteraction();

		Interaction040 enactedInteraction = enact(intendedInteraction);
		System.out.println("Enacted "+ enactedInteraction.toString());
		
		if (enactedInteraction != intendedInteraction && experiment.isAbstract()){
			Result failResult = createOrGetResult(enactedInteraction.getLabel().replace('e', 'E').replace('r', 'R') + ">");
			int valence = enactedInteraction.getValence(); 
			enactedInteraction = (Interaction040)addOrGetPrimitiveInteraction(experiment, failResult, valence);
		}
		
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
	 * Learn composite interactions from 
	 * the previous super interaction, the context interaction, and the enacted interaction
	 */
	@Override
	public void learnCompositeInteraction(Interaction030 enactedInteraction){
		Interaction040 previousInteraction = this.getEnactedInteraction(); 
		Interaction040 lastInteraction = (Interaction040)enactedInteraction;
		Interaction040 previousSuperInteraction = this.getSuperInteraction();
		Interaction040 lastSuperInteraction = null;
        // learn [previous current] called the super interaction
		if (previousInteraction != null)
			lastSuperInteraction = addOrGetAndReinforceCompositeInteraction(previousInteraction, lastInteraction);
		
		// Learn higher-level interactions
        if (previousSuperInteraction != null 
        		//&& previousInteraction.isPrimitive() && lastInteraction.isPrimitive()
        		){	
            // learn [penultimate [previous current]]
            this.addOrGetAndReinforceCompositeInteraction(previousSuperInteraction.getPreInteraction(), lastSuperInteraction);
            // learn [[penultimate previous] current]
            this.addOrGetAndReinforceCompositeInteraction(previousSuperInteraction, lastInteraction);
        }
        this.setSuperInteraction(lastSuperInteraction);
	}
	
	public Interaction040 addOrGetAndReinforceCompositeInteraction(Interaction030 preInteraction, Interaction030 postInteraction){
		Interaction040 compositeInteraction = addOrGetCompositeInteraction(preInteraction, postInteraction);
		compositeInteraction.incrementWeight();
		if (compositeInteraction.getWeight() == 1)
			System.out.println("learn " + compositeInteraction.toString());
		else
			System.out.println("reinforce " + compositeInteraction.toString());

		return compositeInteraction;
	}

	/**
	 * Records or get a composite interaction in memory
	 * If a new composite interaction is created, then a new abstract experience is also created and associated to it.
	 * @param preInteraction: The composite interaction's pre-interaction
	 * @param postInteraction: The composite interaction's post-interaction
	 * @return the learned composite interaction
	 */
	@Override
	public Interaction040 addOrGetCompositeInteraction(Interaction030 preInteraction, Interaction030 postInteraction) {
		String label = "<" + preInteraction.getLabel() + postInteraction.getLabel() + ">";
        Interaction040 interaction = (Interaction040)getInteraction(label);
        if (interaction == null){
			interaction = (Interaction040)addOrGetInteraction(label); 
			interaction.setPreInteraction(preInteraction);
			interaction.setPostInteraction(postInteraction);
			interaction.setValence(preInteraction.getValence() + postInteraction.getValence());
			this.addOrGetAbstractExperiment(interaction);
			//interaction.setExperience(abstractExperience);
        }
    	return interaction;
	}
	
    public Experiment040 addOrGetAbstractExperiment(Interaction040 interaction) {
        String label = interaction.getLabel().replace('e', 'E').replace('r', 'R').replace('>', '|');
        if (!EXPERIENCES.containsKey(label)){
        	Experiment040 abstractExperience =  new Experiment040(label);
        	abstractExperience.setIntendedInteraction(interaction);
			interaction.setExperience(abstractExperience);
            EXPERIENCES.put(label, abstractExperience);
        }
        return (Experiment040)EXPERIENCES.get(label);
    }

	@Override
	protected Interaction040 createInteraction(String label){
		return new Interaction040(label);
	}

	/**
	 * Get the list of activated interactions
	 * from the enacted Interaction, the enacted interaction's post-interaction if any, 
	 * and the last super interaction
	 * @param the enacted interaction
	 * @return the list of anticipations
	 */
	@Override
	public List<Interaction> getActivatedInteractions() {
		
		List<Interaction> contextInteractions = new ArrayList<Interaction>();
		
		if (this.getEnactedInteraction()!=null){
			contextInteractions.add(this.getEnactedInteraction());
			if (!this.getEnactedInteraction().isPrimitive())
				contextInteractions.add(this.getEnactedInteraction().getPostInteraction());
//			if (this.getLastSuperInteraction() != null)
//				contextInteractions.add(this.getLastSuperInteraction());
			if (this.getSuperInteraction() != null)
				contextInteractions.add(this.getSuperInteraction());
		}
		
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		for (Interaction interaction : this.INTERACTIONS.values()){
			Interaction040 activatedInteraction = (Interaction040)interaction;
			if (!activatedInteraction.isPrimitive())
				if (contextInteractions.contains(activatedInteraction.getPreInteraction())){
					activatedInteractions.add(activatedInteraction);
					System.out.println("activated " + activatedInteraction.toString());
				}
		}
		return activatedInteractions;
	}	

	@Override
	protected List<Anticipation> getDefaultAnticipations(){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		for (Experiment experience : this.EXPERIENCES.values()){
			Experiment040 defaultExperience = (Experiment040)experience;
			if (!defaultExperience.isAbstract()){
				Anticipation031 anticipation = new Anticipation031(experience, 0);
				anticipations.add(anticipation);
			}
		}
		return anticipations;
	}

	public Interaction040 enact(Interaction030 intendedInteraction){

		if (intendedInteraction.isPrimitive())
			return enactPrimitiveIntearction(intendedInteraction);
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

	/**
	 * Implements the cognitive coupling between the agent and the environment
	 * @param intendedPrimitiveInteraction: The intended primitive interaction to try to enact against the environment
	 * @param The actually enacted primitive interaction.
	 */
	public Interaction040 enactPrimitiveIntearction(Interaction030 intendedPrimitiveInteraction){
		Experiment experience = intendedPrimitiveInteraction.getExperience();
		/** Change the returnResult() to change the environment 
		 *  Change the valence of primitive interactions to obtain better behaviors */		
		//Result result = returnResult010(experience);
		//Result result = returnResult030(experience);
		//Result result = returnResult031(experience);
		Result result = returnResult040(experience);
		//Result result = returnResult041(experience);
		return (Interaction040)this.addOrGetPrimitiveInteraction(experience, result);
	}

	@Override
	protected Experiment040 createExperience(String label){
		return new Experiment040(label);
	}

	@Override
	public Interaction040 getEnactedInteraction(){
		return (Interaction040)super.getEnactedInteraction();
	}
	public Interaction040 getSuperInteraction() {
		return superInteraction;
	}
	public void setSuperInteraction(Interaction040 previousSuperInteraction) {
		this.superInteraction = previousSuperInteraction;
	}

	/**
	 * Environment040
	 * Results in R2 when the current experience equals the previous experience and differs from the penultimate experience.
	 * and in R1 otherwise.
	 * e1->r1 e1->r2 e2->r1 e2->r2 etc. 
	 */
	private Experiment penultimateExperiment;
	protected void setPenultimateExperiment(Experiment penultimateExperiment){
		this.penultimateExperiment = penultimateExperiment;
	}
	protected Experiment getPenultimateExperiment(){
		return this.penultimateExperiment;
	}

	public Result returnResult040(Experiment experience){
		
		Result result = this.createOrGetResult(this.LABEL_R1);

		if (this.getPenultimateExperiment() != experience &&
			this.getPreviousExperiment() == experience)
			result =  this.createOrGetResult(this.LABEL_R2);
		
		this.setPenultimateExperiment(this.getPreviousExperiment());
		this.setPreviousExperiment(experience);
		
		return result;
	}
	
	/**
	 * Environment041
	 * The agent must alternate experiences e1 and e2 every third cycle to get one r2 result the third time:
	 * e1->r1 e1->r1 e1->r2 e2->r1 e2->r1 e2->r2 etc. 
	 */
	protected Experiment antepenultimateExperiment;
	protected void setAntePenultimateExperiment(Experiment antepenultimateExperiment){
		this.antepenultimateExperiment = antepenultimateExperiment;
	}
	protected Experiment getAntePenultimateExperiment(){
		return this.antepenultimateExperiment;
	}

	public Result returnResult041(Experiment experiment){
		
		Result result = this.createOrGetResult(this.LABEL_R1);

		if (this.getAntePenultimateExperiment() != experiment &&
			this.getPenultimateExperiment() == experiment &&
			this.getPreviousExperiment() == experiment)
			result =  this.createOrGetResult(this.LABEL_R2);
		
		this.setAntePenultimateExperiment(this.getPenultimateExperiment());
		this.setPenultimateExperiment(this.getPreviousExperiment());
		this.setPreviousExperiment(experiment);
		
		return result;
	}

}
