package agent.decider;

import java.util.ArrayList;
import java.util.List;

import coupling.Coupling4;
import coupling.interaction.Interaction3;

public class Episode4 extends Episode3{
	
	private Interaction3 completedInteraction;
	private Episode4 contextEpisode;

	public Episode4(Coupling4 coupling){
		super(coupling);
	}

	@Override
	public Episode createNext(){
		Episode4 nextEpisode = (Episode4)super.createNext();
    	this.contextEpisode = null;
    	nextEpisode.setContextEpisode(this);
		return nextEpisode;
	}
	
	@Override
	public List<Proposition> getPropositions(){
		List<Proposition> propositions = new ArrayList<Proposition>(); 
		for (Interaction3 activatedInteraction : getActivatedInteractions()){
			if (activatedInteraction.getPostInteraction().getExperience() == null){
				int proclivity = activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getPostInteraction().getValence();
				if (proclivity >= 0){
					Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getPreInteraction().getExperience(), proclivity);
					int index = propositions.indexOf(proposition);
					if (index < 0)
						propositions.add(proposition);
					else
						propositions.get(index).addProclivity(proclivity);
				}
			}
			else{
				Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
				int index = propositions.indexOf(proposition);
				if (index < 0)
					propositions.add(proposition);
				else
					propositions.get(index).addProclivity(activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
			}
		}
		return propositions;
	}
	
	@Override
	public Interaction3 store(Interaction3 enactedInteraction){
		Interaction3 episodeInteraction = super.store(enactedInteraction);
		
		if (this.getContextEpisode() != null && this.getContextEpisode().getContextInteraction()!= null ){
			this.getCoupling().createOrReinforceCompositeInteraction(this.getContextEpisode().getContextInteraction(), episodeInteraction);
			this.getCoupling().createOrReinforceCompositeInteraction(this.getContextEpisode().getCompletedInteraction(), enactedInteraction);			
		}
		
		this.completedInteraction = episodeInteraction;
		return episodeInteraction;
	}

	protected void setContextEpisode(Episode4 contextEpisode) {
		this.contextEpisode = contextEpisode;
	}
	
	protected Episode4 getContextEpisode(){
		return this.contextEpisode;
	}
	
	protected Interaction3 getCompletedInteraction(){
		return this.completedInteraction;
	}

	@Override
	protected List<Interaction3> getActivatedInteractions() {
		List<Interaction3> activatedInteractions = new ArrayList<Interaction3>();
	
		for (Interaction3 activatedInteraction : this.getCoupling().getInteractions())
			if (this.completedInteraction!= null && this.completedInteraction.equals(activatedInteraction.getPreInteraction()) ||
				this.getContextInteraction()!= null && this.getContextInteraction().equals(activatedInteraction.getPreInteraction())){
				activatedInteractions.add(activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}
}
