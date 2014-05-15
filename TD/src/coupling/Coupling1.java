package coupling;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;

import reactive.Environment;
import reactive.Environment1;
import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;

import agent.Agent;
import agent.Agent1;
import agent.decider.Decider;
import agent.decider.Decider1;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction1;

/**
 * A Coupling1 manages an Agent1 with an Environment1  
 * which interact through Experiences, Results, and primitive Interaction1s.
 * @author Olivier
 */
public class Coupling1 implements Existence3 {
	
	private Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();

	private Map<String ,Result> RESULTS = new HashMap<String ,Result>();

	private Map<String , Interaction> INTERACTIONS = new HashMap<String , Interaction>() ;
	
	private Decider decider;
	private Environment environment;

	public Coupling1(){
		initCoupling();
	}
	
	protected void initCoupling(){		
		this.decider = new Decider1(this);
		this.environment = new Environment1(this);

		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createOrGetPrimitiveInteraction(e1, r1, 1);
		createOrGetPrimitiveInteraction(e1, r2, -1);
		createOrGetPrimitiveInteraction(e2, r1, 1);
		createOrGetPrimitiveInteraction(e2, r2, -1);
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

	public Interaction getOtherInteraction(Interaction interaction) {
		Interaction otherInteraction = (Interaction)INTERACTIONS.values().toArray()[0];
		if (interaction != null)
			for (Interaction e : INTERACTIONS.values()){
				if (e.getExperience() != null && e.getExperience()!=interaction.getExperience()){
					otherInteraction =  e;
					break;
				}
			}		
		return otherInteraction;
	}

	public Interaction createOrGetPrimitiveInteraction(Experience experience, Result result, int valence) {
		Interaction interaction = createOrGet(experience.getLabel() + result.getLabel(), valence); 
		interaction.setExperience(experience);
		interaction.setResult(result);
		return interaction;
	}

	public Interaction getInteraction(String label) {
		return INTERACTIONS.get(label);
	}

	public Collection<Interaction> getInteractions(){
		return INTERACTIONS.values();
	}
	
	public Interaction createOrGet(String label, int valence) {
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, createNewInteraction(label, valence));			
		return INTERACTIONS.get(label);
	}
	
	protected Interaction1 createNewInteraction(String label, int valence){
		return new Interaction1(label, valence);
	}

	@Override
	public Intention decideIntention(Obtention obtention) {
		return this.decider.chooseIntention(obtention);
	}

	@Override
	public Obtention produceObtention(Intention intention) {
		Result result = this.environment.provideObtention(((Intention1)intention).getExperience());
		return new Obtention1(result);
	}
	
	protected void setDecider(Decider agent){
		this.decider = agent;
	}
	
	protected Decider getDecider(){
		return this.decider;
	}

	protected void setEnvironment(Environment environment){
		this.environment = environment;
	}
	
	protected Environment getEnvironment(){
		return this.environment;
	}
	
	protected Collection<Experience> getExperiences(){
		return this.EXPERIENCES.values();
	}
}
