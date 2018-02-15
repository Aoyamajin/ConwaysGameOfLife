package john.conway;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class containing all the logic to update a 2D array using Conway's rules.
 * @author johburli
 *
 */
public class UpdateConwaysGrid {
	private static final String RESULTS_DIRECTORY = "results";
	private static final String RESULTS_FILE_NAME = "conways_game_of_life_results_";
	private static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmssSSS";
    
	private ConwaysGameOfLifeProperties conwaysGameOfLifeProperties;
	private String[][] conwaysInitialArray;
	private String[][] conwaysFutureArray;

	public UpdateConwaysGrid(ConwaysGameOfLifeProperties conwaysGameOfLifeProperties) {
		this.conwaysGameOfLifeProperties = conwaysGameOfLifeProperties;
	}
	
	/**
	 * Method to execute Conway's Game of Life updates and send them to a results file.
	 * @throws IOException
	 */
	public void executeUpdates() throws IOException {
		conwaysInitialArray = conwaysGameOfLifeProperties.getConwaysMatrix();
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("Initial Setup:\n");
		printResults(stringBuilder, conwaysInitialArray);
		
		for (int iteration = 1; iteration <= conwaysGameOfLifeProperties.getNumberOfIterations(); iteration++) {
			conwaysFutureArray = new String[conwaysGameOfLifeProperties.getRowNumber()][conwaysGameOfLifeProperties
					.getColumnNumber()];
			updateConwaysArray();
			stringBuilder.append("Printing Iteration#").append(iteration).append("\n");
			printResults(stringBuilder, conwaysFutureArray);
			conwaysInitialArray = conwaysFutureArray;
		}

		createResultsFile(stringBuilder.toString());
	}
	
	/**
	 * Loops through the initial array and adds values to the future array accordingly.
	 */
	private void updateConwaysArray() {
		for (int rowIndex = 0; rowIndex < conwaysGameOfLifeProperties.getRowNumber(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < conwaysGameOfLifeProperties.getColumnNumber(); columnIndex++) {
				int livingNeighbors = getLivingNeighbors(rowIndex, columnIndex);
				conwaysFutureArray[rowIndex][columnIndex] = updateValueUsingRules(conwaysInitialArray[rowIndex][columnIndex], livingNeighbors);
			}
		}
	}
	
	/**
	 * Looks around the cell to retrieve the number of living neighbors it has.
	 * @param rowIndex - The cell's row index in Conway's grid.
	 * @param columnIndex - The cell's column index in Conway's grid.
	 * @return The number of living neighbors the cell in question has.
	 */
	private int getLivingNeighbors(int rowIndex, int columnIndex) {
		int livingNeighbors = 0; // subtracting the individual in question, so
									// -1
		for (int neighborRowIndex = -1; neighborRowIndex <= 1; neighborRowIndex++) {
			int newRowIndex = rowIndex + neighborRowIndex;

			for (int neighborColumnIndex = -1; neighborColumnIndex <= 1; neighborColumnIndex++) {
				int newColumnIndex = columnIndex + neighborColumnIndex;

				if ( // prevent the dreaded IndexOutOfBounds exception
				newRowIndex >= 0 && newRowIndex < conwaysGameOfLifeProperties.getRowNumber() && newColumnIndex >= 0
						&& newColumnIndex < conwaysGameOfLifeProperties.getColumnNumber() &&
						// make sure you don't count the individual whose
						// neighbors you are trying to find
						(neighborRowIndex != 0 || neighborColumnIndex != 0) &&
						// no need for a NullPointerException
						conwaysInitialArray[newRowIndex][newColumnIndex] != null &&
						// for potential future iterations check for my "Alive"
						// text
						(conwaysGameOfLifeProperties.getAliveValue()
								.equals(conwaysInitialArray[newRowIndex][newColumnIndex]))) {
					livingNeighbors++;
				}
			}
		}

		return livingNeighbors;
	}
	
	/**
	 * Gets what the new value of that cell should be based upon how many living neighbors it has.
	 * @param originalValue - The cell's original value.
	 * @param livingNeighbors - The number of living neighbors the cell has.
	 * @return
	 */
	private String updateValueUsingRules(String originalValue, int livingNeighbors) {
		// if equal to 3, it's alive no matter what
		if (livingNeighbors == 3) {
			return conwaysGameOfLifeProperties.getAliveValue();
		}
		// if it's 2, keep the original value
		else if (livingNeighbors == 2) {
			return originalValue;
		}
		// otherwise it's dead of either over-population or under-population
		else {
			return conwaysGameOfLifeProperties.getDeadValue();
		}
	}
	
	/**
	 * Print Conway's matrix in a somewhat formatted way.
	 * @param stringBuilder
	 * @param matrixToPrint
	 */
	private void printResults(StringBuilder stringBuilder, String[][] matrixToPrint) {
		if (matrixToPrint == null) {
			stringBuilder.append("It's the apocalypse.  Only oblivion is left.");
		} else {
			for (int rowIndex = 0; rowIndex < matrixToPrint.length; rowIndex++) {
				for (int columnIndex = 0; columnIndex < matrixToPrint[0].length; columnIndex++) {
					stringBuilder.append("\t").append(matrixToPrint[rowIndex][columnIndex]).append(" ");
				}
				stringBuilder.append("\n\n");
			}
		}
	}

	private void createResultsFile(String resultsToWrite) throws IOException {
		File directory = new File(RESULTS_DIRECTORY);
		directory.mkdirs();
		File resultsFile = new File(directory, String.format(RESULTS_FILE_NAME, getDateAsString()));
		resultsFile.createNewFile();
		Files.write(resultsFile.toPath(), resultsToWrite.getBytes());
	}
	
    public static String getDateAsString()
    {
        SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT);
        return sdf.format(new Date());
    }
}
