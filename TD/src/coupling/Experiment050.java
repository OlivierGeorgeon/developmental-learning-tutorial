package coupling;

import java.util.List;
import java.util.ArrayList;
import coupling.interaction.Interaction;

/**
 * An Experience050 is an Experience040 that has a list of enactedInteractions.
 * Enacted Interactions are interactions that may end up being enacted
 * instead of the intended interaction when performing this experience.
 */
public class Experiment050 extends Experiment040 {

	private List<Interaction> enactedInteractions = new ArrayList<Interaction>();
	
	public Experiment050(String label){
		super(label);
	}

	public void addEnactedInteraction(Interaction enactedInteraction){
		if (!this.enactedInteractions.contains(enactedInteraction))
			this.enactedInteractions.add(enactedInteraction);
	}
	
	public List<Interaction> getEnactedInteractions(){
		return this.enactedInteractions;
	}
}
