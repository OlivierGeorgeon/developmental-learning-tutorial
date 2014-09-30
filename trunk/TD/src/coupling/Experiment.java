package coupling;

/**
 * An experiment that can be chosen by the agent.
 */
public class Experiment {
	
	/**
	 * The experience's label.
	 */
	private String label;

	public Experiment(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}	
}
