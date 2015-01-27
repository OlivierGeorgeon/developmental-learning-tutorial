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
import coupling.interaction.Interaction030;
import coupling.interaction.Interaction040;
import coupling.interaction.Interaction060;

/**
 * Existence060 constructs phenomena.
 */
public class Existence060 extends Existence050 {

	private Map<String,Phenomenon> PHENOMENA = new HashMap<String ,Phenomenon>();
	
	private static Phenomenon060 UNKNWOWN_PHENOMENON = new Phenomenon060("n");
	
	private Phenomenon060 phenomenon = UNKNWOWN_PHENOMENON;
	
	private static final int BASE_EXCITEMENT = 1; 
	private static final int TOP_EXCITEMENT = 5;
	private int excitement = BASE_EXCITEMENT;
	
	@Override
	protected void initExistence(){
		// trace to local Abstract lite login: roesch, password: roesch
		Tracer<Element> tracer = new AbstractLiteTracer("http://localhost/alite/php/stream/","BGKGGBbdjxbYzYAlvXrjbVMjOwyXEA"); 
		Trace.init(tracer);
		
		/** You can instantiate another environment here. */
		this.setEnvironment(new EnvironmentString(this));
		
		for (Interaction i : INTERACTIONS.values()){
			UNKNWOWN_PHENOMENON.addPostInteraction((Interaction060)i);
		}
		
	}
	
	@Override
	public String step() {
		
		Interaction060 intendedInteraction;
		Experiment050 experiment;
		
		Trace.startNewEvent();
		
		if (this.getExcitement() >= TOP_EXCITEMENT)
			this.setExcitement(BASE_EXCITEMENT);

		// IF excited then keep trying the same interaction to confirm that it is persistent
		if (this.getExcitement() > BASE_EXCITEMENT){
			intendedInteraction = this.getPhenomenon().getPersistentInteraction();
			experiment = this.addOrGetAbstractExperiment(intendedInteraction);
			this.setExcitement(this.getExcitement()+1);
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
		Trace.addEventElement("arousal", this.getExcitement() + "");

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
		
		boolean phenomenonChange = false;
		
		Interaction060 enactedInteraction = (Interaction060)this.getEnactedInteraction();		
		Interaction060 superInteraction = (Interaction060)this.getSuperInteraction();
		
		Phenomenon060 phenomenon = this.getPhenomenon();
		
		// if the intended interaction was the persistent interaction 
		// but the enacted interaction is different then the phenomenon is invalid
		if (phenomenon != UNKNWOWN_PHENOMENON && intendedInteraction == phenomenon.getPersistentInteraction())
			if (enactedInteraction != intendedInteraction){
				this.getPhenomenon().setConsistent(false);
				this.setExcitement(BASE_EXCITEMENT);
				this.getPhenomenon().trace();
				this.setPhenomenon(UNKNWOWN_PHENOMENON);
				phenomenonChange = true;
			}
		
		// If there is a current phenomenon and the enacted interaction is not persistent
		// then the enacted interaction belong to this phenomenon's post-interactions
		if (phenomenon != UNKNWOWN_PHENOMENON){
			if (enactedInteraction != phenomenon.getPersistentInteraction()){
				phenomenon.addPostInteraction(enactedInteraction);
				phenomenon.trace();
				this.setPhenomenon(UNKNWOWN_PHENOMENON);
				phenomenonChange = true;
				this.setExcitement(BASE_EXCITEMENT);
			}
		}

		// If there is no current phenomenon
		// and the persistent interaction of an existing phenomenon is enacted 
		// Then the previous interaction is added to its pre-interactions.
		else if (superInteraction !=null)
			if (this.PHENOMENA.containsKey(enactedInteraction.getLabel())){
				phenomenon = (Phenomenon060)this.PHENOMENA.get(enactedInteraction.getLabel());
				if (phenomenon.isConsistent()){
					this.setPhenomenon(phenomenon);
					phenomenonChange = true;
					//phenomenon.addPreInteraction((Interaction060)superInteraction.getPreInteraction());
					phenomenon.trace();				
				}
			}

		// If the a pre-interaction of an existing phenomenon is enacted
		// then this phenomenon becomes active
		Phenomenon060 evokedPhenomenon = this.getPhenomenonByPreInteraction(enactedInteraction);
		if (evokedPhenomenon !=null){
			this.setPhenomenon(evokedPhenomenon);
			phenomenonChange = true;
			Trace.addEventElement("evoked_phenomenon", evokedPhenomenon.getLabel());
		}
		
		// If an interaction is enacted twice in a row then there is a suspected persistence
		// create a phenomenon if it does not yet exist
		if (superInteraction !=null){
			Interaction060 preInteraction = (Interaction060)superInteraction.getPreInteraction();
			if (preInteraction == enactedInteraction){
				if (!this.PHENOMENA.containsKey(enactedInteraction.getLabel())){
					Phenomenon060 newPhenomenon = createPhenomenon(enactedInteraction); 
					PHENOMENA.put(enactedInteraction.getLabel(), newPhenomenon);
					newPhenomenon.trace();
					this.setPhenomenon(newPhenomenon);
					phenomenonChange = true;
					Trace.addEventElement("new_phenomenon", newPhenomenon.getLabel());
					this.setExcitement(BASE_EXCITEMENT + 1);
				}
			}			
		}
		
		if (phenomenonChange){
//			if (this.getPhenomenon() == null)
//				Trace.addEventElement("current_phenomenon", "n");
//			else 
				Trace.addEventElement("current_phenomenon", this.getPhenomenon().getLabel());				
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
	
	public void setExcitement(int excitement){
		this.excitement = excitement;
	}

	public int getExcitement(){
		return this.excitement;
	}
	
	public Phenomenon060 getPhenomenonByPreInteraction(Interaction060 preinteraction){
		Phenomenon060 phenomenon = null;
		
		for(Phenomenon p : this.PHENOMENA.values()){
			if (p.isConsistent())
				if (p.getPreInteractions().contains(preinteraction))
					phenomenon = (Phenomenon060)p;
		}
		return phenomenon;
	}
	
	/**
	 * Learn composite interactions from 
	 * the previous super interaction, the context interaction, and the enacted interaction
	 * limited to the thrid level
	 */
	@Override
	public void learnCompositeInteraction(Interaction030 enactedInteraction){
		Interaction060 previousInteraction = (Interaction060)this.getEnactedInteraction(); 
		Interaction060 lastInteraction = (Interaction060)enactedInteraction;
		Interaction060 previousSuperInteraction = (Interaction060)this.getSuperInteraction();
		Interaction060 superInteraction = null;
        // learn [previous current] called the super interaction
		if (previousInteraction != null)
			superInteraction = (Interaction060)addOrGetAndReinforceCompositeInteraction(previousInteraction, lastInteraction);
		
		// Learn higher-level interactions
        if (previousSuperInteraction != null  && superInteraction.getLength() < 5
        		){	
            // learn [penultimate [previous current]]
            this.addOrGetAndReinforceCompositeInteraction(previousSuperInteraction.getPreInteraction(), superInteraction);
            // learn [[penultimate previous] current]
            this.addOrGetAndReinforceCompositeInteraction(previousSuperInteraction, lastInteraction);
        }
        this.setSuperInteraction(superInteraction);
	}


}
