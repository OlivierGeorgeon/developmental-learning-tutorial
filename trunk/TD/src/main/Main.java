package main;

import coupling.Coupling;
import coupling.Coupling1;
import coupling.Coupling2;
import coupling.Coupling3;
import coupling.Coupling4;
import coupling.CouplingString;
import agent.Agent;
import agent.Agent1;
import agent.Agent2;
import agent.Agent3;
import agent.Agent4;
import Environments.Environment;
import Environments.Environment0;
import Environments.Environment1;
import Environments.Environment2;
import Environments.Environment3;
import Environments.Environment4;
import Environments.EnvironmentString;
import coupling.Experience;
import coupling.Result;

public class Main {
	
	public static void main(String[] args){
		
		//Coupling coupling = new Coupling1();
		//Agent agent = new Agent1(coupling);
		
		//Coupling2 coupling = new Coupling2();
		//Agent agent = new Agent2(coupling);

		//Coupling3 coupling = new Coupling3();
		//Agent agent = new Agent3(coupling);
		
		//Coupling4 coupling = new Coupling4();
		CouplingString coupling = new CouplingString();
		Agent agent = new Agent4(coupling);
		
		//Environment environment = new Environment1(coupling);
		//Environment environment = new Environment2(coupling);
		//Environment environment = new Environment3(coupling);
		//Environment environment = new Environment4(coupling);
		Environment environment = new EnvironmentString(coupling);
		
		Experience experience; 
		Result result = null;
		
		for(int i=0 ; i < 150 ; i++){
			experience = agent.chooseExperience(result);
			result = environment.giveResult(experience);
			System.out.println(i + ". " + coupling.getInteraction(experience.getLabel() 
					+ result.getLabel()).toString());
		}
	}
}
