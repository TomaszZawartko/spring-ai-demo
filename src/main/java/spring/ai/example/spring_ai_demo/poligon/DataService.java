package spring.ai.example.spring_ai_demo.poligon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class DataService {
    private final WebClient webClient;
    private final String taskId;
    private final String apiKey;
    private final String verifyUrl;

    public DataService(WebClient.Builder webClientBuilder,
                       @Value("${app.task.id}") String taskId,
                       @Value("${app.apikey}") String apiKey,
                       @Value("${app.verify.url}") String verifyUrl) {
        this.webClient = webClientBuilder.baseUrl("https://poligon.aidevs.pl").build();
        this.taskId = taskId;
        this.apiKey = apiKey;
        this.verifyUrl = verifyUrl;
    }

    public Mono<String> fetchData() {
        return webClient.get()
                .uri("/dane.txt")
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(this::sendDataToVerifyEndpoint);
    }

    private Mono<String> sendDataToVerifyEndpoint(String data) {
        String[] lines = data.split("\n");
        if (lines.length < 2) {
            return Mono.error(new IllegalStateException("Invalid data format"));
        }

        // Tworzymy obiekt żądania
        VerifyRequest request = new VerifyRequest(taskId, apiKey, new String[]{lines[0], lines[1]});

        // Wysyłamy żądanie POST na endpoint verify
        return webClient.post()
                .uri(verifyUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class);
    }
}
