package step4_3.main;

import step4_3.react.ClubStoreServerReactor;

public class ClubStoreServer {
	//
	public static void main(String[] args) {
		//
		startServer();
	}
	
	private static void startServer() {
		//
		ClubStoreServerReactor reactor = new ClubStoreServerReactor();
		reactor.start();
		System.out.println("Store Server is started..");
	}
}
