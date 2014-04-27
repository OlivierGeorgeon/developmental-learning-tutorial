package tracer;

import org.w3c.dom.*;

public class ConsoleTracer implements Tracer<Element> {

	/**
	 * Close the tracer
	 * @return true if tracer ok
	 */
	public boolean close(){
		return true;
	}

	/**
	 * Create a new event
	 * @param t the time stamp
	 */
	public void startNewEvent(int t){
		System.out.println("step " + t);
	}
	
	/**
	 * Closes the current event.
	 */
	public void finishEvent(){
		
	}
	
	/**
	 * Add a new property to the current event
	 * @param name The property's name
	 * @return the added event element
	 */
	public Element addEventElement(String name){
		System.out.println(name);
		return null;
	}
	
	/**
	 * @param name The element's name
	 * @param value The element's string value
	 */
	public void addEventElement(String name, String value){
		System.out.println(name + " " + value);
	}

	/**
	 * @param element The element 
	 * @param name The name of the sub element
	 * @return The sub element.
	 */
	public Element addSubelement(Element element, String name){
		System.out.println("    " + name);
		return null;
	}
	
	/**
	 * @param element The element
	 * @param name The name of the sub element
	 * @param textContent The text content of the sub element.
	 */
	public void addSubelement(Element element, String name, String textContent){
		System.out.println("    " + textContent);
	}

	/**
	 * Create an event that can be populated using its reference.
	 * @param source The source of the event: Ernest or user
	 * @param type The event's type.
	 * @param t The event's time stamp.
	 * @return The pointer to the event.
	 */
	public Element newEvent(String source, String type, int t){
		return null;
	}

}
