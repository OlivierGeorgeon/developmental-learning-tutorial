package tracer;

import org.w3c.dom.*;

/**
 * The Class Trace provides static methods that can be called from anywhere in the project to record elements of trace.   
 * A new Event is created by the method startNewEvent(). Events have incremental time codes.
 * The other methods add new elements and sub-elements to the current Event.
 * @author Olivier
 */
public class Trace {
	
	private static Tracer<Element> tracer;
	private static int time = 0;

	public static void init(Tracer<Element> t){
		tracer = t;
	}
	
	public static void startNewEvent(){
		if (tracer != null)
			tracer.startNewEvent(time); // ++
	}
	
	public static void incTimeCode(){
		time++;
	}

	public static Element addEventElement(String name){
		if (tracer != null)
			return tracer.addEventElement(name);
		else 
			return null;
	}

	public static void addEventElement(String name, String value){
		if (tracer != null)
			tracer.addEventElement(name, value);
	}

	public static Element addSubelement(Element element, String name){
		if (tracer != null)
			return tracer.addSubelement(element, name);
		else 
			return null;
	}
	
	public static void addSubelement(Element element, String name, String textContent){
		if (tracer != null)
			tracer.addSubelement(element, name, textContent);
	}

}
