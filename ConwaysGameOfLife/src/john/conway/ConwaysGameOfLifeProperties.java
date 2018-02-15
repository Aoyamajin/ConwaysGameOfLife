package john.conway;

import java.util.ResourceBundle;

/**
 * A class to get the properties for a run of Conway's Game of Life.
 * @author johburli
 *
 */
public class ConwaysGameOfLifeProperties {
	private static final String RESOURCE_BUNDLE_NAME = "conways_game_of_life";
	private static final String COMMA_DELIMITER = ",";

	private static ConwaysGameOfLifeProperties conwaysGameOfLifeProperties;

	private String[][] conwaysMatrix;
	private int rowNumber;
	private int columnNumber;
	private int numberOfIterations;
	private String aliveValue;
	private String deadValue;

	private ConwaysGameOfLifeProperties() {
		ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME);
		String[] conwaysMatrixOneDimension = bundle.getString("conways.matrix").split(COMMA_DELIMITER);
		rowNumber = Integer.parseInt(bundle.getString("conways.rows"));
		columnNumber = Integer.parseInt(bundle.getString("conways.columns"));
		numberOfIterations = Integer.parseInt(bundle.getString("number.of.iterations"));
		aliveValue = bundle.getString("alive.value");
		deadValue = bundle.getString("dead.value");
		buildConwaysGrid(conwaysMatrixOneDimension);
	}

	public static ConwaysGameOfLifeProperties getConwaysGameOfLifePropertiesInstance() {
		if (conwaysGameOfLifeProperties == null) {
			conwaysGameOfLifeProperties = new ConwaysGameOfLifeProperties();
		}

		return conwaysGameOfLifeProperties;
	}
	
	/**
	 * Helper method to build a 2-dimension array from a one dimension array.
	 * @param conwaysMatrixOneDimension - A 1-dimension String array.
	 */
	private void buildConwaysGrid(String[] conwaysMatrixOneDimension) {
		conwaysMatrix = new String[rowNumber][columnNumber];

		for (int rowIndex = 0; rowIndex < rowNumber; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnNumber; columnIndex++) {
				conwaysMatrix[rowIndex][columnIndex] = conwaysMatrixOneDimension[rowIndex * columnNumber + columnIndex];
			}
		}
	}

	public String[][] getConwaysMatrix() {
		return conwaysMatrix;
	}

	public void setConwaysMatrix(String[][] conwaysMatrix) {
		this.conwaysMatrix = conwaysMatrix;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}
	
	public int getNumberOfIterations() {
		return numberOfIterations;
	}

	public void setNumberOfIterations(int numberOfIterations) {
		this.numberOfIterations = numberOfIterations;
	}

	public String getAliveValue() {
		return aliveValue;
	}

	public void setAliveValue(String aliveValue) {
		this.aliveValue = aliveValue;
	}

	public String getDeadValue() {
		return deadValue;
	}

	public void setDeadValue(String deadValue) {
		this.deadValue = deadValue;
	}
}
