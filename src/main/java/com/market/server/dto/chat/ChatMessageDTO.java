package com.market.server.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDTO {
	
	private String roomId;   // 방번호
	private String sender;   // 보낸사람
	private String message;  // 메세지
	private String sentDttm; // 보낸일시
	
}
