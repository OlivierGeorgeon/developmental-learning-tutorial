package main;

import org.w3c.dom.Element;

import existence.Existence;
import existence.Existence010;
import existence.Existence020;
import existence.Existence021;
import existence.Existence030;
import existence.Existence3_;

import tracer.AbstractLiteTracer;
import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;

/**
 * The Main Class instantiates an Existence and a Tracer.  
 * The Main Class runs the Existence step by step in a finite loop.
 * The Tracer records the Existence's activity as it runs.
 * @author Olivier
 */
public class Main {
	
	public static void main(String[] args){
		
		/** Change this line to use another tracer: */
		Tracer<Element> tracer = new ConsoleTracer();
		//Tracer<Element> tracer = new AbstractLiteTracer("http://134.214.128.53/abstract/lite/php/stream/","OgoKXiacniNKfSYySo-npitjFOXwRM");
		//Tracer<Element> tracer = new AbstractLiteTracer("http://134.214.128.53/abstract/lite/php/stream/","l-kHWqeLDlSZT-TdBrLSoXVeBRCRsw");
		//Tracer<Element> tracer = new AbstractLiteTracer("http://macbook-pro-de-olivier-2.local/alite/php/stream/","BGKGGBbdjxbYzYAlvXrjbVMjOwyXEA");
		Trace.init(tracer);		

		/** Change this line to run another existence: */
		//Existence existence = new Existence0();
		//Existence existence = new Existence01();
		Existence existence = new Existence030();
		//Existence existence = new Existence3();
		
		/** Change this line to adjust the number of cycles of the loop: */
		for(int i = 0 ; i < 20 ; i++){			
			String stepTrace = existence.step();
			System.out.println(i + ": " + stepTrace);
		}
	}
}
