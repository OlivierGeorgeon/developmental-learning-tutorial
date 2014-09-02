package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import agent.decider.Anticipation;
import agent.decider.Anticipation031;
import coupling.Experience;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction030;
import coupling.interaction.Interaction031;

public class Existence031 extends Existence030 {

	@Override
	public List<Anticipation> computeAnticipations(Interaction030 enactedInteraction){
		List<Anticipation> anticipations = this.getDefaultPropositions(); 
		
		if (enactedInteraction != null){
			for (Interaction activatedInteraction : getActivatedInteractions(enactedInteraction)){
				Anticipation031 proposition = new Anticipation031(((Interaction031)activatedInteraction).getPostInteraction().getExperience(), ((Interaction031)activatedInteraction).getWeight() * ((Interaction031)activatedInteraction).getPostInteraction().getValence());
				int index = anticipations.indexOf(proposition);
				if (index < 0)
					anticipations.add(proposition);
				else
					((Anticipation031)anticipations.get(index)).addProclivity(((Interaction031)activatedInteraction).getWeight() * ((Interaction031)activatedInteraction).getPostInteraction().getValence());
			}
		}
		return anticipations;
	}

	protected List<Anticipation> getDefaultPropositions(){
		List<Anticipation> anticipations = new ArrayList<Anticipation>();
		for (Experience experience : this.EXPERIENCES.values()){
			Anticipation031 anticipation = new Anticipation031(experience, 0);
			anticipations.add(anticipation);
		}
		return anticipations;
	}
	

	@Override
	public Experience chooseExperience(List<Anticipation> anticipations){
		// The list of anticipations is never empty because all experiences are proposed by default with a proclivity of 0
		Collections.sort(anticipations);
		for (Anticipation anticipation : anticipations)
			System.out.println("propose " + anticipation.toString());
		
		Anticipation031 selectedAnticipation = (Anticipation031)anticipations.get(0);
		return selectedAnticipation.getExperience();
	}

}
