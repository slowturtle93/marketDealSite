package com.market.server.utils;

import javax.servlet.http.HttpSession;

public class SessionUtil {
	
	private static final String LOGIN_USER_ID  = "LOGIN_USER_ID";
	private static final String LOGIN_USER_NO  = "LOGIN_USER_NO";
	private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";
	private static final String LOGIN_ADMIN_NO = "LOGIN_ADMIN_NO";
	
	// 인스턴스화 방지
	private SessionUtil() {}
	
	/******************************* 사용자 session 영역 **************************************/
	
	/**
	 * 로그인 한 회원정보를 session에 저장한다.
	 * 
	 * @param session 사용자 session
	 * @param loginId 로그인한 사용자의 ID
	 */
	public static void setLoginUserInfo(HttpSession session, int loginNo, String loginId) {
		session.setAttribute(LOGIN_USER_NO, loginNo);
		session.setAttribute(LOGIN_USER_ID, loginId);
	}
	
	
	/**
	 * 고객 로그인 정보를 session에서 삭제한다.
	 * 
	 * @param session
	 */
	public static void logoutUserInfo(HttpSession session) {
		session.removeAttribute(LOGIN_USER_NO);
		session.removeAttribute(LOGIN_USER_ID);
	}
	
	/**
	 * 로그인한 고객의 로그인넘버를 세션에서 꺼낸다.
	 * 
	 * @param session
	 * @return
	 */
	public static String getLoginUserId(HttpSession session) {
		return (String) session.getAttribute(LOGIN_USER_NO);
	}
	
	/**
	 * 로그인한 회원의 로그인ID를 세션에서 꺼낸다.
	 * 
	 * @param session
	 * @return
	 */
	public static int getLoginUserNo(HttpSession session) {
		return (int) session.getAttribute(LOGIN_USER_ID);
	}
	
	
	/******************************* 관리자 session 영역 **************************************/
	
	/**
	 * 로그인 한 관리자정보를 session에 저장한다.
	 * 
	 * @param session 사용자 session
	 * @param loginId 로그인한 사용자의 ID
	 */
	public static void setLoginAdminInfo(HttpSession session, String loginNo, String loginId) {
		session.setAttribute(LOGIN_ADMIN_NO, loginNo);
		session.setAttribute(LOGIN_ADMIN_ID, loginId);
	}
	
	
	/**
	 * 관리자 정보를 session에서 삭제한다.
	 * 
	 * @param session
	 */
	public static void logoutAdminInfo(HttpSession session) {
		session.removeAttribute(LOGIN_ADMIN_NO);
		session.removeAttribute(LOGIN_ADMIN_ID);
	}
	
	/**
	 * 로그인한 관리자의 로그인넘버를 세션에서 꺼낸다.
	 * 
	 * @param session
	 * @return
	 */
	public static String getLoginAdminId(HttpSession session) {
		return (String) session.getAttribute(LOGIN_ADMIN_NO);
	}
	
	/**
	 * 로그인한 관리자의 로그인ID를 세션에서 꺼낸다.
	 * 
	 * @param session
	 * @return
	 */
	public static int getLoginAdminNo(HttpSession session) {
		return (int) session.getAttribute(LOGIN_ADMIN_ID);
	}
	
}
