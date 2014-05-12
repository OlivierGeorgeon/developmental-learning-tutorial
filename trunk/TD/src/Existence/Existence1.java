package Existence;

import coupling.Obtention;
import coupling.Coupling;
import coupling.Coupling1;
import coupling.Intention;

public class Existence1 implements Existence {

	private Coupling coupling;	
	private Obtention situation;

	public Existence1(){
		this.coupling = new Coupling1();
	}	
	
	@Override
	public String step() {
		
		Intention intention = this.coupling.chooseIntention(this.situation);
		this.situation = this.coupling.giveOptention(intention);
				
		return intention.getLabel() + this.situation.getLabel();
	}
}
