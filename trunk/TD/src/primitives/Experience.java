package primitives;

import java.util.List;
import java.util.ArrayList;

public class Experience {
	
	private static List<Experience> EXPERIENCES = new ArrayList<Experience>();
	private static int index = 0;
	
	private String label;
	
	public static Experience create(String label){
		Experience experience = new Experience(label);
		EXPERIENCES.add(experience);
		return experience;
	}
	
//	public static Experience getNext(){
//		if (index < EXPERIENCES.size() - 1) index++;
//		else index = 0;
//		return EXPERIENCES.get(index);
//	}
//	
	public static Experience getOther(Experience experience){
		Experience otherExperience = null;
		for (Experience e : EXPERIENCES){
			if (e!=experience){
				otherExperience =  e;
				break;
			}
		}		
		return otherExperience;
	}
	
	private Experience(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}

}
