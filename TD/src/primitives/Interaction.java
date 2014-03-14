package primitives;

import java.util.HashMap;
import java.util.Map;

public class Interaction {
	private static Map<String , Interaction> INTERACTIONS = new HashMap<String , Interaction>() ;
	
	private Experience experience;
	private Result result;
	private int value;
	
	public static Interaction create(Experience experience, Result result, int value){
		String key = experience.getLabel() + result.getLabel();
		if (!INTERACTIONS.containsKey(key))
			INTERACTIONS.put(key, new Interaction(experience, result, value));			
		return INTERACTIONS.get(key);
	}
	
	public static Interaction get(Experience experience, Result result){
		return INTERACTIONS.get(experience.getLabel() + result.getLabel());
	}
	
	private Interaction(Experience experience, Result result, int value){
		this.experience = experience;
		this.result = result;
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}

}
