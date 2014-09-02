package coupling.interaction;

/**
 * An interaction1 is the association of an experience with a result.
 */
public class Interaction2 extends Interaction1{
	
	private int valence;
	
	public Interaction2(String label, int valence){
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
		return this.experience.getLabel() + this.result.getLabel() + "," + this.getValence();
	}

}
