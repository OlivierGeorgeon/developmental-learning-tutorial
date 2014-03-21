package main;

import agent.Agent;
import agent.Agent1;
import agent.Agent2;
import agent.Agent3;
import Environments.Environment1;
import Environments.Environment;
import Environments.Environment2;
import Environments.Environment3;
import Environments.Environment4;
import coupling.Coupling;
import coupling.Coupling1;
import coupling.Experience;
import coupling.Result;

public class Main {
	
	public static void main(String[] args){
		
		Coupling coupling = new Coupling1();

		//Agent agent = new Agent1(coupling);
		Agent agent = new Agent3(coupling);
		
		//Environment environment = new Environment1(coupling);
		//Environment environment = new Environment2(coupling);
		//Environment environment = new Environment3(coupling);
		Environment environment = new Environment4(coupling);
		
		Experience experience = coupling.createOrGetExperience(Coupling.LABEL_E1);
		Result result = coupling.createOrGetResult(Coupling.LABEL_R1);
		
		for(int i=0 ; i < 20 ; i++){
			experience = agent.chooseExperience(result);
			result = environment.giveResult(experience);
			System.out.println(coupling.getInteraction(experience.getLabel() + result.getLabel()).toString());
		}
	}
}
