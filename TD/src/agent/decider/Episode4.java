package agent.decider;

import java.util.ArrayList;
import java.util.List;
import coupling.Coupling4;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;
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

//	@Override
	public void record(Result result){	
		Interaction3 enactedPrimitiveInteraction = this.getCoupling().getInteraction(this.primitiveExperience.getLabel() + result.getLabel());
		if (this.getExperience().isPrimitive()){
			this.setInteraction(enactedPrimitiveInteraction);
			this.terminated = true;
		}
		else{
			if (step == 0){
				if (!enactedPrimitiveInteraction.equals(this.getExperience().getInteraction().getPreInteraction().getExperience())){
					this.getCoupling().createPrimitiveInteraction(this.getExperience(), result, enactedPrimitiveInteraction.getValence());
					Interaction3 alternateInteraction = this.getCoupling().getInteraction(this.getExperience().getLabel() + result.getLabel());
					//this.setInteraction(enactedPrimitiveInteraction);
					this.setInteraction(alternateInteraction);
					System.out.println("alternate interaction " + alternateInteraction.getLabel());
					this.terminated = true;
				}
				step++;
			}
			else{
				Interaction3 expectedInteraction = this.getExperience().getInteraction();
				this.setInteraction(this.getExperience().getInteraction());
				this.terminated = true;
			}			
		}
	}
	
	
//	@Override
//	public List<Proposition> getPropositions(){
//		List<Proposition> propositions = new ArrayList<Proposition>(); 
//		for (Interaction3 activatedInteraction : getActivatedInteractions()){
////			if (!activatedInteraction.getPostInteraction().getExperience().isPrimitive()){
////				int proclivity = activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getPostInteraction().getValence();
////				if (proclivity >= 0){
////					Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getPreInteraction().getExperience(), proclivity);
////					int index = propositions.indexOf(proposition);
////					if (index < 0)
////						propositions.add(proposition);
////					else
////						propositions.get(index).addProclivity(proclivity);
////				}
////			}
////			else{
//				Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
//				int index = propositions.indexOf(proposition);
//				if (index < 0)
//					propositions.add(proposition);
//				else
//					propositions.get(index).addProclivity(activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
//			//}
//		}
//		return propositions;
//	}
	
	public void setContextEpisode(Episode4 contextEpisode) {
		this.contextEpisode = contextEpisode;
	}
	
	public Episode4 getContextEpisode(){
		return this.contextEpisode;
	}
	
	@Override
	protected List<Interaction3> getActivatedInteractions() {
		List<Interaction3> activatedInteractions = new ArrayList<Interaction3>();
	
		for (Interaction3 activatedInteraction : this.getCoupling().getInteractions())
			if (this.superInteraction!= null && this.superInteraction.equals(activatedInteraction.getPreInteraction()) ||
				this.getInteraction()!= null && this.getInteraction().equals(activatedInteraction.getPreInteraction())){
				activatedInteractions.add(activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}
	
	public void resetTerminated(){
		this.terminated = false;
	}
	
	public boolean isTerminated(){
		return this.terminated;
	}

	public Experience getPrimitiveExperience(){
		Experience experience = this.getExperience();
		
		if (!experience.isPrimitive()){
			if (this.step == 0){
				experience = experience.getInteraction().getPreInteraction().getExperience();
				System.out.println("pre");
			}
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
}
