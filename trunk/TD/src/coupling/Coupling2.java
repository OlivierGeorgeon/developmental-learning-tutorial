package coupling;

import java.util.ArrayList;
import java.util.List;

import coupling.interaction.Interaction1;

public class Coupling2 extends Coupling1 {

	public List<Interaction1> getActivatedInteractions(Interaction1 interaction) {
		List<Interaction1> activatedInteractions = new ArrayList<Interaction1>();
		for (Interaction1 activatedInteraction : getInteractions())
			if (interaction==activatedInteraction.getPreIntearction())
				activatedInteractions.add(activatedInteraction);
		return activatedInteractions;
	}
}
