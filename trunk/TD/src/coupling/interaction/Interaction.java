package coupling.interaction;

import coupling.Experience;
import coupling.Result;

public class Interaction {
	
	private String label;
	private int valence;
	private int weight = 0;
	
	private Experience experience;
	private Result result;
	private Interaction preInteraction;
	private Interaction postInteraction;
		
	public Interaction(String label, int valence){
		this.label = label;
		this.valence = valence;
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public int getValence(){
		return this.valence;
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
		if (this.preInteraction != null)
			return this.preInteraction.getLabel() + "-" + this.postInteraction.getLabel() + "," + this.valence + "," + this.weight;
		else
			return this.experience.getLabel() + "," + this.result.getLabel() + "," + this.valence;
	}

	public void setPostInteraction(Interaction postInteraction) {
		this.postInteraction = postInteraction;
	}

	public int getWeight() {
		return weight;
	}

	public void incrementWeight() {
		this.weight++;
	}

}
