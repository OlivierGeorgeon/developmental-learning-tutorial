package Environments;

import main.Main;
import coupling.Experience;
import coupling.Result;

public class Environment2 implements Environment {
	
	public Result giveResult(Experience experience){
		if (experience.equals(Main.e1))
			return Main.r2;
		else
			return Main.r1;
	}
}
