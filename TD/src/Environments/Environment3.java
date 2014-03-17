package Environments;

import main.Main;
import coupling.Experience;
import coupling.Result;

public class Environment3 implements Environment {

	private Experience experience_1;

	public Result giveResult(Experience experience){
		
		Result result;

		if (experience_1!=experience)
			result =  Main.r2;
		else
			result =  Main.r1;
		
		experience_1 = experience;
		
		return result;
	}
}
