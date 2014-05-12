package coupling.interaction;

/**
 * An Interaction3 has a weight.
 * @author Olivier
 */
public class Interaction3 extends Interaction2 {
	
	private int weight = 0;

	public Interaction3(String label, int valence){
		super(label, valence);
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
