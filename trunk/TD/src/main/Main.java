package main;

import Agents.Agent;
import Agents.Agent1;
import Agents.Agent2;
import Environments.Environment1;
import Environments.Environment;
import Environments.Environment2;
import Environments.Environment3;
import coupling.Coupling;
import coupling.Coupling1;
import coupling.Experience;
import coupling.Interaction;
import coupling.Result;

public class Main {
	
	private static Coupling coupling = new Coupling1();

	public static Experience e1 = coupling.createOrGetExperience("e1");
	public static Experience e2 = coupling.createOrGetExperience("e2");
	public static Result r1 = coupling.createOrGetResult("r1");
	public static Result r2 = coupling.createOrGetResult("r2");
	private static Interaction i11 = coupling.createPrimitiveInteraction(e1, r1, -1);
	private static Interaction i12 = coupling.createPrimitiveInteraction(e1, r2, 1);
	private static Interaction i21 = coupling.createPrimitiveInteraction(e2, r1, -1);
	private static Interaction i22 = coupling.createPrimitiveInteraction(e2, r2, 1);

	public static void main(String[] args){
		
		//Agent agent = new Agent1(coupling);
		Agent agent = new Agent2(coupling);
		
		//Environment env = new Environment1();
		//Environment env = new Environment2();
		Environment env = new Environment3();
		Experience experience = e1;
		Result result = r1;
		
		for(int i=0 ; i < 10 ; i++){
			experience = agent.chooseExperience(result);
			result = env.giveResult(experience);
			System.out.println(coupling.getInteraction(experience.getLabel() + result.getLabel()).toString());
		}
	}
}
