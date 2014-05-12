package Existence;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction1;

public class Existence0 implements Existence {
	
	public static final String LABEL_E1 = "e1"; 
	public static final String LABEL_E2 = "e2"; 
	public static final String LABEL_R1 = "r1";
	public static final String LABEL_R2 = "r2";

	private Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();
	private Map<String ,Result> RESULTS = new HashMap<String ,Result>();
	private Map<String , Interaction> INTERACTIONS = new HashMap<String , Interaction>() ;

	public static int BOREDOME_LEVEL = 5;
	
	private int state = BOREDOME_LEVEL;
	private Experience experience;
	private Result result;
	private Result expectedResult;
	
	public Existence0(){
		initExistence();
	}	
	
	protected void initExistence(){
		createOrGetExperience(LABEL_E1);
		createOrGetExperience(LABEL_E2);
		Tracer<Element> tracer = new ConsoleTracer();
		Trace.init(tracer);		
	}

	@Override
	public String step() {
		
		this.experience = chooseExperience(result);
		this.result = giveResult(experience);
		
		return this.experience.getLabel() + this.result.getLabel();
	}

	protected Experience chooseExperience(Result result){

		if (this.expectedResult != null && this.expectedResult.equals(result))
			Trace.addEventElement("mood", "HAPPY");
		else
			Trace.addEventElement("mood", "SAD");
		
		if (this.experience != null && result != null)
			createPrimitiveInteraction(this.experience, result, 0);

		if (this.state >= BOREDOME_LEVEL){
			Trace.addEventElement("mood", "BORED");
			this.experience = getOtherExperience(this.experience);		
			this.state = 0;
		}
		
		this.state++;
		
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
	
	protected Experience getExperience(){
		return this.experience;
	}
	
	protected void setExperience(Experience experience){
		this.experience = experience;
	}

	protected Result getResult(){
		return this.result;
	}
	
	protected void setResult(Result result){
		this.result = result;
	}
}
