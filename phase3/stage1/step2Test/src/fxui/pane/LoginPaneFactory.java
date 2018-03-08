package fxui.pane;

import javafx.scene.layout.BorderPane;


/**
 * The class <code>LoginPaneFactory</code> implements static methods that return instances of the class <code>{@link LoginPane}</code>.
 *
 * @generatedBy CodePro at 18. 3. 8 오후 4:17
 * @author parksanghyun
 * @version $Revision: 1.0 $
 */
public class LoginPaneFactory
 {
	/**
	 * Prevent creation of instances of this class.
	 *
	 * @generatedBy CodePro at 18. 3. 8 오후 4:17
	 */
	private LoginPaneFactory() {
	}


	/**
	 * Create an instance of the class <code>{@link LoginPane}</code>.
	 *
	 * @generatedBy CodePro at 18. 3. 8 오후 4:17
	 */
	public static LoginPane createLoginPane() {
		return new LoginPane(new BorderPane());
	}
}