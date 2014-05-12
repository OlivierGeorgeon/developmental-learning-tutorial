package Existence;

import coupling.Coupling1;
import coupling.Obtention;
import coupling.Coupling;
import coupling.Intention;
import coupling.interaction.Interaction1;

public class Existence1 implements Existence {

	private Coupling coupling;	
	private Obtention obtention;

	public Existence1(){
		this.coupling = new Coupling1<Interaction1>();
	}	
	
	@Override
	public String step() {
		
		Intention intention = this.coupling.chooseIntention(this.obtention);
		this.obtention = this.coupling.giveObtention(intention);
				
		return print(intention, this.obtention);
	}
	
	protected String print(Intention intention, Obtention obtention){
		return this.coupling.getInteraction(intention.getLabel() + obtention.getLabel()).toString();
	}
}
