package coupling;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;

import coupling.interaction.Interaction;
import coupling.interaction.Interaction1;

public class Coupling10 implements Coupling {
	
	private Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();

	private Map<String ,Result> RESULTS = new HashMap<String ,Result>();

	private Map<String , Interaction1> INTERACTIONS = new HashMap<String , Interaction1>() ;

	public Coupling10(){
		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);

		Tracer<Element> tracer = new ConsoleTracer();
		Trace.init(tracer);
	}
	
	public Experience createOrGetExperience(String label) {
		if (!EXPERIENCES.containsKey(label))
			EXPERIENCES.put(label, new Experience(label));			
		return EXPERIENCES.get(label);
	}

	public Experience getOtherExperience(Experience experience) {
		Experience otherExperience = null;
		for (Experience e : EXPERIENCES.values()){
			if (e!=experience){
				otherExperience =  e;
				break;
			}
		}		
		return otherExperience;
	}

	public Result createOrGetResult(String label) {
		if (!RESULTS.containsKey(label))
			RESULTS.put(label, new Result(label));			
		return RESULTS.get(label);
	}

	public void createPrimitiveInteraction(Experience experience,
			Result result, int valence) {
		Interaction interaction = createOrGet(experience.getLabel() + result.getLabel(), valence); 
		interaction.setExperience(experience);
		interaction.setResult(result);
	}

	@Override
	public Interaction1 getInteraction(String label) {
		return INTERACTIONS.get(label);
	}

	public Collection<Interaction1> getInteractions(){
		return INTERACTIONS.values();
	}
	
	private Interaction createOrGet(String label, int valence) {
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, new Interaction1(label, valence));			
		return INTERACTIONS.get(label);
	}

	public Experience getFirstExperience() {
		return (Experience)EXPERIENCES.values().toArray()[0];
	}
	
	public Interaction predict(Experience experience){
		Interaction interaction = null;
		
		for (Interaction i : INTERACTIONS.values())
			if (i.getExperience().equals(experience))
				interaction = i;
		
		return interaction;
	}
}
