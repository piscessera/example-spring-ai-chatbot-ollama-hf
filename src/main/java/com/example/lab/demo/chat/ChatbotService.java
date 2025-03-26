package com.example.lab.demo.chat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import com.example.lab.demo.emdedding.VectorService;

@Service
public class ChatbotService {
  private final ChatClient chatClient;
  private final VectorService vectorService;

  public ChatbotService(ChatClient chatClient, VectorService vectorService) {
    this.chatClient = chatClient;
    this.vectorService = vectorService;
  }

  public ChatResponse chatWithVectorStore(ChatRequest chatRequest) {
    UUID chatId = Optional
      .ofNullable(chatRequest.chatId())
      .orElse(UUID.randomUUID());

    List<Document> documents = vectorService.semanticSearch(chatRequest.question());

    StringBuilder userPrompt = new StringBuilder();
    StringBuilder context = new StringBuilder();

    if (documents != null && !documents.isEmpty()) {

      userPrompt.append("Here is base information:\n");

      for (Document document : documents) {
        userPrompt.append("Author: " + document.getMetadata().get("author") + ", Quote: " + document.getText()).append("\n");
      }

      String question = userPrompt.toString();
      System.out.println(question);

      context.append(userPrompt + "\n\n User question: " + chatRequest.question());

      String answer = chatClient
      .prompt()
      .user(context.toString())
      .advisors(advisorSpec -> 
        advisorSpec.param("chat_memory_conversation_id", chatId)
      )
      .call()
      .content();
      return new ChatResponse(chatId, answer);
    } else {
      context.append("Sorry, I don't have an answer for that question.");
      return new ChatResponse(chatId, context.toString());
    }

  }

  public ChatResponse chat(ChatRequest chatRequest) {
    UUID chatId = Optional
      .ofNullable(chatRequest.chatId())
      .orElse(UUID.randomUUID());
    
    String answer = chatClient
      .prompt()
      .user(chatRequest.question())
      .advisors(advisorSpec -> 
        advisorSpec.param("chat_memory_conversation_id", chatId)
      )
      .call()
      .content();
 
    return new ChatResponse(chatId, answer);
  }  
}
