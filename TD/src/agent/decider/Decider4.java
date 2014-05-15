package agent.decider;

import Existence.Existence3;
import Existence.Existence4;
import agent.Agent;
import coupling.Coupling4;
import coupling.Experience;
import coupling.Intention;
import coupling.Obtention;
import coupling.Result;
import coupling.interaction.Interaction3;

/**
 * @author Olivier
 */
public class Decider4 implements Decider{
	
	private Existence4 existence;
	private Episode4 episode;
	
	public Decider4(Existence4 existence){
		this.existence = existence;
	}

	public Intention chooseIntention(Obtention obtention){

		if (result != null)
			this.episode.record(result);
		
		if (this.episode.isTerminated()){
			this.existence.store(episode);			
			this.episode = chooseEpisode(this.episode.getInteraction());
		}
		
		return this.episode.nextPrimitiveExperience();
	}

	public Episode4 chooseEpisode(Interaction3 interaction) {
		
		Experience experience = this.episode.propose(interaction);
		
		this.interaction_2 = this.interaction_1;
		this.interaction_1 = interaction;

		return new Episode4(this, experience);
	}


}
