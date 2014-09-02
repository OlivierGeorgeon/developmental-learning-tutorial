package coupling.interaction;

/**
 * An interaction1 is the association of an experience with a result.
 */
public class Interaction020 extends Interaction010{
	
	private int valence;
	
	public Interaction020(String label, int valence){
		super(label);
		this.valence = valence;
	}
	
	public int getValence(){
		return this.valence;
	}

//	public int compareTo(Interaction interaction){
//		return new Integer(interaction.getValence()).compareTo(this.getValence());
//	}

	public String toString(){
		return this.getLabel() + "," + this.getValence();
	}

}
