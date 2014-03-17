package Agents;

import main.Main;
import primitives.Experience;
import primitives.Interaction;
import primitives.Result;

public class Agent1 implements Agent{

	private Experience experience = Main.e1;
	
	public Experience chooseExperience(Result result){
		
		if (Interaction.get(this.experience.getLabel() + result.getLabel()).getValue() < 0)
			this.experience = Experience.getOther(this.experience);		
		return this.experience;
	}
}
