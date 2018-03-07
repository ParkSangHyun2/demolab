package step2.client.transfer.stub.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import step1.share.domain.entity.club.TravelClub;
import step1.share.domain.entity.dto.ClubMembershipDto;
import step1.share.domain.entity.dto.MemberDto;
import step1.share.domain.entity.dto.TravelClubDto;
import step1.share.service.logic.ClubService;
import step1.share.util.RequestMessage;
import step1.share.util.ResponseMessage;
import step2.client.transfer.SocketDispatcher;

public class ClubServiceStub implements ClubService {
	//
	private SocketDispatcher dispatcher;
	private String serviceName;

	public ClubServiceStub() {
		//
		serviceName = (this.getClass().getInterfaces())[0].getSimpleName();
	}

	@Override
	public void registerClub(TravelClubDto club) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("registerClub", (new Gson()).toJson(club),
				"TravelClubDto");
		ResponseMessage response = null;

		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		dispatcher.close();
	}

	@Override
	public TravelClubDto findClub(String clubId) {
		//
		RequestMessage requestMessage =
				createRequestMessage("findClub", clubId, "String");
		ResponseMessage response = null;

		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

		dispatcher.close();

		return (new Gson()).fromJson(response.getValue(), TravelClubDto.class);
	}

	@Override
	public TravelClubDto findClubByName(String name) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("findClubByName", name, "String");
		ResponseMessage response = null;

		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

		dispatcher.close();

		return (new Gson()).fromJson(response.getValue(), TravelClubDto.class);
	}

	@Override
	public void modify(TravelClubDto club) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("modify", (new Gson()).toJson(club), "TravelClubDto");

		try {
			dispatcher.dispatchVoid(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

		dispatcher.close();
	}

	@Override
	public void remove(String clubId) {
		//
		RequestMessage requestMessage =
				createRequestMessage("remove", clubId, "String");
		
		try {
			dispatcher.dispatchVoid(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}

		dispatcher.close();
	}

	@Override
	public void addMembership(ClubMembershipDto membershipDto) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("addMembership", (new Gson()).toJson(membershipDto),
				"ClubMembershipDto");
		ResponseMessage response = null;

		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dispatcher.close();
	}

	@Override
	public ClubMembershipDto findMembershipIn(String clubId, String memberId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("findMembershipIn", clubId, memberId, "String", "String");
		ResponseMessage response = null;

		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dispatcher.close();
		return (new Gson()).fromJson(response.getValue(), ClubMembershipDto.class);
	}

	@Override
	public List<ClubMembershipDto> findAllMembershipsIn(String clubId) {
		//
		RequestMessage requestMessage =
				createRequestMessage("findAllMembershipsIn", clubId, "String");
		ResponseMessage response = null;

		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}

		dispatcher.close();
		return (new Gson()).fromJson(response.getValue(), new TypeToken<List<ClubMembershipDto>>() {}.getType());
	}

	@Override
	public void modifyMembership(String clubId, ClubMembershipDto member) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("modifyMembership", clubId, (new Gson()).toJson(member),
				"String", "ClubMembershipDto");

		try {
			dispatcher.dispatchVoid(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dispatcher.close();
	}

	@Override
	public void removeMembership(String clubId, String memberId) {
		//
		RequestMessage requestMessage = 
				createRequestMessage("removeMembership", clubId, memberId, "String", "String");

		try {
			dispatcher.dispatchVoid(requestMessage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dispatcher.close();
	}

	@Override
	public List<TravelClubDto> findAll() {
		//
		RequestMessage requestMessage = 
				createRequestMessage("findAll", null, null);

		ResponseMessage response = null;

		try {
			response = dispatcher.dispatchReturn(requestMessage);
		} catch (IOException e) {
			//
			e.printStackTrace();
		}
		dispatcher.close();
		ArrayList<TravelClubDto> foundList = 
				(new Gson()).fromJson(response.getValue(), new TypeToken<List<TravelClubDto>>() {}.getType());
		System.out.println(foundList);
		return (ArrayList<TravelClubDto>) foundList;
	}

	private RequestMessage createRequestMessage(String operation, String parameter, String remark) {
		//
		this.dispatcher = getDispatcher();
		RequestMessage requestMessage = new RequestMessage(operation, parameter);
		requestMessage.setServiceName(serviceName);
		requestMessage.setRemark(remark);
		return requestMessage;
	}

	private RequestMessage createRequestMessage(String operation, String parameter1, String parameter2,
			String remark1, String remark2) {
		//
		this.dispatcher = getDispatcher();
		RequestMessage requestMessage = new RequestMessage(operation, parameter1, parameter2);
		requestMessage.setServiceName(serviceName);
		requestMessage.setRemarks(remark1, remark2);
		return requestMessage;
	}

	private SocketDispatcher getDispatcher() {
		//
		return SocketDispatcher.getInstance("127.0.0.1", 9999);
	}

	@Override
	public List<TravelClub> findAllClub() {
		//
		List<TravelClub> travelClubs = new ArrayList<>();
		for(TravelClubDto club : this.findAll()) {
			travelClubs.add(club.toTravelClub());
		}
		return travelClubs;
	}

}
