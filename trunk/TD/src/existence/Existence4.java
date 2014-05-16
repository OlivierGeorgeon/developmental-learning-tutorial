package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import agent.decider.Proposition;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;
import coupling.interaction.Interaction3;
import coupling.Experience;

public class Existence4 extends Existence3 {

	private Interaction3 interaction_1;
	private Interaction3 interaction_2;
	private Interaction3 superInteraction;

	@Override
	protected void learn(){

		Interaction3 enactedPrimitiveInteraction = this.getCoupling().getInteraction(this.primitiveExperience.getLabel() + result.getLabel());
		if (this.getExperience().isPrimitive()){
			this.setInteraction(enactedPrimitiveInteraction);
			this.terminated = true;
		}
		else{
			if (step == 0){
				if (!enactedPrimitiveInteraction.equals(this.getExperience().getInteraction().getPreInteraction())){
					Interaction3 alternateInteraction = this.getCoupling().createOrGetInteraction(this.getExperience(), result, enactedPrimitiveInteraction.getValence());
					this.setInteraction(alternateInteraction);
					System.out.println("alternate interaction " + alternateInteraction.getLabel());
					this.terminated = true;
				}
				step++;
			}
			else{
				if (enactedPrimitiveInteraction.equals(this.getExperience().getInteraction().getPostInteraction()))
					this.setInteraction(this.getExperience().getInteraction());
				else{
					this.getCoupling().createOrGetPrimitiveInteraction(this.getExperience(), result, this.getExperience().getInteraction().getPreInteraction().getValence() + enactedPrimitiveInteraction.getValence());
					Interaction3 alternateInteraction = this.getCoupling().getInteraction(this.getExperience().getLabel() + result.getLabel());
					System.out.println("alternate interaction " + alternateInteraction.getLabel());
					this.setInteraction(alternateInteraction);
				}
				this.terminated = true;
			}			
		}
	}

	@Override
	public Interaction3 createOrReinforceCompositeInteraction(Interaction preInteraction, Interaction postInteraction) {
			
			String label = "(" + preInteraction.getLabel() + postInteraction.getLabel() + ")";
			Interaction3 interaction = (Interaction3)getInteraction(label);
			if (interaction == null){
				int valence = preInteraction.getValence() + postInteraction.getValence();	
				interaction = (Interaction3)this.createOrGet(label, valence); 
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

	public Experience createOrGetCompositeExperience(Interaction3 compositeInteraction) {
		Experience experience = this.createOrGetExperience(compositeInteraction.getLabel());
		experience.setInteraction(compositeInteraction);
		return experience;	
	}
	
	@Override
	protected List<Interaction2> getActivatedInteractions(Interaction interaction) {
		List<Interaction2> activatedInteractions = new ArrayList<Interaction2>();
	
		for (Interaction activatedInteraction : this.INTERACTIONS.values())
			if (this.superInteraction != null && this.superInteraction.equals(((Interaction3)activatedInteraction).getPreInteraction()) ||
				interaction != null && interaction.equals(((Interaction3)activatedInteraction).getPreInteraction()) ||
				((Interaction3)interaction).getPostInteraction() != null && ((Interaction3)interaction).getPostInteraction().equals(((Interaction3)activatedInteraction).getPreInteraction())){
				activatedInteractions.add((Interaction3)activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}

}
