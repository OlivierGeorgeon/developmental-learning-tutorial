package Existence;

import coupling.Obtention;
import coupling.Coupling;
import coupling.Intention;

public class Existence1 implements Existence {

	private Coupling coupling;	
	private Obtention obtention;

	public Existence1(Coupling coupling){
		this.coupling = coupling;
	}	
	
	@Override
	public String step() {
		
		Intention intention = this.coupling.chooseIntention(this.obtention);
		this.obtention = this.coupling.giveObtention(intention);
				
		return intention.getLabel() + this.obtention.getLabel();
	}
}
