package com.example.lab.demo.chat;

import java.util.UUID;

public record ChatResponse(UUID chatId, String answer) {
} 