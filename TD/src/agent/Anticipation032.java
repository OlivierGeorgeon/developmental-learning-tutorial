package agent;

import coupling.interaction.Interaction032;

public class Anticipation032 extends Anticipation030 {

	int proclivity;

	public Anticipation032(Interaction032 interaction, int proclivity){
		super(interaction);
		this.proclivity = proclivity;
	}

	public int compareTo(Anticipation anticipation){
		return new Integer(((Anticipation032)anticipation).getProclivity()).compareTo(this.proclivity);
	}

	public boolean equals(Object otherProposition){
		return ((Anticipation032)otherProposition).getInteraction() == this.getInteraction();
	}
	
	public int getProclivity() {
		return proclivity;
	}

	public void addProclivity(int proclivity) {
		this.proclivity += proclivity;
	}
	
	@Override
	public String toString(){
		return this.getInteraction().getLabel() + " proclivity " + this.getProclivity();
	}
}
