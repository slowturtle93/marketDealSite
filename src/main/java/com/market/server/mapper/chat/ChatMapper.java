package com.market.server.mapper.chat;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.server.dto.Search;
import com.market.server.dto.chat.ChatMessageDTO;
import com.market.server.dto.chat.ChatRoomDTO;


@Mapper
public interface ChatMapper {

	public int addMessageList(@Param("messageList") List<ChatMessageDTO> messageList);
	
	public int createRoom(ChatRoomDTO chatRoom);
	
	public List<ChatRoomDTO> getChatRoomList(@Param("userId") String userId);
	
	public ChatRoomDTO getChatRoomInfo(@Param("roomId") String roomId);
	
	public List<ChatMessageDTO> getChatMessage(Search search);
	
}	
