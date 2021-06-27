package com.market.server.dto.chat;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDTO {
	
	private String roomId;   // 방번호
	private String roomNm;   // 방이름
	private String buyerId;  // 구매자
	private String sellerId; // 판매자
	private String delYn;    // 삭제여부
	private String regDttm;  // 등록일시
	
	public static ChatRoomDTO create(String roomNm, String buyerId, String sellerId) {
		ChatRoomDTO chatRoom = new ChatRoomDTO();
		chatRoom.roomId   = UUID.randomUUID().toString();
		chatRoom.roomNm   = roomNm;
		chatRoom.buyerId  = buyerId;
		chatRoom.sellerId = sellerId;
		return chatRoom;
	}
	
}
