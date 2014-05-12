package Existence;

import coupling.Obtention;
import coupling.Coupling;
import coupling.Intention;

public class ExistenceGeneric implements Existence {

	private Coupling coupling;	
	private Obtention obtention;

	public ExistenceGeneric(Coupling coupling){
		this.coupling = coupling;
	}	
	
	@Override
	public String step() {
		
		Intention intention = this.coupling.chooseIntention(this.obtention);
		this.obtention = this.coupling.giveObtention(intention);
				
		return intention.getLabel() + this.obtention.getLabel();
	}
}
