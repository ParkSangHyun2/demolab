package step4_2.react;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import step1.share.util.ReactFailException;

public class ClubStoreServerReactor extends Thread{
	//
	private int servicePort;
	private ServerSocket serverSocket;
	private ExecutorService pool;
	
	public ClubStoreServerReactor() {
		//
		this.servicePort = 8888;
		pool = Executors.newCachedThreadPool();
	}
	
	private void initServerSocket() {
		//
		try {
			serverSocket = new ServerSocket(servicePort);
		} catch (IOException e) {
			throw new ReactFailException(e.getMessage());
		}
	}
	
	public void run() {
		//
		this.initServerSocket();
		while(true) {
			//
			Socket logicSocket = new Socket();
			
			try {
				synchronized (logicSocket) {
					System.out.println("Connecting...");
					logicSocket = serverSocket.accept();
				}
				
				pool.execute(new EventRouter(logicSocket));
			} catch (IOException e) {
				System.out.println("Server Message -->" + e.getMessage());
				continue;
			}
		}
	}
}
