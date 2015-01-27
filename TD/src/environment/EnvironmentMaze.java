package environment;

import coupling.interaction.Interaction;
import coupling.interaction.Interaction040;
import existence.Existence050;

/**
 * This class implements the Small Loop Environment
 *  
 * The Small Loop Problem: A challenge for artificial emergent cognition. 
 * Olivier L. Georgeon, James B. Marshall. 
 * BICA2012, Annual Conference on Biologically Inspired Cognitive Architectures. 
 * Palermo, Italy. (October 31, 2012).
 * http://e-ernest.blogspot.fr/2012/05/challenge-emergent-cognition.html
 */
public class EnvironmentMaze extends Environment050 
{
	private static final int ORIENTATION_UP    = 0;
	private static final int ORIENTATION_RIGHT = 1;
	private static final int ORIENTATION_DOWN  = 2;
	private static final int ORIENTATION_LEFT  = 3;
	
	// The Small Loop Environment
	
	private static final int WIDTH = 6;	
	private static final int HEIGHT = 6;	
	private int m_x = 4;
	private int m_y = 1;
	private int m_o = 2;
	
	private char[][] m_board = 
		{
		 {'x', 'x', 'x', 'x', 'x', 'x'},
		 {'x', ' ', ' ', ' ', ' ', 'x'},
		 {'x', ' ', 'x', 'x', ' ', 'x'},
		 {'x', ' ', ' ', 'x', ' ', 'x'},
		 {'x', 'x', ' ', ' ', ' ', 'x'},
		 {'x', 'x', 'x', 'x', 'x', 'x'},
		};
	
	private char[] m_agent = 
	{ '^', '>', 'v', '<' };

	public EnvironmentMaze(Existence050  existence){
		super(existence);
	}

	@Override
	protected void init(){
		
		//Settings for a nice demo in the Simple Maze 
		Interaction040 turnLeft = this.getExistence().addOrGetPrimitiveInteraction("^t", -3); // Left toward empty
		Interaction040 turnRight = this.getExistence().addOrGetPrimitiveInteraction("vt", -3); // Right toward empty
		Interaction040 touchRight = this.getExistence().addOrGetPrimitiveInteraction("\\t", -1); // Touch right wall
		this.getExistence().addOrGetPrimitiveInteraction("\\f", -1); // Touch right empty
		Interaction040 touchLeft = this.getExistence().addOrGetPrimitiveInteraction("/t", -1); // Touch left wall
		this.getExistence().addOrGetPrimitiveInteraction("/f", -1); // Touch left empty
		Interaction040 froward = this.getExistence().addOrGetPrimitiveInteraction(">t",  5); // Move
		this.getExistence().addOrGetPrimitiveInteraction(">f", -10); // Bump		
		Interaction040 touchForward = this.getExistence().addOrGetPrimitiveInteraction("-t", -1); // Touch wall
		this.getExistence().addOrGetPrimitiveInteraction("-f", -1); // Touch empty
		this.getExistence().addOrGetAbstractExperiment(turnLeft);
		this.getExistence().addOrGetAbstractExperiment(turnRight);
		this.getExistence().addOrGetAbstractExperiment(touchRight);
		this.getExistence().addOrGetAbstractExperiment(touchLeft);
		this.getExistence().addOrGetAbstractExperiment(froward);
		this.getExistence().addOrGetAbstractExperiment(touchForward);
	}

	public Interaction enact(Interaction intendedInteraction) 
	{
		Interaction040 enactedInteraction = null;

		if (intendedInteraction.getLabel().substring(0,1).equals(">"))
			enactedInteraction = move();
		else if (intendedInteraction.getLabel().substring(0,1).equals("^"))
			enactedInteraction = left();
		else if (intendedInteraction.getLabel().substring(0,1).equals("v"))
			enactedInteraction = right();
		else if (intendedInteraction.getLabel().substring(0,1).equals("-"))
			enactedInteraction = Touch();
		else if (intendedInteraction.getLabel().substring(0,1).equals("\\"))
			enactedInteraction = TouchRight();
		else if (intendedInteraction.getLabel().substring(0,1).equals("/"))
			enactedInteraction = TouchLeft();
		
		// print the maze
		for (int i = 0; i < HEIGHT; i++)
		{
			for (int j = 0; j < WIDTH; j++)
			{
				if (i == m_y && j== m_x)
					System.out.print(m_agent[m_o]);
				else
					System.out.print(m_board[i][j]);	
			}
			System.out.println();
		}
		
		return enactedInteraction;
	}

	/**
	 * Turn to the right. 
	 */
	private Interaction040 right()
	{		
		m_o++;		
		if (m_o > ORIENTATION_LEFT)
			m_o = ORIENTATION_UP;

		return this.getExistence().addOrGetPrimitiveInteraction("vt",0);
	}
	
	/**
	 * Turn to the left. 
	 */
	private Interaction040 left()
	{
		m_o--;
		if (m_o < 0)
			m_o = ORIENTATION_LEFT;
		
		return this.getExistence().addOrGetPrimitiveInteraction("^t",0);
	}
	
	/**
	 * Move forward to the direction of the current orientation.
	 */
	private Interaction040 move()
	{
		Interaction040 enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction(">f",0);	

		if ((m_o == ORIENTATION_UP) && (m_y > 0) && (m_board[m_y - 1][m_x] == ' ' )){
			m_y--; 
			enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction(">t",0);
		}

		if ((m_o == ORIENTATION_DOWN) && (m_y < HEIGHT) && (m_board[m_y + 1][m_x] == ' ' )){
			m_y++; 
			enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction(">t",0);	
		}

		if ((m_o == ORIENTATION_RIGHT) && ( m_x < WIDTH ) && (m_board[m_y][m_x + 1] == ' ' )){
			m_x++; 
			enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction(">t",0);
		}
		if ((m_o == ORIENTATION_LEFT) && ( m_x > 0 ) && (m_board[m_y][m_x - 1] == ' ' )){
			m_x--; 
			enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction(">t",0);
		}

		return enactedInteraction;
	}
	
	/**
	 * Touch the square forward.
	 * Succeeds if there is a wall, fails otherwise 
	 */
	private Interaction040 Touch()
	{
		Interaction040 enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction("-t",0);	

		if (((m_o == ORIENTATION_UP) && (m_y > 0) && (m_board[m_y - 1][m_x] == ' ')) ||
			((m_o == ORIENTATION_DOWN) && (m_y < HEIGHT) && (m_board[m_y + 1][m_x] == ' ')) ||
			((m_o == ORIENTATION_RIGHT) && (m_x < WIDTH) && (m_board[m_y][m_x + 1] == ' ')) ||
			((m_o == ORIENTATION_LEFT) && (m_x > 0) && (m_board[m_y][m_x - 1] == ' ')))
		   	{
			enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction("-f",0);	}

		return enactedInteraction;
	}
	
	/**
	 * Touch the square to the right.
	 * Succeeds if there is a wall, fails otherwise. 
	 */
	private Interaction040 TouchRight()
	{
		Interaction040 enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction("\\t",0);	

		if (((m_o == ORIENTATION_UP) && (m_x > 0) && (m_board[m_y][m_x + 1] == ' ')) ||
			((m_o == ORIENTATION_DOWN) && (m_x < WIDTH) && (m_board[m_y][m_x - 1] == ' ')) ||
			((m_o == ORIENTATION_RIGHT) && (m_y < HEIGHT) && (m_board[m_y + 1][m_x] == ' ')) ||
			((m_o == ORIENTATION_LEFT) && (m_y > 0) && (m_board[m_y - 1][m_x] == ' ')))
			{enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction("\\f",0);
			}

		return enactedInteraction;
	}

	/**
	 * Touch the square forward.
	 * Succeeds if there is a wall, fails otherwise 
	 */
	private Interaction040 TouchLeft()
	{
		Interaction040 enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction("/t",0);	
	
		if (((m_o == ORIENTATION_UP) && (m_x > 0) && (m_board[m_y][m_x - 1] == ' ')) ||
			((m_o == ORIENTATION_DOWN) && (m_x < WIDTH) && (m_board[m_y][m_x + 1] == ' ')) ||
			((m_o == ORIENTATION_RIGHT) && (m_y > 0) && (m_board[m_y - 1][m_x] == ' ')) ||
			((m_o == ORIENTATION_LEFT) && (m_y < HEIGHT) && (m_board[m_y + 1][m_x] == ' ')))
			{enactedInteraction = this.getExistence().addOrGetPrimitiveInteraction("/f",0);}

		return enactedInteraction;
	}
}
