package coupling.interaction;

/**
 * An Interaction031 is an Interaction030 with a weight.
 */
public class Interaction031 extends Interaction030 {
	
	private int weight = 0;

	public Interaction031(String label){
		super(label);
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
		return this.weight;
	}

	public void incrementWeight() {
		this.weight++;
	}
	
	@Override
	public String toString(){
		return this.getLabel() + " valence " + this.getValence() + " weight " + this.weight;
	}	

}
