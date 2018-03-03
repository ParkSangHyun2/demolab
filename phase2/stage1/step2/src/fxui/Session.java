package fxui;

public class Session {
	//
	public static String loggedInMemberEmail;
	public static String loggedInMemberName;
	
	public static void putMemberInSession(String email, String name) {
		//
		loggedInMemberEmail= email;
		loggedInMemberName = name;
	}
}
