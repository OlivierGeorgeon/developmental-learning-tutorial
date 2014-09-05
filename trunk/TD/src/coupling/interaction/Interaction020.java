package coupling.interaction;

/**
 * An Interaction020 is an Interaction010 with a valence. 
 */
public class Interaction020 extends Interaction010{
	
	private int valence;
	
	public Interaction020(String label){
		super(label);
	}

	public void setValence(int valence){
		this.valence = valence;
	}
	
	public int getValence(){
		return this.valence;
	}

	@Override
	public String toString(){
		return this.getLabel() + "," + this.getValence();
	}

}
