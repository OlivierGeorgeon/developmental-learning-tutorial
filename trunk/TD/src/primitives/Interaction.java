package primitives;

import java.util.HashMap;
import java.util.Map;

public class Interaction {
	private static Map<String , Interaction> INTERACTIONS = new HashMap<String , Interaction>() ;
	
	private String label;
	private int value;
	
	public static Interaction create(String label, int value){
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, new Interaction(label, value));			
		return INTERACTIONS.get(label);
	}
	
	public static Interaction get(String label){
		return INTERACTIONS.get(label);
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

}
