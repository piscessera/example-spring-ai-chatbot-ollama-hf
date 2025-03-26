package com.example.lab.demo.chat;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class ChatbotController {

  private final ChatbotService chatbotService;

  public ChatbotController(ChatbotService chatbotService) {
    this.chatbotService = chatbotService;
  }

  @PostMapping("/chat")
  public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
      ChatResponse chatResponse = chatbotService.chatWithVectorStore(chatRequest);
      return ResponseEntity.ok(chatResponse);
  }
  
}
