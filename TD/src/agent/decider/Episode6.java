package agent.decider;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.*;

import tracer.Trace;
import coupling.Coupling3;
import coupling.Coupling6;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction3;

public class Episode6 extends Episode4{

	private List<Interaction3> series = new ArrayList<Interaction3>();
	
	private int valence;
	
	private Interaction3 alternateInteraction;
	
	public Episode6(Coupling3 coupling, Experience experience){
		super(coupling, experience);
		if (!experience.isPrimitive())
			this.series = experience.getInteraction().getSeries();
	}

	@Override
	public void record(Result result){	
		
		this.alternateInteraction = null;
		
		Interaction3 enactedPrimitiveInteraction = this.getCoupling().getInteraction(this.getPrimitiveExperience().getLabel() + result.getLabel());

		Trace.addEventElement("enacted_interaction", enactedPrimitiveInteraction.getLabel());
		Trace.addEventElement("primitive_enacted_schema", this.getPrimitiveExperience().getLabel());
		if (result != null && result.getLabel().equals(Coupling6.LABEL_TRUE)){
			Element e = Trace.addEventElement("effect");
			Trace.addSubelement(e, "color", "FFFFFF");
		}
		else{
			Element e = Trace.addEventElement("effect");
			Trace.addSubelement(e, "color", "FF0000");
		}
		Trace.addEventElement("valence", enactedPrimitiveInteraction.getValence() + "");
		Trace.addEventElement("satisfaction", enactedPrimitiveInteraction.getValence() + "");
		Trace.addEventElement("level", this.series.size() + "");
		if (this.series.size() > 1)
			Trace.addEventElement("top_level", this.series.size() + "");
		else
			Trace.addEventElement("top_level", "1");
		Trace.addEventElement("step", this.getStep() + "");

		valence += enactedPrimitiveInteraction.getValence();
		
		if (this.getExperience().isPrimitive()){
			this.setInteraction(enactedPrimitiveInteraction);
			this.setTerminated();
		}
		else{
			Interaction3 intendedPrimitiveInteraction = this.series.get(this.getStep());
			if (this.getStep() < this.series.size() -1){			
				// TODO Manage different results for longer interactions
				if (!enactedPrimitiveInteraction.equals(intendedPrimitiveInteraction)){
					// The alternate primitive interaction don't have a primitive experience, it cannot be enacted !!
					this.alternateInteraction = this.getCoupling().createOrGetInteraction(this.getExperience(), result, this.valence);
					Trace.addEventElement("alternate_interaction", alternateInteraction.getLabel());
					if (this.getStep() == 0)
						this.setInteraction(enactedPrimitiveInteraction);
					else
						// TODO construct the whole composite enacted interaction
						this.setInteraction(this.getCoupling().createOrGetCompositeInteraction(this.series.get(this.getStep() - 1), enactedPrimitiveInteraction));
						
					this.setTerminated();
				}
				this.incStep();
			}
			else{
				if (enactedPrimitiveInteraction.equals(intendedPrimitiveInteraction))
					this.setInteraction(this.getExperience().getInteraction());
				else{
					this.getCoupling().createPrimitiveInteraction(this.getExperience(), result, this.valence);
					this.alternateInteraction = this.getCoupling().getInteraction(this.getExperience().getLabel() + result.getLabel());
					Trace.addEventElement("alternate_interaction", alternateInteraction.getLabel());
					if (this.getStep() == 0)
						this.setInteraction(enactedPrimitiveInteraction);
					else 
						// TODO construct the whole composite enacted interaction
						this.setInteraction(this.getCoupling().createOrGetCompositeInteraction(this.series.get(this.getStep() - 1), enactedPrimitiveInteraction));
				}
				this.setTerminated();
			}			
		}
	}
	
	@Override
	public Experience nextPrimitiveExperience(){
		Experience nextExperience = this.getExperience();
		
		if (!this.getExperience().isPrimitive())
			nextExperience = this.series.get(this.getStep()).getExperience();
		
		this.setPrimitiveExperience(nextExperience);
		
		return nextExperience; 
	}
	
	public Interaction3 getAlternateInteraction(){
		return this.alternateInteraction;
	}
}
