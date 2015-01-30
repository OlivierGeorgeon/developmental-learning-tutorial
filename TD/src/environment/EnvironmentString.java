package environment;

import org.w3c.dom.*;
import tracer.Trace;
import coupling.Experiment050;
import coupling.interaction.Interaction;
import coupling.interaction.Interaction040;
import existence.Existence060;

/**
 * This class implements the String Environment 
 * used by Georgeon & Hassas in their paper "Single agents can be constructivist too".
 */
public class EnvironmentString extends Environment050{
	
	private Interaction040 stepUp; 
	private Interaction040 feelUp; 
	private Interaction040 swapUp; 
	private Interaction040 stepDown; 
	private Interaction040 feelDown; 
	private Interaction040 swapDown; 
	private Experiment050 step;
	private Experiment050 feel;
	private Experiment050 swap;

	private static final int WIDTH = 20;	
	private int position = 0;
	
	private int clock = 0;
	
	private int[] board = {2, 1, 5, 4, 1, 3, 5, 3, 1, 5, 6, 3, 5, 4, 7, 3, 5, 3, 9, 5};	
	//private int[] board = {6, 3, 5, 4, 7, 3, 5, 3, 1, 5, 6, 3, 5, 4, 7, 3, 5, 3, 9, 5};	
	//private int[] board = {6, 3, 5, 4, 7, 3, 5, 3, 9, 5};	

	public EnvironmentString(Existence060  existence){
		super(existence);
	}
	
	@Override
	protected void init(){
		
		this.stepUp = this.getExistence().addOrGetPrimitiveInteraction(">t", 10); 
		this.feelUp = this.getExistence().addOrGetPrimitiveInteraction("-t", 0);
		this.swapUp = this.getExistence().addOrGetPrimitiveInteraction("it", 0); 
		this.stepDown = this.getExistence().addOrGetPrimitiveInteraction(">f", -10);
		this.feelDown = this.getExistence().addOrGetPrimitiveInteraction("-f", 0); 
		this.swapDown = this.getExistence().addOrGetPrimitiveInteraction("if", 0);		
		this.step = this.getExistence().addOrGetAbstractExperiment(stepUp);
		this.feel = this.getExistence().addOrGetAbstractExperiment(feelUp);
		this.swap = this.getExistence().addOrGetAbstractExperiment(swapUp);
	}

	@Override
	public Interaction enact(Interaction intendedInteraction){

		Interaction040 enactedInteraction = null;
		
		if (intendedInteraction == this.stepUp || intendedInteraction == this.stepDown)
			enactedInteraction = step();
		else if (intendedInteraction == this.feelUp || intendedInteraction == this.feelDown)
			enactedInteraction = feel();
		else if (intendedInteraction == this.swapUp || intendedInteraction == this.swapDown)
			enactedInteraction = swap();
		
		Trace.startNewEvent();
		traceEnv();
		Trace.addEventElement("enacted_interaction", enactedInteraction.getLabel());
		Trace.addEventElement("satisfaction", enactedInteraction.getValence() + "");
		Trace.incTimeCode();
		
		return enactedInteraction;
	
	}	
	/**
	 * Step forward
	 * @return stepUp if the next digit is greater or equal than the current digit, stepDown otherwise.
	 */
	private Interaction040 step(){
		Interaction040 enactedInteraction = this.stepDown;

		if (position < WIDTH -1){
			if (board[position] <= board[position + 1])
				enactedInteraction = this.stepUp;
			position++;
		}else 
			position = 0;
		
		return enactedInteraction;
	}
	
	/**
	 * Feel front
	 * @return feelUp if the next digit is greater or equal than the current digit, feelDown otherwise.
	 */
	private Interaction040 feel(){
		Interaction040 enactedInteraction = this.feelDown;
		
		if ((position < WIDTH -1) && (board[position] <= board[position +1]))
			enactedInteraction = this.feelUp;

		return enactedInteraction;		
	}
	
	/**
	 * Invert the next item and the current item
	 * @return swapUp if the current digit is smaller than the current digit, swapDown otherwise.
	 */
	private Interaction040 swap(){
		Interaction040 enactedInteraction = this.swapDown;

		if ((position < WIDTH -1) ){// && (board[position] > board[position +1])){
			int temp = board[position];
			board[position] = board[position + 1];
			board[position + 1] = temp;
			if (board[position] <= board[position +1])
				enactedInteraction = this.swapUp;
		}

		return enactedInteraction;		
	}
	
	private void traceEnv(){
		
		Trace.addEventElement("clock", this.clock + "");
		this.clock++;

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
