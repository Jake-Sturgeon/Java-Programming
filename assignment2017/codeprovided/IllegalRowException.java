package assignment2017.codeprovided;

@SuppressWarnings("serial")
public class IllegalRowException extends RuntimeException {

	/**
	 * Constructs a new IllegalRowException
	 * @param row the illegal row number that was supplied
	 */
	public IllegalRowException(int row) {
		super("Row "+row+" is an illegal row number");		
	}	

}
