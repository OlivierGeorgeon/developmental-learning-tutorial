package agent.decider;

import java.util.ArrayList;
import java.util.List;
import coupling.Coupling4;
import coupling.interaction.Interaction3;

public class Episode4 extends Episode3{
	
	public Episode4(Coupling4 coupling){
		super(coupling);
	}

	@Override
	public Episode createNext(){
		Episode3 nextEpisode = null;
        try {
        	nextEpisode =  (Episode3)super.clone();
        	this.getContextEpisode().setContextEpisode(null);
        	nextEpisode.setContextEpisode(this);
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not allowed.");
        }		
		return nextEpisode;
	}
	
	@Override
	public void store(Interaction3 enactedInteraction){
		List<Interaction3> enactedInteractions = new ArrayList<Interaction3>();
		enactedInteractions.add(enactedInteraction);

		if (this.getContextEpisode()!= null )
			for (Interaction3 contextInteraction : this.getContextEpisode().getEnactedInteractions()){
				Interaction3 interaction = this.getCoupling().createOrReinforceCompositeInteraction(contextInteraction, enactedInteraction);
				if (contextInteraction.getPreInteraction() == null)
					enactedInteractions.add(interaction);
			}
		this.setEnactedInteractions(enactedInteractions);
	}
}
