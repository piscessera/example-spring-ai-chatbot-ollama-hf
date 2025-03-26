package com.example.lab.demo.emdedding;

import java.util.List;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class VectorService {
  private static final int MAX_RESULTS = 3;

  private final VectorStore vectorStore;

  public VectorService(VectorStore vectorStore) {
    this.vectorStore = vectorStore;
  }

  public List<Document> semanticSearch(String theme) {
    SearchRequest searchRequest = SearchRequest
      .builder()
      .query(theme)
      .topK(MAX_RESULTS)
      .build();
    List<Document> documents = vectorStore.similaritySearch(searchRequest);

    return documents;
  }
}
