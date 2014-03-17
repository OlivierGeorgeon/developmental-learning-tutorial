package coupling;

import coupling.Experience;
import coupling.Result;

public class Interaction {
	
	private String label;
	private int value;
	
	private Experience experience;
	private Result result;
	private Interaction preInteraction;
	private Interaction postInteraction;
		
	Interaction(String label, int value){
		this.label = label;
		this.value = value;
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public int getValue(){
		return this.value;
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

	public Interaction getPreIntearction() {
		return preInteraction;
	}

	public void setPreInteraction(Interaction preInteraction) {
		this.preInteraction = preInteraction;
	}

	public Interaction getPostInteraction() {
		return postInteraction;
	}
	
	public String toString(){
		return this.experience.getLabel() + "," + this.result.getLabel() + "," + this.value;
	}

	public void setPostInteraction(Interaction postInteraction) {
		this.postInteraction = postInteraction;
	}

}
