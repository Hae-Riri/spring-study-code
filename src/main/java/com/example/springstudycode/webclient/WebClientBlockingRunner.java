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
public class WebClientBlockingRunner implements ApplicationRunner {

    private final WebClient.Builder webClientBuilder;

    public WebClientBlockingRunner(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        WebClient webClient = webClientBuilder.build();

        StopWatch stopWatch = new StopWatch();
        log.info("(webclient block) start!");
        stopWatch.start();

        Mono<String> resultFor5Sec = webClient
                .get()
                .uri("http://localhost:8080/delay/5")
                .retrieve()
                .bodyToMono(String.class);

        resultFor5Sec.subscribe(result -> {
            log.info("(webclient) resultFor5Sec : {}", result);
            log.info("(webclient) now :{}", stopWatch.getTotalTimeSeconds());
        });

        String resultFor5SecString = resultFor5Sec.block();
        log.info("resultFor5SecString: {}", resultFor5SecString);
        stopWatch.stop();
        log.info("(webclient) now :{}", stopWatch.getTotalTimeSeconds());
        stopWatch.start();

        Mono<String> resultFor10Sec = webClient
                .get()
                .uri("http://localhost:8080/delay/10")
                .retrieve()
                .bodyToMono(String.class);

        resultFor10Sec.subscribe(result -> {
            log.info("(webclient) resultFor10Sec : {}", result);

            log.info("(webclient) now :{}", stopWatch.getTotalTimeSeconds());
            stopWatch.start();
        });

        stopWatch.stop();
        log.info("(webclient block) result(10Sec): {}", stopWatch.getTotalTimeSeconds());
    }
}
