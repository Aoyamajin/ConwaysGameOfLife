package john.conway;

import java.io.IOException;

/**
 * Class to initialize a run of Conway's Game of Life.
 * @author johburli
 *
 */
public class ConwaysGameOfLife 
{	
	public static void main(String[] args) 
	{
		ConwaysGameOfLifeProperties conwaysGameOfLifeProperties = ConwaysGameOfLifeProperties.getConwaysGameOfLifePropertiesInstance();
		
		UpdateConwaysGrid updateConwaysGrid = new UpdateConwaysGrid(conwaysGameOfLifeProperties);
		try {
			updateConwaysGrid.executeUpdates();
		} catch (IOException e) {
			System.err.println("Failed to execute updates to Conways Grid: " + e.getMessage());
		}
	}
}
