package agent.decider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import coupling.Coupling4;
import coupling.Experience;
import coupling.interaction.Interaction3;

public class Episode4 extends Episode3{
	
	private Interaction3 episodeInteraction;
	private Episode4 contextEpisode;
	
	private boolean terminated = false;
	
	private int step = 0;

	public Episode4(Coupling4 coupling, Experience experience){
		super(coupling, experience);
	}

	
	@Override
	public List<Proposition> getPropositions(){
		List<Proposition> propositions = new ArrayList<Proposition>(); 
		for (Interaction3 activatedInteraction : getActivatedInteractions()){
//			if (!activatedInteraction.getPostInteraction().getExperience().isPrimitive()){
//				int proclivity = activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getPostInteraction().getValence();
//				if (proclivity >= 0){
//					Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getPreInteraction().getExperience(), proclivity);
//					int index = propositions.indexOf(proposition);
//					if (index < 0)
//						propositions.add(proposition);
//					else
//						propositions.get(index).addProclivity(proclivity);
//				}
//			}
//			else{
				Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
				int index = propositions.indexOf(proposition);
				if (index < 0)
					propositions.add(proposition);
				else
					propositions.get(index).addProclivity(activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
			//}
		}
		return propositions;
	}
	
	public void track(Interaction3 enactedInteraction){
		this.terminated = true;
	}
	
//	@Override
//	public Interaction3 store(Interaction3 enactedInteraction){
//		Interaction3 episodeInteraction = super.store(enactedInteraction);
//		
//		if (this.getContextEpisode() != null && this.getContextEpisode().getContextInteraction()!= null ){
//			this.getCoupling().createOrReinforceCompositeInteraction(this.getContextEpisode().getContextInteraction(), episodeInteraction);
//			this.getCoupling().createOrReinforceCompositeInteraction(this.getContextEpisode().getEpisodeInteraction(), enactedInteraction);	
//			
//			if (this.getContextEpisode().getContextEpisode() != null && this.getContextEpisode().getContextEpisode().getEpisodeInteraction() != null)
//			this.getCoupling().createOrReinforceCompositeInteraction(this.getContextEpisode().getContextEpisode().getEpisodeInteraction(), episodeInteraction);			
//		}
//		
//		this.episodeInteraction = episodeInteraction;
//		return episodeInteraction;
//	}

	protected void setContextEpisode(Episode4 contextEpisode) {
		this.contextEpisode = contextEpisode;
	}
	
	protected Episode4 getContextEpisode(){
		return this.contextEpisode;
	}
	
	protected Interaction3 getEpisodeInteraction(){
		return this.episodeInteraction;
	}

//	@Override
//	protected List<Interaction3> getActivatedInteractions() {
//		List<Interaction3> activatedInteractions = new ArrayList<Interaction3>();
//	
//		for (Interaction3 activatedInteraction : this.getCoupling().getInteractions())
//			if (this.episodeInteraction!= null && this.episodeInteraction.equals(activatedInteraction.getPreInteraction()) ||
//				this.getContextInteraction()!= null && this.getContextInteraction().equals(activatedInteraction.getPreInteraction())){
//				activatedInteractions.add(activatedInteraction);
//				System.out.println("activated " + activatedInteraction.toString());
//			}
//		return activatedInteractions;
//	}
	
	public void resetTerminated(){
		this.terminated = false;
	}
	
	public boolean isTerminated(){
		return this.terminated;
	}

	public Experience getPrimitiveExperience(){
		Experience experience = this.getExperience();
		
		if (!experience.isPrimitive()){
			if (!this.terminated){
				experience = experience.getInteraction().getPreInteraction().getExperience();
				System.out.println("pre");
			}
			else
				experience = experience.getInteraction().getPostInteraction().getExperience();
		}
			
		return experience;
	}
}
