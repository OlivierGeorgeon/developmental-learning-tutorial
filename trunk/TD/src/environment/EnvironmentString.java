package environment;

import org.w3c.dom.*;
import tracer.Trace;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction040;
import existence.Existence050;

/**
 * This class implements the String Environment 
 * used by Georgeon & Hassas in their paper "Single agents can be constructivist too".
 */
public class EnvironmentString extends Environment050{
	
	public final String LABEL_STEP = ">";
	public final String LABEL_FEEL = "-";
	public final String LABEL_SWAP = "i";
	public final String LABEL_TRUE = "t";
	public final String LABEL_FALSE = "f";

	private static final int WIDTH = 20;	
	private int position = 0;
	
	private int[] board = {6, 3, 5, 4, 7, 3, 5, 3, 1, 5, 6, 3, 5, 4, 7, 3, 5, 3, 9, 5};	
	//private int[] board = {6, 3, 5, 4, 7, 3, 5, 3, 9, 5};	

	public EnvironmentString(Existence050  existence){
		super(existence);
	}
	
	@Override
	public Interaction enact(Interaction intendedInteraction){
		traceEnv();

		Interaction040 enactedInteraction = null;
		
		if (intendedInteraction == this.getExistence().addOrGetPrimitiveInteraction(">t",0))
			enactedInteraction = step();
		else if (intendedInteraction == this.getExistence().addOrGetPrimitiveInteraction("-t",0))
			enactedInteraction = feel();
		else if (intendedInteraction == this.getExistence().addOrGetPrimitiveInteraction("it",0))
			enactedInteraction = swap();
		
		return enactedInteraction;
	
	}	
	/**
	 * Step forward
	 * @return true if the agent went up
	 */
	private Interaction040 step(){
		Interaction040 enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction(">f",0);

		if (position < WIDTH -1){
			if (board[position] <= board[position + 1])
				enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction(">t",0);
			position++;
		}else 
			position = 0;
		
		return enactedInteraction;
	}
	
	/**
	 * @return true if the next item is greater than the current item
	 */
	private Interaction040 feel(){
		Interaction040 enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction("-f",0);
		
		if ((position < WIDTH -1) && (board[position] <= board[position +1]))
			enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction(">t",0);

		return enactedInteraction;		
	}
	
	/**
	 * Invert the next item and the current item
	 * @return true if the next item is greater than the current item
	 */
	private Interaction040 swap(){
		Interaction040 enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction("if",0);

		int temp = board[position];
		if ((position < WIDTH -1) && (board[position] > board[position +1])){
				board[position] = board[position + 1];
				board[position + 1] = temp;
				enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction("it",0);
		}

		return enactedInteraction;		
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
