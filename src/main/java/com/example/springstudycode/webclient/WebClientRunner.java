package com.example.springstudycode.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class WebClientRunner implements ApplicationRunner {
    private final WebClient.Builder webClientBuilder;

    public WebClientRunner(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        WebClient webClient = webClientBuilder.build();

        StopWatch stopWatch = new StopWatch();
        log.info("(webclient) start!");
        stopWatch.start();

        Mono<String> resultFor5Sec = webClient
                .get()
                .uri("http://localhost:8080/delay/5")
                .retrieve()
                .bodyToMono(String.class);

        resultFor5Sec.subscribe(result -> {
            log.info("(webclient) resultFor5Sec : {}", result);

            stopWatch.stop();
            log.info("(webclient) now :{}", stopWatch.getTotalTimeSeconds());
            stopWatch.start();
        });

        Mono<String> resultFor10Sec = webClient
                .get()
                .uri("http://localhost:8080/delay/10")
                .retrieve()
                .bodyToMono(String.class);

        resultFor10Sec.subscribe(result -> {
            log.info("(webclient) resultFor10Sec : {}", result);

            stopWatch.stop();
            log.info("(webclient) now :{}", stopWatch.getTotalTimeSeconds());
        });
    }
}
