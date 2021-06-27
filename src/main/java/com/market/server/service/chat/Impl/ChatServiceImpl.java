package com.market.server.service.chat.Impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import com.market.server.dao.ChatDao;
import com.market.server.dto.Search;
import com.market.server.dto.chat.ChatMessageDTO;
import com.market.server.dto.chat.ChatRoomDTO;
import com.market.server.mapper.chat.ChatMapper;
import com.market.server.service.chat.ChatService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	private ChatMapper chatMapper;
	
	@Autowired
	private ChatDao chatDao;
	
	private final SimpMessageSendingOperations messagingTemplate;

	/**
	 * 발송된 message를 Redis에 저장한다.
	 */
	@Override
	public void insertMessage(ChatMessageDTO message, String simpSessionId) {
		//현재시간 set
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time = new Date();
		message.setSentDttm(format.format(time));
		
		chatDao.addChatMessage(message, simpSessionId);
		messagingTemplate.convertAndSend("/topic/chatting" , message);
	}
	
	/**
	 * 중고물품을 구매하기 위해 판매자에게 연락 시 새로운 채팅방 생성한다.
	 */
	@Override
	public void createRoom(ChatRoomDTO chatRoom) {
		chatMapper.createRoom(chatRoom);
	}

	/**
	 * 본인이 연락을 시도한 채팅방 List를 조회한다.
	 */
	@Override
	public List<ChatRoomDTO> getChatRoomList(String userId) {
		return chatMapper.getChatRoomList(userId);
	}

	/**
	 * 채팅방 입장 시 채팅방 정보를 조회한다.
	 */
	@Override
	public ChatRoomDTO getChatRoomInfo(String roomId) {
		return chatMapper.getChatRoomInfo(roomId);
	}

	/**
	 * 채팅방의 message내용을 조회한다.
	 */
	@Override
	public List<ChatMessageDTO> getChatMessage(Search serach) {
		return chatMapper.getChatMessage(serach);
	}

}
