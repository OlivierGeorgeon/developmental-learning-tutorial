package agent.decider;

import java.util.ArrayList;
import java.util.List;

import coupling.Coupling3;
import coupling.Experience;
import coupling.interaction.Interaction3;

public class Episode3 implements Episode{
	
	private Coupling3 coupling;
	
	private Experience experience;

	private Interaction3 contextInteraction;

	private Interaction3 enactedInteraction;
	
	public Episode3(Coupling3 coupling){
		this.coupling = coupling;
		this.experience = this.coupling.createOrGetExperience(Coupling3.LABEL_E1);
	}
	
	public Episode createNext(){
		Episode3 nextEpisode = null;
        
		try { nextEpisode =  (Episode3)super.clone();
        } catch (CloneNotSupportedException e) { System.out.println("Cloning not allowed."); }		
       	
        nextEpisode.contextInteraction = this.enactedInteraction;
        return nextEpisode;
	}
	
	public List<Proposition> getPropositions(){
		List<Proposition> propositions = new ArrayList<Proposition>(); 
		for (Interaction3 activatedInteraction : getActivatedInteractions()){
			Proposition proposition = new Proposition(activatedInteraction.getPostInteraction().getExperience(), activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
			int index = propositions.indexOf(proposition);
			if (index < 0)
				propositions.add(proposition);
			else
				propositions.get(index).addProclivity(activatedInteraction.getWeight() * activatedInteraction.getPostInteraction().getValence());
		}
		return propositions;
	}
	
	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	
	public Experience getExperience() {
		return experience;
	}

	public Interaction3 store(Interaction3 enactedInteraction){
		this.enactedInteraction = enactedInteraction;
		Interaction3 episodeInteraction = null;
		if (this.contextInteraction!= null)
			episodeInteraction = this.coupling.createOrReinforceCompositeInteraction(this.contextInteraction, enactedInteraction);
		return episodeInteraction;
	}
	
	protected Coupling3 getCoupling(){
		return this.coupling;
	}
	
//	protected Interaction3 getEnactedInteraction() {
//		return this.enactedInteraction;
//	}

//	protected void setEnactedInteractions(List<Interaction3> enactedInteractions) {
//		this.enactedInteractions = enactedInteractions;
//	}
//
	protected Interaction3 getContextInteraction() {
		return this.contextInteraction;
	}

	protected List<Interaction3> getActivatedInteractions() {
		List<Interaction3> activatedInteractions = new ArrayList<Interaction3>();
		for (Interaction3 activatedInteraction : this.coupling.getInteractions())
			if (this.contextInteraction != null && this.contextInteraction.equals(activatedInteraction.getPreInteraction())){
				activatedInteractions.add(activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}
}
