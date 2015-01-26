package existence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tracer.AbstractLiteTracer;
import tracer.Trace;
import tracer.Tracer;
import environment.EnvironmentString;
import existence.Existence010.Mood;
import org.w3c.dom.*;

import agent.Anticipation;
import agent.Anticipation031;
import agent.Phenomenon;
import agent.Phenomenon060;
import coupling.Experiment;
import coupling.Experiment050;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction040;
import coupling.interaction.Interaction060;

/**
 * Existence060 constructs phenomena.
 */
public class Existence060 extends Existence050 {

	private Map<String,Phenomenon> PHENOMENA = new HashMap<String ,Phenomenon>();
	
	private Phenomenon060 phenomenon;
	
	private final int BASE_AROUSAL = 1; 
	private final int TOP_AROUSAL = 5;
	private int arousal = BASE_AROUSAL;
	
	@Override
	protected void initExistence(){
		// trace to local Abstract lite login: roesch, password: roesch
		Tracer<Element> tracer = new AbstractLiteTracer("http://localhost/alite/php/stream/","BGKGGBbdjxbYzYAlvXrjbVMjOwyXEA"); 
		Trace.init(tracer);
		
		/** You can instantiate another environment here. */
		this.setEnvironment(new EnvironmentString(this));
	}
	
	@Override
	public String step() {
		
		Interaction060 intendedInteraction;
		Experiment050 experiment;
		
		Trace.startNewEvent();

		// IF excited then keep trying the same interaction to confirm that it is persistent
		if (this.getArousal() > this.BASE_AROUSAL && this.getArousal() < this.TOP_AROUSAL){
			intendedInteraction = this.getPhenomenon().getPersistentInteraction();
			experiment = this.addOrGetAbstractExperience(intendedInteraction);
		}
		// If not excited then choose another experiment.
		else{
			List<Anticipation> anticipations = anticipate();
			experiment =  (Experiment050)selectExperiment(anticipations);
			intendedInteraction = (Interaction060)experiment.getIntendedInteraction();
		}
		
		System.out.println("Intended "+ intendedInteraction.toString());

		Trace.addEventElement("intended_interaction", intendedInteraction.getLabel());
		Trace.addEventElement("length", intendedInteraction.getLength() + "");
		Trace.addEventElement("arousal", this.getArousal() + "");

		Interaction040 enactedInteraction = this.enact(intendedInteraction);
		
		Trace.addEventElement("top_level", intendedInteraction.getLength() + "");
		if (enactedInteraction != intendedInteraction){
			experiment.addEnactedInteraction(enactedInteraction);
			Trace.addEventElement("alternate_interaction", enactedInteraction.getLabel());
		}

		System.out.println("Enacted "+ enactedInteraction.toString());
		
		if (enactedInteraction.getValence() >= 0)
			this.setMood(Mood.PLEASED);
		else
			this.setMood(Mood.PAINED);

		this.learnCompositeInteraction(enactedInteraction);
		this.setEnactedInteraction(enactedInteraction);
		
		if (this.getArousal() > this.BASE_AROUSAL){
			if (enactedInteraction != intendedInteraction)
				this.getPhenomenon().setConsistent(false);
		}
		else
			this.learnPhenomenon(intendedInteraction);		
		
		return "" + this.getMood();
	}
	
	@Override
	public Experiment selectExperiment(List<Anticipation> anticipations){
		
		Phenomenon060 phenomenon = this.getPhenomenon();
		Experiment050 playExperiment = null;
		if (phenomenon != null)
			playExperiment = this.getOtherExperiment(phenomenon);
		
		if (phenomenon == null || playExperiment == null){
		
			// The list of anticipations is never empty because all the experiences are proposed by default with a proclivity of 0
			Collections.sort(anticipations);
			Element e = Trace.addEventElement("propositions");
			for (Anticipation anticipation : anticipations){
				System.out.println("propose " + anticipation.toString());
				Trace.addSubelement(e,"propose", anticipation.toString());
			}
			
			Anticipation031 selectedAnticipation = (Anticipation031)anticipations.get(0);
			return selectedAnticipation.getExperience();
		}
		else {
			Trace.addEventElement("play", playExperiment.getLabel());
			return playExperiment;
		}
	}

	@Override
	protected Interaction060 createInteraction(String label){
		return new Interaction060(label);
	}
	
	/**
	 * Learn a phenomenon from the super interaction
	 */
	protected void learnPhenomenon(Interaction060 intendedInteraction){
		
		Interaction060 enactedInteraction = (Interaction060)this.getEnactedInteraction();		
		Interaction060 superInteraction = (Interaction060)this.getPreviousSuperInteraction();
		
		if (superInteraction != null){
			Interaction060 preInteraction = (Interaction060)superInteraction.getPreInteraction();
			Interaction060 postInteraction = (Interaction060)superInteraction.getPostInteraction();
			if (this.PHENOMENA.containsKey(preInteraction.getLabel())){
				// Add post-interaction
				Phenomenon060 phenomenon = (Phenomenon060)this.PHENOMENA.get(preInteraction.getLabel());
				phenomenon.addPostInteraction(postInteraction);
				phenomenon.trace();
			}
			else if (this.PHENOMENA.containsKey(postInteraction.getLabel())){
				// Add pre-interaction
				Phenomenon060 phenomenon = (Phenomenon060)this.PHENOMENA.get(postInteraction.getLabel());
				phenomenon.addPreInteraction(preInteraction);
				phenomenon.trace();
			}
			else if (preInteraction == postInteraction){
				// Create phenomenon
				Phenomenon060 phenomenon = createPhenomenon(preInteraction); 
				PHENOMENA.put(preInteraction.getLabel(), phenomenon);
				phenomenon.trace();
			}
		}		

		// Current phenomenon
		Phenomenon060 phenomenon = (Phenomenon060)this.PHENOMENA.get(enactedInteraction.getLabel());
		if (phenomenon != null && phenomenon.isConsistent()){
			this.setPhenomenon(phenomenon);
			Trace.addEventElement("current_phenomenon", phenomenon.getLabel());
			this.setArousal(this.getArousal() + 1);
		}
		else{
			if (this.getPhenomenon() != null){
				this.setPhenomenon(null);
				Trace.addEventElement("current_phenomenon", "n");
				this.setArousal(BASE_AROUSAL);
			}
		}
	}
	
	/**
	 * Creates a new phenomenon type from its persistent interaction.
	 * @param persistentInteraction: The persistent interaction.
	 * @return The phenomenon.
	 */
	protected Phenomenon060 createPhenomenon(Interaction060 persistentInteraction){
		return new Phenomenon060(persistentInteraction);
	}
	
	public void setPhenomenon(Phenomenon060 phenomenon){
		this.phenomenon = phenomenon;
	}

	public Phenomenon060 getPhenomenon(){
		return this.phenomenon;
	}
	
	public Experiment050 getOtherExperiment(Phenomenon060 phenomenon){
		
		for (Experiment e : this.EXPERIENCES.values()){
			Experiment050 experiment = (Experiment050)e;
			Interaction060 interaction = (Interaction060)experiment.getIntendedInteraction(); 
			if (interaction.isPrimitive() && !phenomenon.isAlreadyTried(experiment)){
				return experiment;
			}
		}
		return null;		
	}
	
	public void setArousal(int arousal){
		this.arousal = arousal;
	}

	public int getArousal(){
		return this.arousal;
	}

}
