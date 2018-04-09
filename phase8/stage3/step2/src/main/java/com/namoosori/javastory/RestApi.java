package com.namoosori.javastory;

public class RestApi {
	//
	private final static String URL = "http://127.0.0.1:8090/";
	
	public static final String CLUB_SEARCH_ALL_API = URL+"club/search/all";
	public static final String CLUB_SEARCH_MY_API = URL+"club/search/myclub";
	public static final String CLUB_CREATE_API = URL+"club/create";
	public static final String CLUB_DELETE_API = URL+"club/withdrawal/";
	public static final String CLUB_JOIN_API = URL+"club/join/";
	public static final String MEMBER_SIGNUP_API = URL+"member/signup";
	public static final String MEMBER_SIGNIN_API = URL+"member/signin";
	public static final String MEMBER_PROFILE_API = URL+"member/profile";
	public static final String MEMBER_PROFILE_MODIFY_API = URL+"member/profile/modify";
	public static final String POSTING_LIST_API = URL+"posting/list";
	public static final String POSTING_CREATE_API = URL+"posting/create";
	public static final String POSTING_LOAD_API = URL+"posting/usid/";
	public static final String POSTING_DELETE_API = URL+"posting/delete/";
	public static final String POSTING_MODIFY_API = URL+"posting/modify";
}
