package com.market.server.controller.chat;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.market.server.aop.LoginCheck;
import com.market.server.aop.LoginCheck.UserType;
import com.market.server.dto.Search;
import com.market.server.dto.chat.ChatMessageDTO;
import com.market.server.dto.chat.ChatRoomDTO;
import com.market.server.service.chat.Impl.ChatServiceImpl;
import com.market.server.utils.SessionUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@RequiredArgsConstructor
public class ChatController {
	
	@Value("${max.page.size:30}")
    private int pageSize;
	
	@Autowired
	private ChatServiceImpl chatService;
	
	
	/**
	 * 발송된 message를 처리한다.
	 * 
	 * @param chatMessage
	 * @param message
	 */
	@MessageMapping("/chat/message")
    public void message(ChatMessageDTO chatMessage, Message<?> message) {
		String simpSessionId = (String) message.getHeaders().get("simpSessionId");
		chatService.insertMessage(chatMessage, simpSessionId);
    }
	   
	/**
	 * 중고물품을 구매하기 위해 판매자에게 연락 시 새로운 채팅방 생성한다.
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("/createRoom")
	@LoginCheck(type = UserType.USER)
	public void createRoom(@RequestBody ChatReqeust chatReqeust){
		
		String roomNm   = chatReqeust.getRoomNm();
	    String buyerId  = chatReqeust.getBuyerId();
	    String sellerId = chatReqeust.getSellerId();
	    ChatRoomDTO chatRoom = ChatRoomDTO.create(roomNm, buyerId, sellerId);
	   
	    chatService.createRoom(chatRoom);
	}
	   
	/**
	 * 채팅방 목록을 조회한다.
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping("/rooms")
	@LoginCheck(type = UserType.USER)
	public List<ChatRoomDTO> getRoom(HttpSession session){
		String userId = SessionUtil.getLoginUserId(session);
		List<ChatRoomDTO> chatRoomList = chatService.getChatRoomList(userId);
		return chatRoomList;
	}
	
	/**
	 * 채팅방 입장 시 채팅방 정보 및 message 조회
	 * @return
	 */
	@GetMapping("/room/enter/{roomId}")
	@LoginCheck(type = UserType.USER)
	public ChatResponse chatting(@PathVariable("roomId") String roomId, @RequestParam(required = true) int cursor) {
		
	    ChatRoomDTO roomInfo = chatService.getChatRoomInfo(roomId);
	    
	    Search search = new Search();
	    search.add("roomId", roomId);
	    search.add("pg", cursor);
	    search.add("pgSz", pageSize);
	    search.setRow();
	    List<ChatMessageDTO> chatMessage = chatService.getChatMessage(search);
	    
	    ChatResponse chatResponse = new ChatResponse(roomInfo, chatMessage);
	    
	    return chatResponse;
	    
	}
	
	// -------------- response 객체 --------------
	
	@Getter
    @AllArgsConstructor
    private static class ChatResponse {
        private ChatRoomDTO chatRoom;
        private List<ChatMessageDTO> chatMessage;
    }
	
	/*======================= reqeust 객체 ======================= */
			
	@Getter
	@Setter
	private static class ChatReqeust {
		private String roomNm;
		private String buyerId;
		private String sellerId;
		private int pg;
	}
			
	
}
