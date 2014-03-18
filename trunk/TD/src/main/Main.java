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
import coupling.Result;

public class Main {
	
	private static Coupling coupling = new Coupling1();

	public static void main(String[] args){
		
		//Agent agent = new Agent1(coupling);
		Agent agent = new Agent2(coupling);
		
		//Environment env = new Environment1(coupling);
		//Environment env = new Environment2(coupling);
		Environment environment = new Environment3(coupling);
		
		Experience experience = coupling.createOrGetExperience(Coupling.LABEL_E1);
		Result result = coupling.createOrGetResult(Coupling.LABEL_R1);
		
		for(int i=0 ; i < 10 ; i++){
			experience = agent.chooseExperience(result);
			result = environment.giveResult(experience);
			System.out.println(coupling.getInteraction(experience.getLabel() + result.getLabel()).toString());
		}
	}
}
