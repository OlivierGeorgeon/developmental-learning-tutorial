package existence;

import java.util.ArrayList;
import java.util.List;
import agent.Anticipation;
import agent.Anticipation031;
import coupling.Experience;
import coupling.Experience040;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction030;
import coupling.interaction.Interaction040;

/** 
* Existence040 implements two-step self-programming.
*/
public class Existence040 extends Existence031 {

	private Interaction040 previousSuperInteraction;
	private Interaction040 lastSuperInteraction;

	@Override
	protected void initExistence(){
		Experience040 e1 = (Experience040)addOrGetExperience(LABEL_E1);
		Experience040 e2 = (Experience040)addOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		Interaction040 e11 = (Interaction040)addOrGetPrimitiveInteraction(e1, r1, -1);
		Interaction040 e12 = (Interaction040)addOrGetPrimitiveInteraction(e1, r2, 1);
		Interaction040 e21 = (Interaction040)addOrGetPrimitiveInteraction(e2, r1, -1);
		Interaction040 e22 = (Interaction040)addOrGetPrimitiveInteraction(e2, r2, 1);
		e1.setInteraction(e12); e1.resetAbstract();
		e2.setInteraction(e22); e2.resetAbstract();
	}
	
	@Override
	public String step() {
		
		Experience040 experience = (Experience040)chooseExperience();
		Interaction040 intendedInteraction = experience.getInteraction();

		Interaction040 enactedInteraction = enact(intendedInteraction);
		
		if (enactedInteraction != intendedInteraction && experience.isAbstract()){
			Result failResult = createOrGetResult(enactedInteraction.getLabel().replace('e', 'E').replace('r', 'R') + ">");
			int valence = enactedInteraction.getValence(); 
			enactedInteraction = (Interaction040)addOrGetPrimitiveInteraction(experience, failResult, valence);
		}
		
		this.setPreviousSuperInteraction(this.getLastSuperInteraction());
		this.setContextInteraction(this.getEnactedInteraction());
		this.setEnactedInteraction(enactedInteraction);
		
		return "Enacted " + enactedInteraction.getLabel() + " valence " +  enactedInteraction.getValence();
	}
	
	/**
	 * Learn composite interactions from 
	 * the previous super interaction, the context interaction, and the enacted interaction
	 */
	@Override
	public void learnCompositeInteraction(){
		Interaction040 previousInteraction = this.getContextInteraction();
		Interaction040 lastInteraction = this.getEnactedInteraction();
		Interaction040 previousSuperInteraction = this.getPreviousSuperInteraction();
		Interaction040 lastSuperIntearction = null;
        // learn [previous current] called the super interaction
		if (previousInteraction != null)
			lastSuperIntearction = addOrGetAndReinforceCompositeInteraction(previousInteraction, lastInteraction);
		
		// Learn three-step higher-level interactions
        if (previousSuperInteraction != null && previousInteraction.isPrimitive() && lastInteraction.isPrimitive()){	
            // learn [penultimate [previous current]]
            this.addOrGetAndReinforceCompositeInteraction(previousSuperInteraction.getPreInteraction(), lastSuperIntearction);
            // learn [[penultimate previous] current]
            this.addOrGetAndReinforceCompositeInteraction(previousSuperInteraction, lastInteraction);
        }
        this.setLastSuperInteraction(lastSuperIntearction);
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
			Experience040 abstractExperience = this.addOrGetAbstractExperience(interaction);
			interaction.setExperience(abstractExperience);
        }
    	return interaction;
	}
	
    public Experience040 addOrGetAbstractExperience(Interaction040 compositeInteraction) {
        String label = compositeInteraction.getLabel().replace('e', 'E').replace('r', 'R').replace('>', '|');
        if (!EXPERIENCES.containsKey(label)){
        	Experience040 abstractExperience =  new Experience040(label);
        	abstractExperience.setInteraction(compositeInteraction);
            EXPERIENCES.put(label, abstractExperience);
        }
        return (Experience040)EXPERIENCES.get(label);
}

	@Override
	protected Interaction040 createInteraction(String label){
		return new Interaction040(label);
	}

	/**
	 * Get the list of activated interactions
	 * from the enacted Interaction, the enacted interaction's post-interaction if any, 
	 * and the last super interaction 
	 * and the last super interaction
	 * @param the enacted interaction
	 * @return the list of anticipations
	 */
	@Override
	public List<Interaction> getActivatedInteractions() {
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		for (Interaction interaction : this.INTERACTIONS.values()){
			Interaction040 activatedInteraction = (Interaction040)interaction;
			if (!activatedInteraction.isPrimitive())
				if (activatedInteraction.getPreInteraction() == this.getEnactedInteraction() ||
					activatedInteraction.getPreInteraction() == this.getLastSuperInteraction() ||
					!this.getEnactedInteraction().isPrimitive() &&
					activatedInteraction.getPreInteraction() == this.getEnactedInteraction().getPostInteraction()){
				activatedInteractions.add(activatedInteraction);
				}
		}
		return activatedInteractions;
	}	

	@Override
	protected List<Anticipation> getDefaultPropositions(){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		for (Experience experience : this.EXPERIENCES.values()){
			Experience040 defaultExperience = (Experience040)experience;
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
			if (!enactedPreInteraction.equals(intendedInteraction.getPreInteraction())){
				// TODO interrupt
				return enactedPreInteraction;
			}
			else
			{
				// Enact the post-interaction
				Interaction040 enactedPostInteraction = enact(intendedInteraction.getPostInteraction());
				if (!enactedPostInteraction.equals(intendedInteraction.getPostInteraction())){
					// TODO interrupt
				}
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
		Experience experience = intendedPrimitiveInteraction.getExperience();
		/** Change the returnResult() to change the environment */		
		//Result result = returnResult010(experience);
		//Result result = returnResult030(experience);
		//Result result = returnResult031(experience);
		Result result = returnResult040(experience);
		return (Interaction040)this.addOrGetPrimitiveInteraction(experience, result);
	}

	@Override
	protected Experience040 createExperience(String label){
		return new Experience040(label);
	}
	@Override
	public Interaction040 getContextInteraction(){
		return (Interaction040)super.getContextInteraction();
	}
	@Override
	public Interaction040 getEnactedInteraction(){
		return (Interaction040)super.getEnactedInteraction();
	}
	public Interaction040 getPreviousSuperInteraction() {
		return previousSuperInteraction;
	}
	public void setPreviousSuperInteraction(Interaction040 previousSuperInteraction) {
		this.previousSuperInteraction = previousSuperInteraction;
	}
	public Interaction040 getLastSuperInteraction() {
		return lastSuperInteraction;
	}
	public void setLastSuperInteraction(Interaction040 lastSuperInteraction) {
		this.lastSuperInteraction = lastSuperInteraction;
	}

	/**
	 * Environment040
	 * Results in R2 when the current experience equals the previous experience and differs from the penultimate experience.
	 * and in R1 otherwise.
	 * e1->r1 e1->r2 e2->r1 e2->r2 etc. 
	 */
	private Experience penultimateExperience;
	protected void setPenultimateExperience(Experience penultimateExperience){
		this.penultimateExperience = penultimateExperience;
	}
	protected Experience getPenultimateExperience(){
		return this.penultimateExperience;
	}

	public Result returnResult040(Experience experience){
		
		Result result = this.createOrGetResult(this.LABEL_R1);

		if (this.getPenultimateExperience() != experience &&
			this.getPreviousExperience() == experience)
			result =  this.createOrGetResult(this.LABEL_R2);
		
		this.setPenultimateExperience(this.getPreviousExperience());
		this.setPreviousExperience(experience);
		
		return result;
	}

}
