package Agents;

import coupling.Experience;
import coupling.Result;

public interface Agent {

	public Experience chooseExperience(Result result);

}
