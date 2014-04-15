package agent.decider;

import coupling.Coupling3;
import coupling.Coupling4;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction3;

public class Episode4 extends Episode3{
	
	private Experience primitiveExperience;
	private boolean terminated = false;
	
	private int step = 0;

	public Episode4(Coupling3 coupling, Experience experience){
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
				this.terminated = true;
			}			
		}
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
}
