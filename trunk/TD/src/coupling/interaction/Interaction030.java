package coupling.interaction;

/**
 * An Interaction030 is an Interaction020 that can be primitive or composite
 * A composite interaction has a preInteraction and a postInteraction.
 */
public class Interaction030 extends Interaction020{
	
	private Interaction030 preInteraction;
	private Interaction030 postInteraction;

	public Interaction030(String label){
		super(label);
	}
	
	public Interaction030 getPreInteraction() {
		return preInteraction;
	}

	public void setPreInteraction(Interaction030 preInteraction) {
		this.preInteraction = preInteraction;
	}

	public Interaction030 getPostInteraction() {
		return (Interaction030)postInteraction;
	}
	
	public void setPostInteraction(Interaction030 postInteraction) {
		this.postInteraction = postInteraction;
	}
	
	public boolean isPrimitive(){
		return this.getPreInteraction() == null;
	}
}
