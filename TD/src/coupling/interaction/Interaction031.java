package coupling.interaction;

/**
 * An Interaction031 is an Interaction030 with a weight.
 */
public class Interaction031 extends Interaction030 {
	
	private int weight = 0;

	public Interaction031(String label, int valence){
		super(label, valence);
	}
	
	@Override
	public Interaction031 getPreInteraction() {
		return (Interaction031)super.getPreInteraction();
	}

	@Override
	public Interaction031 getPostInteraction() {
		return (Interaction031)super.getPostInteraction();
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
