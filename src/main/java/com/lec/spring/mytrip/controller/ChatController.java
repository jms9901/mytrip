package com.lec.spring.mytrip.controller;




import com.lec.spring.mytrip.domain.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    @GetMapping("/chat")
    public String chat() {
        return "Chat";
    }

    // 클라이언트에서 /app/chat에 메시지를 보내면 처리되는 메서드
    @MessageMapping("/chat")
    public void sendMessage(Message message) {
        // 서버에서 받은 메시지를 /topic/messages에 발행하여 구독한 클라이언트에게 메시지를 전달
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}
