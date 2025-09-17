package chrisgreer.recipeengine.services;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GumloopService {

    private final WebClient webClient = WebClient.builder().build();

    public void sendUrl(String url) {
        String gumloopApiUrl = "https://tbd";

        webClient.post()
                .uri(gumloopApiUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("url", url))
                .retrieve()
                .toBodilessEntity()
                .block();
    }

}
