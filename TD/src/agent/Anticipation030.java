package agent;

import coupling.interaction.Interaction030;

/**
 * An Anticipation030 is created for each proposed primitive interaction.
 * An Anticipation030 is greater than another if its interaction has a greater valence than the other's.
 */
public class Anticipation030 implements Anticipation {
	
	Interaction030 interaction;
	
	public Anticipation030(Interaction030 interaction){
		this.interaction = interaction;
	}
	
	public Interaction030 getInteraction(){
		return this.interaction;
	}

	@Override
	public int compareTo(Anticipation anticipation) {
		return ((Integer)((Anticipation030)anticipation).getInteraction().getValence()).compareTo(this.interaction.getValence());
	}

}
