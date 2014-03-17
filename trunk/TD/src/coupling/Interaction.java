package coupling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import coupling.Experience;
import coupling.Result;

public class Interaction {
	private static Map<String , Interaction> INTERACTIONS = new HashMap<String , Interaction>() ;
	
	private String label;
	private int value;
	
	private Experience experience;
	private Result result;
	private Interaction preInteraction;
	private Interaction postInteraction;
	
	public static Interaction createPrimitiveInteraction(Experience experience, Result result, int value){
		Interaction interaction = create(experience.getLabel() + result.getLabel(), value); 
		interaction.setExperience(experience);
		interaction.setResult(result);
		return interaction;
	}
	
	public static Interaction createOrGetCompositeInteraction(Interaction preInteraction, Interaction postInteraction){
		int value = preInteraction.getValue() + postInteraction.getValue();
		Interaction interaction = create(preInteraction.getLabel() + postInteraction.getLabel(), value); 
		interaction.setPreInteraction(preInteraction);
		interaction.setPostInteraction(postInteraction);
		return interaction;
	}
	
	public static Interaction create(String label, int value){
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, new Interaction(label, value));			
		return INTERACTIONS.get(label);
	}
	
	public static Interaction get(String label){
		return INTERACTIONS.get(label);
	}
	
	public static List<Interaction> getActivatedInteractions(Interaction interaction){
		List<Interaction> activatedInteractions = new ArrayList<Interaction>();
		for (Interaction activatedInteraction : INTERACTIONS.values())
			if (interaction==activatedInteraction.getPreIntearction())
				activatedInteractions.add(activatedInteraction);
		return activatedInteractions;
	}
	
	private Interaction(String label, int value){
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

	public void setPostInteraction(Interaction postInteraction) {
		this.postInteraction = postInteraction;
	}

}
