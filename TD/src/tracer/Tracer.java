package tracer;

/**
 * Generates Ernest's activity traces. 
 * @author Pierre-Yves Ronot, Olivier Georgeon 
 * @param <EventElement> The element of trace
 */
public interface Tracer<EventElement> {

	/**
	 * Close the tracer
	 * @return true if tracer ok
	 */
	public boolean close();

	/**
	 * Create a new event
	 * @param t the time stamp
	 */
	public void startNewEvent(int t);
	
	/**
	 * Closes the current event.
	 */
	public void finishEvent();
	
	/**
	 * Add a new property to the current event
	 * @param name The property's name
	 * @return the added event element
	 */
	public EventElement addEventElement(String name);
	
	/**
	 * @param name The element's name
	 * @param value The element's string value
	 */
	public void addEventElement(String name, String value);

	/**
	 * @param element The element 
	 * @param name The name of the sub element
	 * @return The sub element.
	 */
	public EventElement addSubelement(EventElement element, String name);
	
	/**
	 * @param element The element
	 * @param name The name of the sub element
	 * @param textContent The text content of the sub element.
	 */
	public void addSubelement(EventElement element, String name, String textContent);

	/**
	 * Create an event that can be populated using its reference.
	 * @param source The source of the event: Ernest or user
	 * @param type The event's type.
	 * @param t The event's time stamp.
	 * @return The pointer to the event.
	 */
	public EventElement newEvent(String source, String type, int t);

}
