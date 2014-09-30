package coupling.interaction;

import coupling.Experiment;
import coupling.Result;

/**
 * An interaction010 is the association of an experience with a result.
 */
public class Interaction010 implements Interaction{
	
	private String label;
	protected Experiment experience;
	protected Result result;
	
	public Interaction010(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public Experiment getExperience() {
		return experience;
	}

	public void setExperience(Experiment experience) {
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
