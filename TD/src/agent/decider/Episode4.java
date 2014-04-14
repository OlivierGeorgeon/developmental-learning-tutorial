package agent.decider;

import java.util.ArrayList;
import java.util.List;
import coupling.Coupling4;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction3;

public class Episode4 extends Episode3{
	
	private Episode4 contextEpisode;
	private Interaction3 superInteraction;
	private Experience primitiveExperience;
	private boolean terminated = false;
	
	private int step = 0;

	public Episode4(Coupling4 coupling, Experience experience){
		super(coupling, experience);
	}

	public void record(Result result){	
		Interaction3 enactedPrimitiveInteraction = this.getCoupling().getInteraction(this.primitiveExperience.getLabel() + result.getLabel());
		if (this.getExperience().isPrimitive()){
			this.setInteraction(enactedPrimitiveInteraction);
			this.terminated = true;
		}
		else{
			if (step == 0){
				if (!enactedPrimitiveInteraction.equals(this.getExperience().getInteraction().getPreInteraction())){
					this.getCoupling().createPrimitiveInteraction(this.getExperience(), result, enactedPrimitiveInteraction.getValence());
					Interaction3 alternateInteraction = this.getCoupling().getInteraction(this.getExperience().getLabel() + result.getLabel());
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
					this.getCoupling().createPrimitiveInteraction(this.getExperience(), result, this.getExperience().getInteraction().getPreInteraction().getValence() + enactedPrimitiveInteraction.getValence());
					Interaction3 alternateInteraction = this.getCoupling().getInteraction(this.getExperience().getLabel() + result.getLabel());
					System.out.println("alternate interaction " + alternateInteraction.getLabel());
					this.setInteraction(alternateInteraction);
				}
				//Interaction3 enactedInteraction = this.getCoupling().createOrReinforceCompositeInteraction(this.getExperience().getInteraction().getPreInteraction(), enactedPrimitiveInteraction);
				//this.setInteraction(enactedInteraction);
				this.terminated = true;
			}			
		}
	}
	
	public void setContextEpisode(Episode4 contextEpisode) {
		this.contextEpisode = contextEpisode;
	}
	
	public Episode4 getContextEpisode(){
		return this.contextEpisode;
	}
	
	public boolean isTerminated(){
		return this.terminated;
	}

	public Experience getPrimitiveExperience(){
		Experience experience = this.getExperience();
		
		if (!experience.isPrimitive()){
			if (this.step == 0)
				experience = experience.getInteraction().getPreInteraction().getExperience();
			else
				experience = experience.getInteraction().getPostInteraction().getExperience();
		}
		
		this.primitiveExperience = experience;
			
		return experience;
	}


	public Interaction3 getSuperInteraction() {
		return superInteraction;
	}

	public void setSuperInteraction(Interaction3 superInteraction) {
		this.superInteraction = superInteraction;
	}

	@Override
	protected List<Interaction3> getActivatedInteractions() {
		List<Interaction3> activatedInteractions = new ArrayList<Interaction3>();
	
		for (Interaction3 activatedInteraction : this.getCoupling().getInteractions())
			if (this.superInteraction!= null && this.superInteraction.equals(activatedInteraction.getPreInteraction()) ||
				this.getInteraction()!= null && this.getInteraction().equals(activatedInteraction.getPreInteraction()) ||
				this.getInteraction().getPostInteraction() != null && this.getInteraction().getPostInteraction().equals(activatedInteraction.getPreInteraction())){
				activatedInteractions.add(activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}
}
