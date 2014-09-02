package coupling.interaction;

import coupling.Experience4;

/**
 * An Interaction3 is an Interaction2 with a weight.
 * @author Olivier.
 */
public class Interaction3_ extends Interaction030 {
	
	private int weight = 0;

	public Interaction3_(String label, int valence){
		super(label, valence);
	}
	
	public Experience4 getExperience() {
		return (Experience4)super.getExperience(); 
	}

	public Interaction3_ getPreInteraction() {
		return (Interaction3_)super.getPreInteraction();
	}

	public Interaction3_ getPostInteraction() {
		return (Interaction3_)super.getPostInteraction();
	}
	
	public int getWeight() {
		return weight;
	}

	public void incrementWeight() {
		this.weight++;
	}
	
	public String toString(){
		return this.getLabel() + "," + this.getValence() + "," + this.weight;
	}	

}
