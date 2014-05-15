package main;

import org.w3c.dom.Element;

import tracer.AbstractLiteTracer;
import tracer.ConsoleTracer;
import tracer.Trace;
import tracer.Tracer;
import Existence.Existence;
import Existence.Existence0;
import Existence.Existence01;
import Existence.Existence1;
import Existence.Existence2;

/**
 * The Main Class instantiates an Existence and a Tracer.  
 * The Main Class runs the Existence step by step in a finite loop.
 * The Tracer records the Existence's activity as it runs.
 * @author Olivier
 */
public class Main {
	
	public static void main(String[] args){
		
		Tracer<Element> tracer = new ConsoleTracer();
		//Tracer<Element> tracer = new AbstractLiteTracer("http://134.214.128.53/abstract/lite/php/stream/","OgoKXiacniNKfSYySo-npitjFOXwRM");
		//Tracer<Element> tracer = new AbstractLiteTracer("http://134.214.128.53/abstract/lite/php/stream/","l-kHWqeLDlSZT-TdBrLSoXVeBRCRsw");
		//Tracer<Element> tracer = new AbstractLiteTracer("http://macbook-pro-de-olivier-2.local/alite/php/stream/","BGKGGBbdjxbYzYAlvXrjbVMjOwyXEA");
		Trace.init(tracer);		

		//Existence existence = new Existence0();
		//Existence existence = new Existence01();
		Existence existence = new Existence2();
		
		for(int i = 0 ; i < 50 ; i++){			
			String stepTrace = existence.step();
			System.out.println(i + ": " + stepTrace);
		}
	}
}
