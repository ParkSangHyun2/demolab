package step3.server.react;

import java.io.IOException;
import java.net.Socket;

import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step1.share.util.SocketWorker;
import step3.server.react.handler.BoardServiceHandler;
import step3.server.react.handler.ClubServiceHandler;
import step3.server.react.handler.MemberServiceHandler;
import step3.server.react.handler.PostingServiceHandler;
import step3.server.react.handler.ServiceHandler;

public class EventRouter implements Runnable{
	//
	private SocketWorker socketWorker;
	
	public EventRouter(Socket socket) {
		//
		this.socketWorker = new SocketWorker(socket);
	}
	
	public void route() {
		//
		String json = socketWorker.readMessage();
		System.out.println("json : " + json);
		RequestMessage request = RequestMessage.fromJson(json);
		
		String serviceName = request.getServiceName();
		
		ServiceHandler serviceHandler = null;
		System.out.println("ServiceType : " + serviceName);
		
		switch(serviceName) {
		case "BoardService":
			serviceHandler = new BoardServiceHandler();
			break;
			
		case "ClubService":
			serviceHandler = new ClubServiceHandler();
			break;
			
		case "MemberService":
			serviceHandler = new MemberServiceHandler();
			break;
			
		case "PostingService":
			serviceHandler = new PostingServiceHandler();
			break;
		}
		
		ResponseMessage response = serviceHandler.handle(request);
		try {
			socketWorker.writeMessage(response.toJson());
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		socketWorker.closeSocket();
	}

	@Override
	public void run() {
		//
		route();
	}
}
