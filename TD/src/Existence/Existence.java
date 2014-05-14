package Existence;

/**
 * An Existence is an Object that simulates a "flow of consciousness" when it is run step by step.   
 * Each call to the Step() method generates an "event of consciousness" that can be traced.
 * (The Existence also traces other "elements of event of consciousness" using the Trace static class)
 * @author Olivier
 */
public interface Existence {
	
	public String step();

}
