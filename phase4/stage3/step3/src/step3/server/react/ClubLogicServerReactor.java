package step3.server.react;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import step1.share.util.ReactFailException;

public class ClubLogicServerReactor extends Thread {
	//
	private int servicePort;
	private ServerSocket serverSocket;
	private ExecutorService pool;

	public ClubLogicServerReactor() {
		//
		this.servicePort = 9999;
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
			Socket clientSocket = null;
			
			try {
				synchronized (serverSocket) {
					System.out.println("Connecting..");
					clientSocket = serverSocket.accept();
					//clientSocket.setSoTimeout(10000);
				}
				
				pool.execute(new EventRouter(clientSocket));
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
