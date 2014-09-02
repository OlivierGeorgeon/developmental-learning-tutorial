package coupling.interaction;

import coupling.Experience;
import coupling.Result;

/**
 * An interaction1 is the association of an experience with a result.
 */
public class Interaction1 implements Interaction{
	
	private String label;
	private Experience experience;
	private Result result;
	private int valence;
	
	public Interaction1(String label, int valence){
		this.label = label;
		this.valence = valence;
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public int getValence(){
		return this.valence;
	}

	public int compareTo(Interaction interaction){
		return new Integer(interaction.getValence()).compareTo(this.getValence());
	}

	public Experience getExperience() {
		return experience;
	}

	public void setExperience(Experience experience) {
		this.experience = experience;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String toString(){
		return this.experience.getLabel() + this.result.getLabel() + "," + this.getValence();
	}

}
