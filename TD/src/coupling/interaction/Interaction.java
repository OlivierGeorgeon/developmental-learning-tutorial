package coupling.interaction;

import coupling.Experience;
import coupling.Result;

public interface Interaction {

	public String getLabel();
	
	public int getValence();

	public Experience getExperience();

	public void setExperience(Experience experience) ;

	public Result getResult();

	public void setResult(Result result);

}
