package coupling;

import agent.decider.Episode;
import agent.decider.Episode4;

public class Coupling4 extends Coupling3 {

	@Override
	public Episode createEpisode(){
		return new Episode4(this);
	}	
}
