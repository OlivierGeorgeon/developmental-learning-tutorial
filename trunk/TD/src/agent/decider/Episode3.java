package agent.decider;

import java.util.ArrayList;
import java.util.List;

import coupling.Coupling3;
import coupling.Experience;
import coupling.interaction.Interaction3;

public class Episode3 implements Episode{
	
	private Coupling3 coupling;
	
	private Experience experience;

	Episode3 contextEpisode;

	List<Interaction3> enactedInteractions = new ArrayList<Interaction3>();
	
	public Episode3(Coupling3 coupling){
		this.coupling = coupling;
		this.experience = this.coupling.createOrGetExperience(Coupling3.LABEL_E1);
	}
	
	public Episode createNext(){
		Episode3 nextEpisode = null;
        try {
        	nextEpisode =  (Episode3)super.clone();
        	this.contextEpisode = null;
        	nextEpisode.setContextEpisode(this);
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not allowed.");
        }		
		return nextEpisode;
	}
	
	protected Coupling3 getCoupling(){
		return this.coupling;
	}
	
	public List<Interaction3> getEnactedInteractions() {
		return enactedInteractions;
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}
	
	public void store(Interaction3 enactedInteraction){
		this.enactedInteractions = new ArrayList<Interaction3>();
		this.enactedInteractions.add(enactedInteraction);

		if (contextEpisode!= null )
			for (Interaction3 contextInteraction : contextEpisode.getEnactedInteractions())
				this.coupling.createOrReinforceCompositeInteraction(contextInteraction, enactedInteraction);
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
	
	private List<Interaction3> getActivatedInteractions() {
		List<Interaction3> activatedInteractions = new ArrayList<Interaction3>();
		for (Interaction3 activatedInteraction : this.coupling.getInteractions())
			if (contextEpisode.getEnactedInteractions().contains(activatedInteraction.getPreInteraction())){
				activatedInteractions.add(activatedInteraction);
				System.out.println("activated " + activatedInteraction.toString());
			}
		return activatedInteractions;
	}

	public void setContextEpisode(Episode3 contextEpisode) {
		this.contextEpisode = contextEpisode;
	}
	
	public Episode getContextEpisode(){
		return this.contextEpisode;
	}

	@Override
	public void setEnactedInteractions(List<Interaction3> enactedInteractions) {
		this.enactedInteractions = enactedInteractions;
	}
}
