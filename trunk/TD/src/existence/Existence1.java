package existence;

import java.util.HashMap;
import java.util.Map;
import tracer.Trace;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction1;

/**
 * An Existence1 simulates a "stream of intelligence" made of a succession of Experiences and Results.   
 * The Existence1 is SELF-SATISFIED when the Result corresponds to the Result it expected, and FRUSTRATED otherwise.
 * Additionally, the Existence0 is BORED when it has been SELF-SATISFIED for too long, which causes it to try another Experience.  
 * An Existence1 is still a single entity rather than being split into an explicit Agent and Environment.
 */
public class Existence1 implements Existence {
	
	protected final String LABEL_E1 = "e1"; 
	protected final String LABEL_E2 = "e2"; 
	protected final String LABEL_R1 = "r1";
	protected final String LABEL_R2 = "r2";

	protected Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();
	protected Map<String ,Result> RESULTS = new HashMap<String ,Result>();
	protected Map<String , Interaction> INTERACTIONS = new HashMap<String , Interaction>() ;

	protected final int BOREDOME_LEVEL = 5;
	protected int selfSatisfactionCounter = BOREDOME_LEVEL;
	protected Experience experience;
	protected Result result;
	protected Result expectedResult;
	
	public Existence1(){
		initExistence();
	}	
	
	protected void initExistence(){
		createOrGetExperience(LABEL_E1);
		createOrGetExperience(LABEL_E2);
	}

	@Override
	public String step() {
		
		this.experience = chooseExperience(result);
		this.result = returnResult(experience);
		
		return this.experience.getLabel() + this.result.getLabel();
	}

	/**
	 * @param result: The result obtained in the previous cycle.
	 * @return The next experience.
	 */
	protected Experience chooseExperience(Result result){

		if (this.expectedResult != null && this.expectedResult.equals(result))
			Trace.addEventElement("mood", "SELF-SATISFIED");
		else
			Trace.addEventElement("mood", "FRUSTRATED");
		
		if (this.experience != null && result != null)
			createPrimitiveInteraction(this.experience, result);

		if (this.selfSatisfactionCounter >= BOREDOME_LEVEL){
			Trace.addEventElement("mood", "BORED");
			this.experience = getOtherExperience(this.experience);		
			this.selfSatisfactionCounter = 0;
		}
		
		this.selfSatisfactionCounter++;
		
		Interaction intendedInteraction = predict(this.experience);
		if (intendedInteraction != null)
			this.expectedResult = intendedInteraction.getResult();
		
		return this.experience;
	}
	
	/**
	 * @param experience: The current experience.
	 * @return The result of this experience.
	 */
	public Result returnResult(Experience experience){
		if (experience.equals(createOrGetExperience(LABEL_E1)))
			return createOrGetResult(LABEL_R1);
		else
			return createOrGetResult(LABEL_R2);
	}
	
	/**
	 * Create an interaction as a tuple <experience, result>.
	 * @param experience: The experience.
	 * @param result: The result.
	 * @return The created interaction
	 */
	protected Interaction createPrimitiveInteraction(Experience experience, Result result) {
		Interaction interaction = createOrGet(experience.getLabel() + result.getLabel()); 
		interaction.setExperience(experience);
		interaction.setResult(result);
		return interaction;
	}

	/**
	 * Records an interaction in memory.
	 * @param label: The label of this interaction.
	 * @return The interaction.
	 */
	protected Interaction createOrGet(String label) {
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, new Interaction1(label));			
		return INTERACTIONS.get(label);
	}
	
	/**
	 * Finds an interaction from its label
	 * @param label: The label of this interaction.
	 * @return The interaction.
	 */
	protected Interaction getInteraction(String label){
		return INTERACTIONS.get(label);
	}
	
	/**
	 * Finds an interaction from its experience
	 * @return The interaction.
	 */
	protected Interaction predict(Experience experience){
		Interaction interaction = null;
		
		for (Interaction i : INTERACTIONS.values())
			if (i.getExperience().equals(experience))
				interaction = i;
		
		return interaction;
	}

	/**
	 * Creates a new experience from its label and stores it in memory.
	 * @param label: The experience's label
	 * @return The experience.
	 */
	protected Experience createOrGetExperience(String label) {
		if (!EXPERIENCES.containsKey(label))
			EXPERIENCES.put(label, new Experience(label));			
		return EXPERIENCES.get(label);
	}

	/**
	 * Finds an experience different from that passed in parameter.
	 * @param experience: The experience that we don't want
	 * @return The other experience.
	 */
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

	/**
	 * Creates a new result from its label and stores it in memory.
	 * @param label: The result's label
	 * @return The result.
	 */
	protected Result createOrGetResult(String label) {
		if (!RESULTS.containsKey(label))
			RESULTS.put(label, new Result(label));			
		return RESULTS.get(label);
	}	
}
