package fxui;



/**
 * The class <code>SessionFactory</code> implements static methods that return instances of the class <code>{@link Session}</code>.
 *
 * @generatedBy CodePro at 18. 3. 8 오후 4:16
 * @author parksanghyun
 * @version $Revision: 1.0 $
 */
public class SessionFactory
 {
	/**
	 * Prevent creation of instances of this class.
	 *
	 * @generatedBy CodePro at 18. 3. 8 오후 4:16
	 */
	private SessionFactory() {
	}


	/**
	 * Create an instance of the class <code>{@link Session}</code>.
	 *
	 * @generatedBy CodePro at 18. 3. 8 오후 4:16
	 */
	public static Session createSession() {
		return new Session();
	}
}