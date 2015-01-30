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
	
	public static final int UNPERSISTENT = -1;
	public static final int BASE_EXCITEMENT = 1; 
	public static final int TOP_EXCITEMENT = 4;
	public int excitement = BASE_EXCITEMENT;
	
	@Override
	protected void initExistence(){
		// trace to local Abstract lite login: roesch, password: roesch
		Tracer<Element> tracer = new AbstractLiteTracer("http://localhost/alite/php/stream/","BGKGGBbdjxbYzYAlvXrjbVMjOwyXEA"); 
		Trace.init(tracer);
		
		/** You can instantiate another environment here. */
		this.setEnvironment(new EnvironmentString(this));
		
		for (Interaction i : INTERACTIONS.values()){
			//UNKNWOWN_PHENOMENON.addPostInteraction((Interaction060)i);
		}
		
	}
	
	@Override
	public String step() {
		
		Interaction060 previousEnactedInteraction = (Interaction060)this.getEnactedInteraction();
		Interaction060 intendedInteraction;
		Experiment050 experiment;
		
		Trace.startNewEvent();
		
		// If excited then keep trying the same interaction to see if it is persistent
		if (this.getExcitement() > BASE_EXCITEMENT){
			intendedInteraction = previousEnactedInteraction;
			experiment = this.addOrGetAbstractExperiment(intendedInteraction);
		}
		// If not excited then choose another experiment.
		else{
			List<Anticipation> anticipations = anticipate();
			experiment =  (Experiment050)selectExperiment(anticipations);
			intendedInteraction = (Interaction060)experiment.getIntendedInteraction();
		}
		
		this.getPhenomenon().addPostExperiment(experiment);
		
		System.out.println("Intended "+ intendedInteraction.toString());
		Trace.addEventElement("intended_interaction", intendedInteraction.getLabel());
		Trace.addEventElement("length", intendedInteraction.getLength() + "");

		Interaction060 enactedInteraction = (Interaction060)this.enact(intendedInteraction);
		
		Trace.addEventElement("top_level", intendedInteraction.getLength() + "");
		
		if (previousEnactedInteraction != null && enactedInteraction != previousEnactedInteraction){
			previousEnactedInteraction.setPersistency(UNPERSISTENT);
			this.setExcitement(BASE_EXCITEMENT);
		}
		if (enactedInteraction != intendedInteraction){
			experiment.addEnactedInteraction(enactedInteraction);
			Trace.addEventElement("alternate_interaction", enactedInteraction.getLabel());			
		}

		System.out.println("Enacted "+ enactedInteraction.toString());
		
		this.learnCompositeInteraction(enactedInteraction);
		this.setEnactedInteraction(enactedInteraction);
		
		this.learnPhenomenon(intendedInteraction);		
		
		if (enactedInteraction.getValence() >= 0)
			this.setMood(Mood.PLEASED);
		else
			this.setMood(Mood.PAINED);

		// If the enacted interactions has not been tested for persistency yet then get excited
		if (enactedInteraction.getPersistency() < TOP_EXCITEMENT ){
			if (enactedInteraction.getPersistency() > UNPERSISTENT)
				this.setExcitement(this.getExcitement() + 1);
		}
		else
			this.setExcitement(BASE_EXCITEMENT);
		Trace.addEventElement("arousal", this.getExcitement() + "");
		
		//if (this.getExcitement() > TOP_EXCITEMENT + 2)
		//	this.setExcitement(BASE_EXCITEMENT);

		return "" + this.getMood();
	}
	
	@Override
	public Experiment selectExperiment(List<Anticipation> anticipations){
		
		// Look if there is still an experiment to play with from the current phenomenon
		Experiment050 playExperiment = this.getPlayExperiment();
		if (playExperiment == null)
			playExperiment = this.getPhenomenon().getPlayExperiment();
			
		if (playExperiment == null){
		
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
		Interaction060 superInteraction = (Interaction060)this.getSuperInteraction();
		
		Phenomenon060 phenomenon = this.getPhenomenon();
		Phenomenon060 newPhenomenon = phenomenon;
		
		// If a phenomenon is based on an unpersistent interaction then mark it inconsistent
		if (intendedInteraction == phenomenon.getPersistentInteraction()){
//			if (enactedInteraction != intendedInteraction){
//				this.getPhenomenon().setConsistent(false);
//				intendedInteraction.setPersistency(UNPERSISTENT);
//				this.setExcitement(BASE_EXCITEMENT);
//				this.getPhenomenon().trace();
//				newPhenomenon = UNKNWOWN_PHENOMENON;
//				this.setPhenomenon(newPhenomenon);
//			}
		}
		// If the intended interaction was not the persistent interaction 
		// then the enacted interaction belong to this phenomenon's post-interactions
		// and the current phenomenon is now unknown
		else{
			phenomenon.addPostInteraction(enactedInteraction);
			if (phenomenon != UNKNWOWN_PHENOMENON){
				phenomenon.trace();
				newPhenomenon = UNKNWOWN_PHENOMENON;
				this.setPhenomenon(newPhenomenon);
			}
		}

		// If the persistent interaction of an existing phenomenon is enacted 
		// Then it becomes the current phenomenon.
		//if (superInteraction !=null)
			if (this.PHENOMENA.containsKey(enactedInteraction.getLabel())){
				newPhenomenon = (Phenomenon060)this.PHENOMENA.get(enactedInteraction.getLabel());
				if (newPhenomenon.isConsistent()){
					this.setPhenomenon(newPhenomenon);
					//phenomenon.addPreInteraction((Interaction060)superInteraction.getPreInteraction());
					newPhenomenon.trace();				
				}
			}

		// If the a pre-interaction of an existing phenomenon is enacted
		// then this phenomenon becomes active
		Phenomenon060 evokedPhenomenon = this.getPhenomenonByPreInteraction(enactedInteraction);
		if (evokedPhenomenon != null){
			newPhenomenon = evokedPhenomenon;
			if (newPhenomenon != UNKNWOWN_PHENOMENON){
				this.setPhenomenon(newPhenomenon);
				Trace.addEventElement("evoked_phenomenon", newPhenomenon.getLabel());
			}
		}
		
		// If an interaction is enacted twice in a row
		// then its persistency value is incremented.
		// create a phenomenon if it does not yet exist
		// and get excited
		if (superInteraction != null){
			Interaction060 preInteraction = (Interaction060)superInteraction.getPreInteraction();
			if (preInteraction == enactedInteraction)
				enactedInteraction.incPersistency();
		}
		
		// If the enacted interaction is persistent then create a phenomenon from it
		if (enactedInteraction.getPersistency() >= TOP_EXCITEMENT){
			if (!this.PHENOMENA.containsKey(enactedInteraction.getLabel())){
				newPhenomenon = createPhenomenon(enactedInteraction); 
				PHENOMENA.put(enactedInteraction.getLabel(), newPhenomenon);
				newPhenomenon.trace();
				this.setPhenomenon(newPhenomenon);
				Trace.addEventElement("new_phenomenon", newPhenomenon.getLabel());
			}			
		}
		
		if (phenomenon != newPhenomenon)
				Trace.addEventElement("current_phenomenon", newPhenomenon.getLabel());				
		
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
	
	public Experiment050 getPlayExperiment(){
		
		Phenomenon060 phenomenon = this.getPhenomenon();
		
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
