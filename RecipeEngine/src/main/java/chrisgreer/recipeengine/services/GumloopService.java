package chrisgreer.recipeengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GumloopService {

    @Value("${gumloop.api.url}")
    private String gumloopApiUrl;

    private final WebClient webClient = WebClient.builder().build();

    public void sendUrl(String url) {
        webClient.post()
                .uri(gumloopApiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("url", url))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
