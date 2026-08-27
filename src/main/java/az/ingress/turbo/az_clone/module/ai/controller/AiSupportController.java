package az.ingress.turbo.az_clone.module.ai.controller;

import az.ingress.turbo.az_clone.module.ai.dto.AiCarRecommendationDto;
import az.ingress.turbo.az_clone.module.ai.service.IngestionService;
import az.ingress.turbo.az_clone.module.ai.service.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiSupportController {

    private final RagService ragService;
    private final IngestionService ingestionService;


    @GetMapping("/recommend")
    public ResponseEntity<AiCarRecommendationDto> getCarRecommendations(@RequestParam String question) {
        return ResponseEntity.ok(ragService.askAiForJson(question));
    }

    @PostMapping("/sync-database")
    public ResponseEntity<String> syncDatabase() {
        return ResponseEntity.ok(ingestionService.syncAllCarsToMilvus());
    }
}
