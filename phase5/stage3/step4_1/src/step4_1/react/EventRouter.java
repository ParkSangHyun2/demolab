package step4_1.react;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;

import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step1.share.util.SocketWorker;
import step4_1.react.handler.BoardStoreHandler;
import step4_1.react.handler.ClubStoreHandler;
import step4_1.react.handler.MemberStoreHandler;
import step4_1.react.handler.PostingStoreHandler;
import step4_1.react.handler.StoreHandler;

public class EventRouter implements Runnable {
	//
	private SocketWorker socketWorker;

	public EventRouter(Socket logicSocket) {
		//
		this.socketWorker = new SocketWorker(logicSocket);
	}
	
	public void route() {
		//
		String json = socketWorker.readMessage();
		
		RequestMessage request = RequestMessage.fromJson(json);
		
		String serviceName = request.getServiceName();
		
		StoreHandler storeHandler = null;
		
		switch(serviceName) {
		case "BoardStore":
			storeHandler = new BoardStoreHandler<Object>();
			break;
		case "ClubStore":
			storeHandler = new ClubStoreHandler<Object>();
			break;
		case "MemberStore":
			storeHandler = new MemberStoreHandler<Object>();
			break;
		case "PostingStore":
			storeHandler = new PostingStoreHandler<Object>();
			break;
		}
		
		ResponseMessage resultJson = storeHandler.handle(request);
		
		try {
			socketWorker.writeMessage((new Gson()).toJson(resultJson));
		} catch (IOException e) {
			//
			System.out.println("Event Router --> " + e.getMessage());
		}
	}

	@Override
	public void run() {
		//
		route();
	}

}
