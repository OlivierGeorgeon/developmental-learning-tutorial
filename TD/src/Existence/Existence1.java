package Existence;

import org.w3c.dom.Element;

import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;
import coupling.Coupling1;
import coupling.Obtention;
import coupling.Coupling;
import coupling.Intention;
import coupling.interaction.Interaction1;

public class Existence1 implements Existence {

	private Coupling coupling;	
	private Obtention obtention;

	public Existence1(){
		this.setCoupling(new Coupling1());
		Tracer<Element> tracer = new ConsoleTracer();
		Trace.init(tracer);
	}	
	
	@Override
	public String step() {
		
		Intention intention = this.coupling.chooseIntention(this.obtention);
		this.obtention = this.coupling.giveObtention(intention);
				
		return print(intention, this.obtention);
	}
	
	protected void setCoupling(Coupling coupling){
		this.coupling = coupling;		
	}
	
	protected String print(Intention intention, Obtention obtention){
		return this.coupling.getInteraction(intention.getLabel() + obtention.getLabel()).toString();
	}
}
