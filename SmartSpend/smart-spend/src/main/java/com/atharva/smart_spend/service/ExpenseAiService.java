package com.atharva.smart_spend.service;

import org.springframework.ai.chat.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ExpenseAiService {

    private final ChatClient chatClient;

    public ExpenseAiService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String categorizeExpense(String description) {
        // Advanced Prompting: Give the AI a Persona and Rules
        String systemInstruction = "You are a precise financial assistant. ";
        String categories = "[FOOD, TRAVEL, BILLS, ENTERTAINMENT, SHOPPING, INVESTMENT, OTHER]. ";
        String rules = "Rule 1: Categorize the input into one of the listed categories. " +
                       "Rule 2: Return ONLY the category name in UPPERCASE. " +
                       "Rule 3: No punctuation, no sentences, no explanations. " +
                       "Rule 4: If unsure, return 'OTHER'. ";

        String userPrompt = "Input: " + description;

        String finalPrompt = systemInstruction + categories + rules + userPrompt;

        // Using the stable .call() method for version 0.8.1
        String response = chatClient.call(finalPrompt);

        // Cleanup: Just in case the AI adds a space or newline
        return response.trim().toUpperCase();
    }
}