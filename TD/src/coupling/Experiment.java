package coupling;

/**
 * An experience (aka experiment) that can be performed by the system.
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
