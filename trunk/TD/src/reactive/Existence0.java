package reactive;

import java.util.HashMap;
import java.util.Map;
import tracer.Trace;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction1;

/**
 * An Existence0 simulates a "flow of consciousness" made of a succession of Experiences and Results.   
 * The Existence0 is SELF-SATISFIED when the Result corresponds to the Result it expected, and FRUSTRATED otherwise.
 * Additionally, the Existence0 is BORED when it has been happy for too long, which causes it to try another Experience.  
 * An Existence0 is still a single entity rather than being split into an explicit Agent and Environment.
 * An Existence0 demonstrates a rudimentary decisional mechanism but no learning.  
 * 
 * Existence0 illustrates the primacy of existence as a "monist" entity that precedes the dualism agent/environment.
 * 
 * @author Olivier
 */
public class Existence0 implements Existence {
	
	protected final String LABEL_E1 = "e1"; 
	protected final String LABEL_E2 = "e2"; 
	protected final String LABEL_R1 = "r1";
	protected final String LABEL_R2 = "r2";

	protected Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();
	protected Map<String ,Result> RESULTS = new HashMap<String ,Result>();
	protected Map<String , Interaction> INTERACTIONS = new HashMap<String , Interaction>() ;

	protected final int BOREDOME_LEVEL = 5;
	protected int selfSatsfactionCounter = BOREDOME_LEVEL;
	protected Experience experience;
	protected Result result;
	protected Result expectedResult;
	
	public Existence0(){
		initExistence();
	}	
	
	protected void initExistence(){
		createOrGetExperience(LABEL_E1);
		createOrGetExperience(LABEL_E2);
	}

	@Override
	public String step() {
		
		this.experience = chooseExperience(result);
		this.result = giveResult(experience);
		
		return this.experience.getLabel() + this.result.getLabel();
	}

	protected Experience chooseExperience(Result result){

		if (this.expectedResult != null && this.expectedResult.equals(result))
			Trace.addEventElement("mood", "SELF-SATISFIED");
		else
			Trace.addEventElement("mood", "FRUSTRATED");
		
		if (this.experience != null && result != null)
			createPrimitiveInteraction(this.experience, result, 0);

		if (this.selfSatsfactionCounter >= BOREDOME_LEVEL){
			Trace.addEventElement("mood", "BORED");
			this.experience = getOtherExperience(this.experience);		
			this.selfSatsfactionCounter = 0;
		}
		
		this.selfSatsfactionCounter++;
		
		Interaction intendedInteraction = predict(this.experience);
		if (intendedInteraction != null)
			this.expectedResult = intendedInteraction.getResult();
		
		return this.experience;
	}
	
	public Result giveResult(Experience experience){
		if (experience.equals(createOrGetExperience(LABEL_E1)))
			return createOrGetResult(LABEL_R1);
		else
			return createOrGetResult(LABEL_R2);
	}
	
	protected Interaction createPrimitiveInteraction(Experience experience, Result result, int valence) {
		Interaction interaction = createOrGet(experience.getLabel() + result.getLabel(), valence); 
		interaction.setExperience(experience);
		interaction.setResult(result);
		return interaction;
	}

	protected Interaction createOrGet(String label, int valence) {
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, new Interaction1(label, valence));			
		return INTERACTIONS.get(label);
	}
	
	protected Interaction getInteraction(String label){
		return INTERACTIONS.get(label);
	}
	
	protected Interaction predict(Experience experience){
		Interaction interaction = null;
		
		for (Interaction i : INTERACTIONS.values())
			if (i.getExperience().equals(experience))
				interaction = i;
		
		return interaction;
	}

	protected Experience createOrGetExperience(String label) {
		if (!EXPERIENCES.containsKey(label))
			EXPERIENCES.put(label, new Experience(label));			
		return EXPERIENCES.get(label);
	}

	protected Experience getOtherExperience(Experience experience) {
		Experience otherExperience = null;
		for (Experience e : EXPERIENCES.values()){
			if (e!=experience){
				otherExperience =  e;
				break;
			}
		}		
		return otherExperience;
	}

	protected Result createOrGetResult(String label) {
		if (!RESULTS.containsKey(label))
			RESULTS.put(label, new Result(label));			
		return RESULTS.get(label);
	}	
}
