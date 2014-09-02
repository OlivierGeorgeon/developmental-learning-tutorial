package reactive;

import org.w3c.dom.*;

import tracer.Trace;
import coupling.Experience;
import coupling.Result;
import existence.Existence022;
import existence.Existence6;

/**
 * This class implements the String Environment 
 * used by Georgeon & Hassas in their paper "Single agents can be constructivist too".
 */
public class EnvironmentString extends Environment5{
	
	private static final int WIDTH = 20;	
	private int position = 0;
	
	private int[] board = {6, 3, 5, 4, 7, 3, 5, 3, 1, 5, 6, 3, 5, 4, 7, 3, 5, 3, 9, 5};	
	//private int[] board = {6, 3, 5, 4, 7, 3, 5, 3, 9, 5};	

	/**
	 * Process a primitive schema and return its enaction status.
	 * @param s The string code that represents the primitive schema to enact.
	 * @return The boolean feedback resulting from the schema enaction.
	 */

	public EnvironmentString(Existence6  existence){
		super(existence);
	}
	
	protected Existence6 getExistence(){
		return (Existence6)this.existence;
	}

	public Result giveResult(Experience experience){
		traceEnv();

		Result result = null;
		
		if (experience.equals(this.existence.createOrGetExperience(this.getExistence().LABEL_STEP)))
			result = step();
		else if (experience.equals(this.existence.createOrGetExperience(this.getExistence().LABEL_FEEL)))
			result = feel();
		else if (experience.equals(this.existence.createOrGetExperience(this.getExistence().LABEL_SWAP)))
			result = swap();
		
		return result;
	
	}	
	/**
	 * Step forward
	 * @return true if the agent went up
	 */
	private Result step(){
		Result result = this.existence.createOrGetResult(this.getExistence().LABEL_FALSE);

		if (position < WIDTH -1){
			if (board[position] <= board[position + 1])
				result = this.existence.createOrGetResult(this.getExistence().LABEL_TRUE);
			position++;
		}else 
			position = 0;
		
		return result;
	}
	
	/**
	 * @return true if the next item is greater than the current item
	 */
	private Result feel(){
		Result result = this.existence.createOrGetResult(this.getExistence().LABEL_FALSE);
		
		if ((position < WIDTH -1) && (board[position] <= board[position +1]))
				result = this.existence.createOrGetResult(this.getExistence().LABEL_TRUE);

		return result;		
	}
	
	/**
	 * Invert the next item and the current item
	 * @return true if the next item is greater than the current item
	 */
	private Result swap(){
		Result result = this.existence.createOrGetResult(this.getExistence().LABEL_FALSE);

		int temp = board[position];
		if ((position < WIDTH -1) && (board[position] > board[position +1])){
				board[position] = board[position + 1];
				board[position + 1] = temp;
				result = this.existence.createOrGetResult(this.getExistence().LABEL_TRUE);
		}

		return result;		
	}
	
	private void traceEnv(){
		
		Element e = Trace.addEventElement("environment");

		// print the board
		String stringBoard = "";
		for (int i = 0; i < WIDTH; i++)
			stringBoard += this.board[i] + " ";
		Trace.addSubelement(e,"board", stringBoard);

		// print the agent
		String stringAgent = "";
		for (int i = 0; i < position; i++)
			stringAgent +=".. ";
		stringAgent += ">";
		Trace.addSubelement(e,"agent", stringAgent);
	}
}
