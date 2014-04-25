package tracer;

import org.w3c.dom.*;

public class Trace {
	
	private static Tracer<Element> tracer;
	private static int time = 0;

	public static void init(Tracer<Element> t){
		tracer = t;
	}
	
	public static void startNewEvent(){
		if (tracer != null)
			tracer.startNewEvent(time++);
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
