package coupling;

import java.util.ArrayList;
import java.util.List;

public class Coupling3 extends Coupling2 {

	public List<Interaction> getActivatedInteractions(List<Interaction> contextInteractions) {
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		for (Interaction activatedInteraction : getInteractions())
			if (contextInteractions.contains(activatedInteraction.getPreIntearction()))
				activatedInteractions.add(activatedInteraction);
		return activatedInteractions;
	}
}
