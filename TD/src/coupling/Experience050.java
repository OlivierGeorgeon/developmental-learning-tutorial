package coupling;

import java.util.List;
import java.util.ArrayList;
import coupling.interaction.Interaction;

/**
 * An Experience050 is an Experience040 that have a list of enactedInteractions.
 * Interactions in the list of enactedInteractions are interaction that may end up being enacted
 * if the intendedInteraction fails.
 */
public class Experience050 extends Experience040 {

	private List<Interaction> enactedInteractions = new ArrayList<Interaction>();
	
	public Experience050(String label){
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
