package main;

import Agents.Agent;
import Agents.Agent1;
import Environments.Environment1;
import Environments.Environment;
import Environments.Environment2;
import Environments.Environment3;
import primitives.Experience;
import primitives.Interaction;
import primitives.Result;

public class Main {

	public static Experience e1 = Experience.create("e1");
	public static Experience e2 = Experience.create("e2");
	public static Result r1 = new Result("r1");
	public static Result r2 = new Result("r2");
	public static Interaction i11 = Interaction.create(e1.getLabel() + r1.getLabel(), 1);
	public static Interaction i12 = Interaction.create(e1.getLabel() + r2.getLabel(), -1);
	public static Interaction i21 = Interaction.create(e2.getLabel() + r1.getLabel(), 1);
	public static Interaction i22 = Interaction.create(e2.getLabel() + r2.getLabel(), -1);

	public static void main(String[] args){
		
		Agent agent = new Agent1();
		//Environment env = new Environment1();
		Environment env = new Environment2();
		//Environment env = new Environment3();
		Experience experience = e1;
		Result result = r1;
		
		for(int i=0 ; i < 10 ; i++){
			experience = agent.chooseExperience(result);
			result = env.giveResult(experience);
			System.out.println(experience.getLabel() + "," + result.getLabel() + "," + Interaction.get(experience.getLabel() + result.getLabel()).getValue());
		}
	}
}
