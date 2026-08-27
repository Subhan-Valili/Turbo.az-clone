package az.ingress.turbo.az_clone.module.ai.service;

import az.ingress.turbo.az_clone.module.ai.dto.AiCarRecommendationDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RagService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public RagService(ChatClient chatClient, VectorStore vectorStore) {
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    public AiCarRecommendationDto askAiForJson(String userQuestion) {
        // 1. Milvus-dan suala ən yaxın 5 elanı tapırıq (Düzgün Builder strukturu ilə)
        List<Document> similarDocs = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(userQuestion)
                        .topK(20)
                        .build()
        );

        // 2. Mətnləri bir yerə yığırıq (getText() istifadə olunur)
        String context = similarDocs.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n---\n"));

        // 3. Output Converter
        var converter = new BeanOutputConverter<>(AiCarRecommendationDto.class);

        // 4. Context + Question məlumatlarını Gemini-yə göndəririk
        String response = this.chatClient.prompt()
                .user(u -> u.text("""
                        Aşağıdakı maşın elanları məlumatlarından istifadə edərək istifadəçinin sualını cavablandır.
                        
                        MƏLUMATLAR:
                        {context}
                        
                        SUAL:
                        {question}
                        
                        Cavabı mütləq aşağıdakı JSON formatında ver:
                        {format}
                        """)
                        .param("context", context)
                        .param("question", userQuestion)
                        .param("format", converter.getFormat()))
                .call()
                .content();

        return converter.convert(response);
    }
}