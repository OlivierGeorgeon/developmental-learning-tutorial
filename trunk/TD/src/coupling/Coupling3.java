package coupling;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import agent.decider.Episode3;
import agent.decider.Proposition;
import coupling.interaction.Interaction3;

public class Coupling3 implements Coupling {

	private Map<String ,Experience> EXPERIENCES = new HashMap<String ,Experience>();

	private Map<String ,Result> RESULTS = new HashMap<String ,Result>();

	private Map<String , Interaction3> INTERACTIONS = new HashMap<String , Interaction3>() ;
	
	public Coupling3(){
		init();
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
	
	@Override
	public Experience createOrGetExperience(String label) {
		if (!EXPERIENCES.containsKey(label))
			EXPERIENCES.put(label, new Experience(label));			
		return EXPERIENCES.get(label);
	}

	public Episode3 createEpisode(Experience experience) {
		return new Episode3(this, experience);
	}

	public Experience createOrGetCompositeExperience(Interaction3 compositeInteraction) {
		String label = compositeInteraction.getLabel();
		if (!EXPERIENCES.containsKey(label))
			EXPERIENCES.put(label, new Experience(compositeInteraction));			
		return EXPERIENCES.get(label);
	}

	@Override
	public Experience getOtherExperience(Experience experience) {
		Experience otherExperience = null;
		for (Experience e : EXPERIENCES.values()){
			if (e!=experience && e.isPrimitive()){
				otherExperience =  e;
				break;
			}
		}		
		return otherExperience;
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
		Interaction3 interaction = createOrGet(experience.getLabel() + result.getLabel(), valence); 
		interaction.setExperience(experience);
		interaction.setResult(result);
	}

	@Override
	public Interaction3 getInteraction(String label) {
		return INTERACTIONS.get(label);
	}

	public Collection<Interaction3> getInteractions(){
		return INTERACTIONS.values();
	}
	
	public Interaction3 createOrReinforceCompositeInteraction(
		Interaction3 preInteraction, Interaction3 postInteraction) {
		
		String label = "(" + preInteraction.getLabel() + postInteraction.getLabel() + ")";
		Interaction3 interaction = getInteraction(label);
		if (interaction == null){
			int valence = preInteraction.getValence() + postInteraction.getValence();	
			interaction = createOrGet(label, valence); 
			interaction.setPreInteraction(preInteraction);
			interaction.setPostInteraction(postInteraction);
			interaction.incrementWeight();
			System.out.println("learn " + interaction.toString());
		}
		else
			interaction.incrementWeight();
		return interaction;
	}
	
	protected Interaction3 createOrGet(String label, int valence) {
		if (!INTERACTIONS.containsKey(label))
			INTERACTIONS.put(label, new Interaction3(label, valence));			
		return INTERACTIONS.get(label);
	}	
	
	public List<Proposition> getDefaultPropositions(){
		List<Proposition> propositions = new ArrayList<Proposition>();
		for (Experience experience : EXPERIENCES.values()){
			if (experience.isPrimitive()){
				Proposition proposition = new Proposition(experience, 0);
				propositions.add(proposition);
			}
		}
		return propositions;
	}
	
	public Experience getFirstExperience() {
		return (Experience)EXPERIENCES.values().toArray()[0];
	}

}
