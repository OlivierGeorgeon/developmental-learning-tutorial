package agent.decider;

import java.util.List;
import coupling.Experience;
import coupling.interaction.Interaction3;

public interface Episode extends Cloneable{

	public Experience getExperience();

	public void setExperience(Experience experience);
	
	public void store(Interaction3 enactedInteraction);
	
	public List<Proposition> getPropositions();
	
	public Episode getContextEpisode();

	public void setContextEpisode(Episode3 contextEpisode);
	
	public Episode createNext();
	
	public List<Interaction3> getEnactedInteractions();

	public void setEnactedInteractions(List<Interaction3> enactedInteractions);

}
