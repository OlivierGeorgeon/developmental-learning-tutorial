package coupling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import agent.decider.Episode2;

import coupling.interaction.Interaction;
import coupling.interaction.Interaction2;

public class Coupling2 implements Coupling {

	private Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();

	private Map<String ,Result> RESULTS = new HashMap<String ,Result>();

	private Map<String , Interaction2> INTERACTIONS = new HashMap<String , Interaction2>() ;

	public Coupling2(){
		init();
	}
	
	@Override
	public Experience createOrGetExperience(String label) {
		if (!EXPERIENCES.containsKey(label))
			EXPERIENCES.put(label, new Experience(label));			
		return EXPERIENCES.get(label);
	}

	public Episode2 createEpisode(Experience experience) {
		return new Episode2(this, experience);
	}

	@Override
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

	public Interaction2 getOtherInteraction(Interaction2 interaction) {
		Interaction2 otherInteraction = (Interaction2)INTERACTIONS.values().toArray()[0];
		if (interaction != null)
			for (Interaction2 e : INTERACTIONS.values()){
				if (e.getExperience() != null && e.getExperience()!=interaction.getExperience()){
					otherInteraction =  e;
					break;
				}
			}		
		return otherInteraction;
	}

	@Override
	public Result createOrGetResult(String label) {
		if (!RESULTS.containsKey(label))
			RESULTS.put(label, new Result(label));			
		return RESULTS.get(label);
	}

	@Override
	public void createPrimitiveInteraction(Experience experience,
			Result result, int valence) {
		Interaction interaction = createOrGet(experience.getLabel() + result.getLabel(), valence); 
		interaction.setExperience(experience);
		interaction.setResult(result);
	}

	@Override
	public Interaction2 getInteraction(String label) {
		return INTERACTIONS.get(label);
	}

	public Collection<Interaction2> getInteractions(){
		return INTERACTIONS.values();
	}
	
	private Interaction2 createOrGet(String label, int valence) {
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, new Interaction2(label, valence));			
		return INTERACTIONS.get(label);
	}
	public void createCompositeInteraction(
		Interaction2 preInteraction, Interaction2 postInteraction) {
		int valence = preInteraction.getValence() + postInteraction.getValence();
		Interaction2 interaction = createOrGet(preInteraction.getLabel() + postInteraction.getLabel(), valence); 
		interaction.setPreInteraction(preInteraction);
		interaction.setPostInteraction(postInteraction);
		System.out.println("learn " + interaction.toString());
	}

	public List<Interaction2> getActivatedInteractions(Interaction2 interaction) {
		List<Interaction2> activatedInteractions = new ArrayList<Interaction2>();
		for (Interaction2 activatedInteraction : getInteractions())
			if (interaction==activatedInteraction.getPreInteraction())
				activatedInteractions.add(activatedInteraction);
		return activatedInteractions;
	}
	
	public Experience getFirstExperience() {
		return (Experience)EXPERIENCES.values().toArray()[0];
	}
	
	public Experience propose(Episode2 episode){
		Experience experience = this.getFirstExperience();
		for (Interaction2 activatedInteraction : this.getActivatedInteractions(episode))
			if (activatedInteraction.getPostInteraction().getValence() >= 0){
				experience = activatedInteraction.getPostInteraction().getExperience();
				System.out.println("propose " + experience.getLabel());
			}
			else{
				experience = this.getOtherExperience(activatedInteraction.getPostInteraction().getExperience());						
			}
		return experience;
	}
	
	protected List<Interaction2> getActivatedInteractions(Episode2 episode) {
		List<Interaction2> activatedInteractions = new ArrayList<Interaction2>();
		if (episode.getInteraction() != null)
			for (Interaction2 activatedInteraction : this.getInteractions())
				if (episode.getInteraction() == activatedInteraction.getPreInteraction())
					activatedInteractions.add(activatedInteraction);
		return activatedInteractions;
	}
	
	protected void init(){
		Experience e1 = createOrGetExperience(LABEL_E1);
		Experience e2 = createOrGetExperience(LABEL_E2);
		Result r1 = createOrGetResult(LABEL_R1);
		Result r2 = createOrGetResult(LABEL_R2);
		createPrimitiveInteraction(e1, r1, -1);
		createPrimitiveInteraction(e1, r2, 1);
		createPrimitiveInteraction(e2, r1, -1);
		createPrimitiveInteraction(e2, r2, 1);
	}

}
