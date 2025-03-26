package com.example.lab.demo.chat;

import java.util.UUID;

import org.springframework.lang.Nullable;

public record ChatRequest (@Nullable UUID chatId, String question) {  
}
