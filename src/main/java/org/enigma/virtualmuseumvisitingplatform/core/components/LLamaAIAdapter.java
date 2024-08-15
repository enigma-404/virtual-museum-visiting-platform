package org.enigma.virtualmuseumvisitingplatform.core.components;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LLamaAIAdapter {

    private final OllamaChatModel chatModel;

    public boolean isSwear (String prompt){
        ChatResponse chatResponse = chatModel.call(new Prompt(prompt, OllamaOptions.create().withModel("profanity-detector")));
        return chatResponse.getResult().getOutput().getContent().equals("1");
    }
}
