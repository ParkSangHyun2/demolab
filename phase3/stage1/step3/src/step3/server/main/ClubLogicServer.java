package step3.server.main;

import step3.server.react.ClubLogicServerReactor;

public class ClubLogicServer {
	//
	public static void main(String[] args) {
		//
		startServer();
	}
	
	private static void startServer() {
		//
		ClubLogicServerReactor reactor = new ClubLogicServerReactor();
		reactor.start();
		System.out.println("Logic Server is started..");
	}
}
