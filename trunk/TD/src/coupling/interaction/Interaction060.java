package coupling.interaction;

import existence.Existence060;

/**
 * An interaction060 is an Interaction040 that has a persistency value and that can return its length.
 * The length is used for tracing.
 */
public class Interaction060 extends Interaction040 {
	
	private int persistency = 0;

	public Interaction060(String label){
		super(label);
	}
	
	public void incPersistency(){
		if (this.persistency > Existence060.UNPERSISTENT)
			this.persistency++;
	}
	
	public void setPersistency(int peristency){
		this.persistency = peristency;
	}
	
	public int getPersistency(){
		return this.persistency;
	}
	
	public int getLength(){
		if (this.isPrimitive())
			return 1;
		else
			return ((Interaction060)this.getPreInteraction()).getLength() + ((Interaction060)this.getPostInteraction()).getLength(); 
	}
}
