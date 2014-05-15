package reactive;

import java.util.HashMap;
import java.util.Map;

import Environments.Environment;
import Environments.Environment1;
import agent.decider.Decider;
import agent.decider.Decider1;
import coupling.Experience;
import coupling.Obtention;
import coupling.Intention;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction1;

/**
 * Existence1 is a re-implementation of Existence0 in which the decision has been moved to the class Decider and the result to the class Environment.
 * Compared to an Existence0, an Esitence1's "flow of consciousness" is generalized as a succession of Intentions and Obtentions.
 * The Decider obtains the previous Obtention and chooses the next Intention through its method decideIntention(Obtention).
 * The Environment provides the next Obtention through its method produceObtention(Intention). 
 * 
 * Existence1 illustrates the fact the Decider's input (the Obtention) is a consequence of its previous output (the Intention) 
 * rather than the other way around as it is the case in traditional agent modeling.
 * 
 * @author Olivier
 */
public class Existence1 implements Existence {

	public final String LABEL_E1 = "e1"; 
	public final String LABEL_E2 = "e2"; 
	public final String LABEL_R1 = "r1";
	public final String LABEL_R2 = "r2";

	protected Map<String,Experience> EXPERIENCES = new HashMap<String,Experience>();
	protected Map<String,Result> RESULTS = new HashMap<String,Result>();
	protected Map<String,Interaction> INTERACTIONS = new HashMap<String,Interaction>() ;

	protected Decider decider;
	protected Environment environment;
	protected Obtention obtention;
	
	public Existence1(){
		initExistence();
	}	
	
	protected void initExistence(){
		this.decider = new Decider1(this);
		this.environment = new Environment1(this);
		
		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createOrGetPrimitiveInteraction(e1, r1, 1); // Change the valence of interactions to change the agent's motivation 
		createOrGetPrimitiveInteraction(e1, r2, -1);
		createOrGetPrimitiveInteraction(e2, r1, 1);
		createOrGetPrimitiveInteraction(e2, r2, -1);		
	}

	@Override
	public String step() {
		
		Intention intention = this.decider.chooseIntention(this.obtention);
		this.obtention = this.environment.provideObtention(intention);
				
		return print(intention, this.obtention);
	}
	
	protected String print(Intention intention, Obtention obtention){
		return this.getInteraction(intention.getLabel() + obtention.getLabel()).toString();
	}
	
	public Interaction createOrGetPrimitiveInteraction(Experience experience, Result result, int valence) {
		Interaction interaction = createOrGet(experience.getLabel() + result.getLabel(), valence); 
		interaction.setExperience(experience);
		interaction.setResult(result);
		return interaction;
	}

	protected Interaction createOrGet(String label, int valence) {
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, createNewInteraction(label, valence));			
		return INTERACTIONS.get(label);
	}
	
	protected Interaction1 createNewInteraction(String label, int valence){
		return new Interaction1(label, valence);
	}

	public Interaction getInteraction(String label){
		return INTERACTIONS.get(label);
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

	protected Interaction predict(Experience experience){
		Interaction interaction = null;
		
		for (Interaction i : INTERACTIONS.values())
			if (i.getExperience().equals(experience))
				interaction = i;
		
		return interaction;
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
}
