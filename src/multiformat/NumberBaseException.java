package multiformat;

/*
 * A class to show exception messages
 * @author Chakir Bouziane
 * @version 1
 */
public class NumberBaseException extends Exception {
	/*
	 * Show the exception
	 * 
	 * @param msg The message of the exception
	 */
  public NumberBaseException(String msg) {
    super(msg);
  }
}