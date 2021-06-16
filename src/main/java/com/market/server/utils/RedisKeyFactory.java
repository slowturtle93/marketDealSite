package com.market.server.utils;

public class RedisKeyFactory {
	
	public static final String ORDER_KEY = "ORDERS";
  
    public enum Key {
      ORDER 
    }

    // 인스턴스화 방지
    private RedisKeyFactory() {}
  
    private static String generateKey(String id, Key key) {
      return id + ":" + key;
    }
  
    public static String generateOrderKey(String orderCd) {
      return generateKey(orderCd, Key.ORDER);
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
  
}
