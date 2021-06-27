package com.market.server.utils;

public class RedisKeyFactory {
	
	public static final String ORDER_KEY     = "ORDERS";
	public static final String VIEW_CNT_KEY  = "VIEWCNT";
	public static final String LIKE_CNT_KEY  = "LIKECNT";
	public static final String ORDER_CNT_KEY = "ORDERCNT";
  
    public enum Key {
      FCM_USER, FCM_USER_ERROR, CHAT_USER
    }

    // 인스턴스화 방지
    private RedisKeyFactory() {}
  
    private static String generateKey(String id, Key key) {
      return id + ":" + key;
    }
  
    public static String generateFcmUserKey(String userId) {
      return generateKey(userId, Key.FCM_USER);
    }
    
    public static String generateChatUserKey(String userId) {
      return generateKey(userId, Key.CHAT_USER);
    }
    
    /**
     * 생성된 키로부터 아이디를 추출한다.
     * 
     * @param key redis Key
     * @return
     */
    public static String getIdFromKey(String key) {
      return key.substring(0, key.indexOf(":"));
    }
    
    public static String generateFcmUserErrorKey(String userId) {
      return generateKey(userId, Key.FCM_USER_ERROR);
    }
  
}
