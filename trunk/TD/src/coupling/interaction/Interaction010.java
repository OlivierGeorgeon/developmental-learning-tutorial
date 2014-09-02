package coupling.interaction;

import coupling.Experience;
import coupling.Result;

/**
 * An interaction1 is the association of an experience with a result.
 */
public class Interaction010 implements Interaction{
	
	private String label;
	protected Experience experience;
	protected Result result;
	
	public Interaction010(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
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
		return this.experience.getLabel() + this.result.getLabel();
	}

}
