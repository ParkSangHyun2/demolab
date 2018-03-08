package fxui;

import javafx.scene.layout.BorderPane;


/**
 * The class <code>PrimarySceneFactory</code> implements static methods that return instances of the class <code>{@link PrimaryScene}</code>.
 *
 * @generatedBy CodePro at 18. 3. 8 오후 4:16
 * @author parksanghyun
 * @version $Revision: 1.0 $
 */
public class PrimarySceneFactory
 {
	/**
	 * Prevent creation of instances of this class.
	 *
	 * @generatedBy CodePro at 18. 3. 8 오후 4:16
	 */
	private PrimarySceneFactory() {
	}


	/**
	 * Create an instance of the class <code>{@link PrimaryScene}</code>.
	 *
	 * @generatedBy CodePro at 18. 3. 8 오후 4:16
	 */
	public static PrimaryScene createPrimaryScene() {
		return new PrimaryScene(new BorderPane());
	}
}