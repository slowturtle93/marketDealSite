package com.market.server.dto.push;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class PushMessage {
    @NonNull
    private String title;
    @NonNull
    private String message;
  
    public static final PushMessage ORDER_STATUS_ACCEPT          = new PushMessage("[상품 접수]"     , "상품이 정상적으로 접수되었습니다.");       // 상품 접수
    public static final PushMessage ORDER_STATUS_CORRECT         = new PushMessage("[상품 잡화처리]" , "상품준비가 완료되어 잡화처리가 되었습니다.");  // 잡화처리
    public static final PushMessage ORDER_STATUS_START           = new PushMessage("[상품 배송출고]" , "상품이 배송출발하였습니다.");             // 배송출발
    public static final PushMessage ORDER_STATUS_COMPLETE        = new PushMessage("[상품 배송완료]" , "상품이 정상적으로 배송이완료되었습니다.");    // 배송완료
    public static final PushMessage ORDER_STATUS_REFUND          = new PushMessage("[상품 환불]"    , "상품을 요청하신 환불처리를 접수하였습니다."); // 환불
    public static final PushMessage ORDER_STATUS_REFUND_COMPLETE = new PushMessage("[상품 환불완료]" , "상품이 정상적으로 환불처리되었습니다");      // 환불완료
  
  
    private LocalDateTime generatedTime;
  
    public PushMessage() {}
  
    public PushMessage(String title, String message) {
      this.title = title;
      this.message = message;
      this.generatedTime = LocalDateTime.now();
    }
}
