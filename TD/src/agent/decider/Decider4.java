package agent.decider;

import reactive.Existence3;
import reactive.Existence4;
import agent.Agent;
import coupling.Coupling4;
import coupling.Experience;
import coupling.Intention;
import coupling.Obtention;
import coupling.Obtention1;
import coupling.Result;
import coupling.interaction.Interaction3;

/**
 * @author Olivier
 */
public class Decider4 implements Decider{
	
	private Existence4 existence;
	private Episode4 episode;
	
	private Interaction3 interaction_1;
	private Interaction3 interaction_2;

	public Decider4(Existence4 existence){
		this.existence = existence;
	}

	public Intention chooseIntention(Obtention obtention){

		Result result = null; 
		if (obtention != null) result = ((Obtention1)obtention).getResult();

		if (result != null)
			this.episode.record(result);
		
		//if (this.episode.isTerminated()){
		//	this.existence.store(episode);			
		//	this.episode = chooseEpisode(this.episode.getInteraction());
		//}
		//return this.episode.nextPrimitiveExperience());
		
		Experience experience = null;
		return new Intention1(experience);
		
	}

//	public Episode4 chooseEpisode(Interaction3 interaction) {
//		
//		Experience experience = this.episode.propose(interaction);
//		
//		this.interaction_2 = this.interaction_1;
//		this.interaction_1 = interaction;
//
//		return new Episode4(this, experience);
//	}


}
