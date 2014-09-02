package existence;

import java.util.HashMap;
import java.util.Map;

import reactive.Environment;
import reactive.Environment1;
import tracer.Trace;

import agent.decider.Decider;
import agent.decider.Decider021;
import coupling.Experience;
import coupling.Obtention;
import coupling.Intention;
import coupling.Result;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction020;

/**
 * Existence021 is a re-implementation of Existence020 in which the decision has been moved to the class Decider and the result to the class Environment.
 * Compared to an Existence0, an Esitence1's "flow of consciousness" is generalized as a succession of Intentions and Obtentions.
 * The Decider obtains the previous Obtention and chooses the next Intention through its method decideIntention(Obtention).
 * The Environment provides the next Obtention through its method produceObtention(Intention). 
 * 
 * Existence1 illustrates the fact the Decider's input (the Obtention) is a consequence of its previous output (the Intention) 
 * rather than the other way around as it is the case in traditional agent modeling.
 * 
 * @author Olivier
 */
public class Existence021 implements Existence {

	public final String LABEL_E1 = "e1"; 
	public final String LABEL_E2 = "e2"; 
	public final String LABEL_R1 = "r1";
	public final String LABEL_R2 = "r2";

	protected Map<String,Experience> EXPERIENCES = new HashMap<String,Experience>();
	protected Map<String,Result> RESULTS = new HashMap<String,Result>();
	protected Map<String,Interaction> INTERACTIONS = new HashMap<String,Interaction>() ;

	protected Decider proactive;
	protected Environment reactive;
	protected Obtention obtention;
	
	public Existence021(){
		initExistence();
	}	
	
	protected void initExistence(){
		this.proactive = new Decider021(this);
		this.reactive = new Environment1(this);
		
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
		
		Intention intention = this.proactive.chooseIntention(this.obtention);
		Trace.addEventElement("intend", intention.getLabel());
		this.obtention = this.reactive.provideObtention(intention);
		Trace.addEventElement("obtain", this.obtention.getLabel());
		
		this.learn();
				
		return print(intention, this.obtention);
	}
	
	protected void learn(){
	}
	
	protected String print(Intention intention, Obtention obtention){
		return "";//obtention.getLabel();
	}
	
	public Interaction020 createOrGetPrimitiveInteraction(Experience experience, Result result, int valence) {
		Interaction020 interaction = (Interaction020)addOrGetIntearction(experience.getLabel() + result.getLabel()); 
		interaction.setExperience(experience);
		interaction.setResult(result);
		interaction.setValence(valence);
		return interaction;
	}

	protected Interaction addOrGetIntearction(String label) {
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, createInteraction(label));			
		return INTERACTIONS.get(label);
	}
	
	protected Interaction020 createInteraction(String label){
		return new Interaction020(label);
	}

	public Interaction020 getInteraction(String label){
		return (Interaction020)INTERACTIONS.get(label);
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
