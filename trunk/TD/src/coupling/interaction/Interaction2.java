package coupling.interaction;

import java.util.ArrayList;
import java.util.List;

/**
 * An Interaction2 has a preInteraction and a postInteraction.
 * @author Olivier
 */
public class Interaction2 extends Interaction1{
	
	private Interaction2 preInteraction;
	private Interaction2 postInteraction;

	public Interaction2(String label, int valence){
		super(label, valence);
	}
	
	public Interaction2 getPreInteraction() {
		return preInteraction;
	}

	public void setPreInteraction(Interaction2 preInteraction) {
		this.preInteraction = preInteraction;
	}

	public Interaction2 getPostInteraction() {
		return postInteraction;
	}
	
	public void setPostInteraction(Interaction2 postInteraction) {
		this.postInteraction = postInteraction;
	}

	public List<Interaction2> getSeries(){
		List<Interaction2> series = new ArrayList<Interaction2>();
				
		if (this.getExperience().isPrimitive())	
			series.add(this);
		else{
			series.addAll(this.getPreInteraction().getSeries());
			series.addAll(this.getPostInteraction().getSeries());
		}		
		return series;
	}

	public String toString(){
		if (this.preInteraction != null)
			return this.preInteraction.getLabel() + this.postInteraction.getLabel() + "," + this.getValence();
		else
			return this.getExperience().getLabel() + this.getResult().getLabel() + "," + this.getValence();
	}
}
