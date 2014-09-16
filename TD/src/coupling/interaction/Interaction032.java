package coupling.interaction;

import java.util.ArrayList;
import java.util.List;

/**
 * An Interaction032 is an Interaction031 
 * that has a list of alternate interactions.
 */
public class Interaction032 extends Interaction031 {

	private List<Interaction> alternateInteractions = new ArrayList<Interaction>();
	
	public Interaction032(String label){
		super(label);
	}
	
	public void addAlternateInteraction(Interaction interaction){
		if (!this.alternateInteractions.contains(interaction))
			this.alternateInteractions.add(interaction);
	}
	
	public List<Interaction> getAletnerateInteractions(){
		return this.alternateInteractions;
	}

}
