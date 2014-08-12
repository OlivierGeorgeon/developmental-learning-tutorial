package coupling;

/**
 * An experience (aka experiment) that can be performed by the system.
 */
public class Experience {
	
	/**
	 * The experience's label.
	 */
	private String label;

	public Experience(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return this.label;
	}	
}
