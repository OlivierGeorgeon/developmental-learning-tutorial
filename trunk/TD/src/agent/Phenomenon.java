package agent;

import java.util.List;

import coupling.Experiment050;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction060;

/**
 * A type of phenomenon with which the agent interacts.
 */
public interface Phenomenon {
	
	public String getLabel();
	
	public Interaction060 getPersistentInteraction();

	public void addPreInteraction(Interaction060 interaction);

	public List<Interaction> getPreInteractions();

	public void addPostInteraction(Interaction060 interaction);

	public List<Interaction> getPostInteractions();
	
	public void setConsistent(boolean consistent);
	
	public boolean isConsistent();
	
	public boolean isAlreadyTried(Experiment050 experiment);
	
	public Experiment050 getPlayExperiment();
	
	public void trace();
}
