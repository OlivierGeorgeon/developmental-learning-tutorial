package environment;

import coupling.interaction.Interaction;
import existence.Existence050;

public class Environment050 implements Environment {

	private Existence050 existence;

	public Environment050(Existence050  existence){
		this.existence = existence;
	}

	protected Existence050 getExistence(){
		return this.existence;
	}
	
	@Override
	public Interaction enact(Interaction intendedInteraction) {
		return intendedInteraction;
	}
}
