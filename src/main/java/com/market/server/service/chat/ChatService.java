package com.market.server.service.chat;

import java.util.List;

import com.market.server.dto.Search;
import com.market.server.dto.chat.ChatMessageDTO;
import com.market.server.dto.chat.ChatRoomDTO;

public interface ChatService {
	
	public void insertMessage(ChatMessageDTO chatMessage, String simpSessionId);
	
	public void createRoom(ChatRoomDTO chatRoom);
	
	public List<ChatRoomDTO> getChatRoomList(String userId);
	
	public ChatRoomDTO getChatRoomInfo(String roomId);
	
	public List<ChatMessageDTO> getChatMessage(Search search);
}
