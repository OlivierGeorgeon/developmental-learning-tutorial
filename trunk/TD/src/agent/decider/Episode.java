package agent.decider;

import java.util.List;
import coupling.Experience;
import coupling.interaction.Interaction3;

public interface Episode extends Cloneable{

	public Episode createNext();

	//public void setContextInteractions(List<Interaction3> contextInteractions);

	public List<Proposition> getPropositions();
	
	public void setExperience(Experience experience);
	
	public Experience getExperience();

	public Interaction3 store(Interaction3 enactedInteraction);
}
