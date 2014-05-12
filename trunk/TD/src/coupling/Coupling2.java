package coupling;

import java.util.ArrayList;
import java.util.List;

import Environments.Environment2;
import agent.Agent2;
import agent.decider.Episode2;

import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;

/**
 * A Coupling2 
 * - can learn composite interactions (Interaction2)
 * - Can instantiate an Episode2 .
 * @author Olivier
 */
public class Coupling2 extends Coupling1 {
	
	protected void initCoupling(){
		this.setAgent(new Agent2(this));
		this.setEnvironment(new Environment2(this));

		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createOrGetPrimitiveInteraction(e1, r1, -1);
		createOrGetPrimitiveInteraction(e1, r2, 1);
		createOrGetPrimitiveInteraction(e2, r1, -1);
		createOrGetPrimitiveInteraction(e2, r2, 1);
	}
	
	public Episode2 createEpisode(Experience experience) {
		return new Episode2(this, experience);
	}

	public void createCompositeInteraction(
		Interaction2 preInteraction, Interaction2 postInteraction) {
		int valence = preInteraction.getValence() + postInteraction.getValence();
		Interaction2 interaction = (Interaction2)createOrGet(preInteraction.getLabel() + postInteraction.getLabel(), valence); 
		interaction.setPreInteraction(preInteraction);
		interaction.setPostInteraction(postInteraction);
		System.out.println("learn " + interaction.toString());
	}

	public Experience propose(Episode2 episode){
		Experience experience = this.getOtherExperience(null);
		for (Interaction2 activatedInteraction : this.getActivatedInteractions(episode))
			if (activatedInteraction.getPostInteraction().getValence() >= 0){
				experience = activatedInteraction.getPostInteraction().getExperience();
				System.out.println("propose " + experience.getLabel());
			}
			else{
				experience = this.getOtherExperience(activatedInteraction.getPostInteraction().getExperience());						
			}
		return experience;
	}
	
	public List<Interaction2> getActivatedInteractions(Interaction2 interaction) {
		List<Interaction2> activatedInteractions = new ArrayList<Interaction2>();
		for (Interaction activatedInteraction : getInteractions())
			if (interaction == ((Interaction2)activatedInteraction).getPreInteraction())
				activatedInteractions.add((Interaction2)activatedInteraction);
		return activatedInteractions;
	}
	
	protected List<Interaction2> getActivatedInteractions(Episode2 episode) {
		List<Interaction2> activatedInteractions = new ArrayList<Interaction2>();
		if (episode.getInteraction() != null)
			for (Interaction activatedInteraction : this.getInteractions())
				if (episode.getInteraction() == ((Interaction2)activatedInteraction).getPreInteraction())
					activatedInteractions.add(((Interaction2)activatedInteraction));
		return activatedInteractions;
	}
}
