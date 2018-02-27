package fxui;

import java.util.LinkedHashMap;
import java.util.Map;

public class Session {
	//
	public static Map<String, String> loggedInMember = new LinkedHashMap<String,String>();;
	
	public static void putMemberInSession(Map<String, String> member) {
		//
		loggedInMember = member; 
	}
}
