package fxui.pane;

import entity.dto.TravelClubDto;


/**
 * The class <code>BoardPaneFactory</code> implements static methods that return instances of the class <code>{@link BoardPane}</code>.
 *
 * @generatedBy CodePro at 18. 3. 8 오후 4:17
 * @author parksanghyun
 * @version $Revision: 1.0 $
 */
public class BoardPaneFactory
 {
	/**
	 * Prevent creation of instances of this class.
	 *
	 * @generatedBy CodePro at 18. 3. 8 오후 4:17
	 */
	private BoardPaneFactory() {
	}


	/**
	 * Create an instance of the class <code>{@link BoardPane}</code>.
	 *
	 * @generatedBy CodePro at 18. 3. 8 오후 4:17
	 */
	public static BoardPane createBoardPane() {
		return new BoardPane(new TravelClubDto("", ""));
	}
}