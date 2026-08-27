package az.ingress.turbo.az_clone.module.ai.service;

import az.ingress.turbo.az_clone.module.car.entity.CarEntity;
import az.ingress.turbo.az_clone.module.car.repository.CarRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class IngestionService {

    // Gemini pulsuz planı: embed_content üçün dəqiqədə 100 sorğu limiti var.
    // Bir partiyada bu limitin altında qalmaq üçün ehtiyat payı ilə 80 seçilib.
    private static final int BATCH_SIZE = 80;
    // Partiyalar arasında 60 saniyə gözləyirik ki, dəqiqəlik kvota sıfırlansın.
    private static final long DELAY_BETWEEN_BATCHES_MS = 60_000L;

    private final VectorStore vectorStore;
    private final CarRepository carRepository;

    public IngestionService(VectorStore vectorStore, CarRepository carRepository) {
        this.vectorStore = vectorStore;
        this.carRepository = carRepository;
    }

    @Transactional(readOnly = true)
    public String syncAllCarsToMilvus() {
        List<CarEntity> cars = carRepository.findByIndexedFalse();

        if (cars.isEmpty()) {
            return "PostgreSQL bazasında indekslənəcək maşın tapılmadı.";
        }

        List<Document> documents = cars.stream()
                .map(this::buildDocument)
                .toList();

        int totalBatches = (int) Math.ceil((double) documents.size() / BATCH_SIZE);

        for (int i = 0; i < documents.size(); i += BATCH_SIZE) {
            List<Document> batch = documents.subList(i, Math.min(i + BATCH_SIZE, documents.size()));
            int currentBatch = (i / BATCH_SIZE) + 1;

            vectorStore.accept(batch);

            boolean isLastBatch = currentBatch == totalBatches;
            if (!isLastBatch) {
                try {
                    Thread.sleep(DELAY_BETWEEN_BATCHES_MS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Sinxronizasiya fasilə zamanı kəsildi", e);
                }
            }
        }

        return String.format("Cəmi %d ədəd maşın elanı PostgreSQL-dən Milvus-a uğurla sinxronlaşdırıldı!", documents.size());
    }

    public void syncSingleCarToMilvus(CarEntity car) {
        vectorStore.accept(List.of(buildDocument(car)));
    }

    private Document buildDocument(CarEntity car) {
        String brandName = car.getBrand() != null ? car.getBrand().getName() : "Naməlum";
        String modelName = car.getModel() != null ? car.getModel().getName() : "Naməlum";

        String content = String.format(
                "Elan ID: %d, Marka: %s, Model: %s, İl: %d, Qiymət: %.2f AZN, Yanacaq: %s, Mühərrik: %.1f L, Yürüş: %d km. Ətraflı Məlumat: %s",
                car.getId(),
                brandName,
                modelName,
                car.getYear(),
                car.getPrice(),
                car.getFuel(),
                car.getEngineVolume(),
                car.getMileage(),
                car.getDescription()
        );

        Map<String, Object> metadata = Map.of(
                "carId", car.getId(),
                "brand", brandName,
                "price", car.getPrice()
        );

        return new Document(content, metadata);
    }
}