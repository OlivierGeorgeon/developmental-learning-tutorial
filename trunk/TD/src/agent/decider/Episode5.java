package agent.decider;

import java.util.ArrayList;
import java.util.List;
import coupling.Coupling3;
import coupling.Intention4;
import coupling.Experience;
import coupling.Result;
import coupling.interaction.Interaction030;
import coupling.interaction.Interaction031;

public class Episode5 extends Intention4{

	private List<Interaction030> series = new ArrayList<Interaction030>();
	
	private int valence;
	
	private Interaction031 alternateInteraction;

	public Episode5(Coupling3 coupling, Experience experience){
		super(coupling, experience);
		if (!experience.isAbstract())
			this.series = experience.getIntendedInteraction().getSeries();
	}

	@Override
	public void record(Result result){	
		this.alternateInteraction = null;
		Interaction031 enactedPrimitiveInteraction = this.getCoupling().getInteraction(this.getPrimitiveExperience().getLabel() + result.getLabel());
		valence += enactedPrimitiveInteraction.getValence();
		
		if (this.getExperience().isAbstract()){
			this.setIntendedInteraction(enactedPrimitiveInteraction);
			this.setTerminated();
		}
		else{
			Interaction030 intendedPrimitiveInteraction = this.series.get(this.getStep());
			if (this.getStep() < this.series.size() -1){			
				// TODO Manage different results for longer interactions
				if (!enactedPrimitiveInteraction.equals(intendedPrimitiveInteraction)){
					// The alternate primitive interaction don't have a primitive experience, it cannot be enacted !!
					this.alternateInteraction = this.getCoupling().createOrGetInteraction(this.getExperience(), result, this.valence);
					System.out.println("alternate interaction " + alternateInteraction.getLabel());
					if (this.getStep() == 0)
						this.setIntendedInteraction(enactedPrimitiveInteraction);
					if (this.getStep() >= 1)
						this.setIntendedInteraction(this.getCoupling().getInteraction("(" + this.series.get(0).getLabel() + enactedPrimitiveInteraction.getLabel() + ")"));
					this.setTerminated();
				}
				this.incStep();
			}
			else{
				if (enactedPrimitiveInteraction.equals(intendedPrimitiveInteraction))
					this.setIntendedInteraction(this.getExperience().getIntendedInteraction());
				else{
					this.getCoupling().createOrGetPrimitiveInteraction(this.getExperience(), result, this.valence);
					this.alternateInteraction = this.getCoupling().getInteraction(this.getExperience().getLabel() + result.getLabel());
					System.out.println("alternate interaction " + alternateInteraction.getLabel());
					if (this.getStep() == 0)
						this.setIntendedInteraction(enactedPrimitiveInteraction);
					if (this.getStep() >= 1)
						this.setIntendedInteraction(this.getCoupling().getInteraction("(" + this.series.get(0).getLabel() + enactedPrimitiveInteraction.getLabel() + ")"));
				}
				this.setTerminated();
			}			
		}
	}
	
	@Override
	public Experience nextPrimitiveExperience(){
		Experience nextExperience = this.getExperience();
		
		if (!this.getExperience().isAbstract())
			nextExperience = this.series.get(this.getStep()).getExperience();
		
		this.setPrimitiveExperience(nextExperience);
		
		return nextExperience; 
	}
	
	public Interaction031 getAlternateInteraction(){
		return this.alternateInteraction;
	}
}
