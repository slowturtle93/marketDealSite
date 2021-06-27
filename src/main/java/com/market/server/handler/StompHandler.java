package com.market.server.handler;

import java.util.List;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.market.server.dao.ChatDao;
import com.market.server.dto.chat.ChatMessageDTO;
import com.market.server.mapper.chat.ChatMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor{

	private final ChatDao chatDao;
	private final ChatMapper chatMapper;
	
	/**
	 * 발송된 메시지를 전처리를 진행한다.
	 */
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel){
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
		
		// 연결이 끊어졌을 경우 Redis에 저장 된 message 내용 저장
		if(StompCommand.DISCONNECT == accessor.getCommand()) {
			
			String sessionId = (String) message.getHeaders().get("simpSessionId");
			List<ChatMessageDTO> messageList = chatDao.getChatMessage(sessionId);
			
			if(messageList.size() > 0) {
				chatDao.delChatMessage(sessionId);
				chatMapper.addMessageList(messageList);
			}
		}
		
		return message;
	}
}
