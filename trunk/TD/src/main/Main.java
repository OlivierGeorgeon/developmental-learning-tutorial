package main;

import Existence.Existence;
import Existence.Existence0;
import Existence.Existence01;
import Existence.Existence1;
import Existence.Existence2;

public class Main {
	
	public static void main(String[] args){
		
		//Existence existence = new Existence0();
		//Existence existence = new Existence01();
		Existence existence = new Existence2();
		
		for(int i = 0 ; i < 50 ; i++){			
			String trace = existence.step();
			System.out.println(i + ": " + trace);
		}
	}
}
