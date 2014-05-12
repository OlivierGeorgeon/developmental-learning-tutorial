package Existence;

import coupling.Situation;
import coupling.Coupling;
import coupling.Coupling1;
import coupling.Intention;

public class Existence1 implements Existence {

	private Coupling coupling;	
	private Situation situation;

	public Existence1(){
		this.coupling = new Coupling1();
	}	
	
	@Override
	public String step() {
		
		Intention intention = this.coupling.chooseIntention(this.situation);
		this.situation = this.coupling.giveSituation(intention);
				
		return intention.getLabel() + this.situation.getLabel();
	}
}
