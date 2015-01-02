package coupling.interaction;

/**
 * An interaction060 is an Interaction040 that can return its length.
 * The length is used for tracing.
 */
public class Interaction060 extends Interaction040 {

	public Interaction060(String label){
		super(label);
	}
	
	public int getLength(){
		if (this.isPrimitive())
			return 1;
		else
			return ((Interaction060)this.getPreInteraction()).getLength() + ((Interaction060)this.getPostInteraction()).getLength(); 
	}
}
