package Existence;

import org.w3c.dom.Element;

import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;
import coupling.Coupling2;
import coupling.Coupling21;

public class Existence2 extends Existence1 {

	public Existence2(){
		this.setCoupling(new Coupling2());
		//this.setCoupling(new Coupling20());
		
		Tracer<Element> tracer = new ConsoleTracer();
		Trace.init(tracer);
	}	

}
