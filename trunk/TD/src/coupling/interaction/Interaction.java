package coupling.interaction;

import coupling.Experience;
import coupling.Result;

public interface Interaction extends Comparable<Interaction>{

	/**
	 * @return The interaction's label
	 */
	public String getLabel();
	
	/**
	 * @return The interaction's valence
	 */
	public int getValence();

	/**
	 * @return The interaction's experience
	 */
	public Experience getExperience();

	/**
	 * @return The interaction's result
	 */
	public Result getResult();

	/**
	 * @param experience: The interaction's experience.
	 */
	public void setExperience(Experience experience) ;

	/**
	 * @param result: The interaction's result.
	 */
	public void setResult(Result result);

}
