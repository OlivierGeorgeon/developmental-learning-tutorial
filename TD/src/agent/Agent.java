package agent;

import coupling.Experience;
import coupling.Result;

public interface Agent {

	public abstract Experience chooseExperience(Result result);

}
