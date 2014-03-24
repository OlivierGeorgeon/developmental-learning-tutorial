package coupling;

import java.util.ArrayList;
import java.util.List;

import coupling.interaction.Interaction;

public class Coupling2 extends Coupling1 {

	public List<Interaction> getActivatedInteractions(Interaction interaction) {
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		for (Interaction activatedInteraction : getInteractions())
			if (interaction==activatedInteraction.getPreIntearction())
				activatedInteractions.add(activatedInteraction);
		return activatedInteractions;
	}
}
