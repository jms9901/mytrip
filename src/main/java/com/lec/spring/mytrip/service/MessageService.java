//package com.lec.spring.mytrip.service;
//
//
//import com.lec.spring.mytrip.domain.Message;
//import com.lec.spring.mytrip.repository.MessageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MessageService {
//
//    private final MessageRepository messageRepository;
//
//    @Autowired
//    public MessageService(MessageRepository messageRepository) {
//        this.messageRepository = messageRepository;
//    }
//
//    // 메시지 저장 메소드
//    public Message saveMessage(String sender, String content) {
//        Message message = new Message(sender, content);
//        return messageRepository.save(message);
//    }
//}