package com.example.springstudycode.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class RestTemplateRunner implements ApplicationRunner {
    private final RestTemplateBuilder restTemplateBuilder;

    public RestTemplateRunner(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public void run(ApplicationArguments args) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        StopWatch stopWatch = new StopWatch();
        log.info("(restTemplate) start!");
        stopWatch.start();

        String resultFor5Sec = restTemplate.getForObject("http://localhost:8080/delay/5", String.class);
        log.info("(restTemplate) resultFor5Sec: {}", resultFor5Sec);

        String resultFor10Sec = restTemplate.getForObject("http://localhost:8080/delay/10", String.class);
        log.info("(restTemplate) resultFor10Sec: {}", resultFor10Sec);

        stopWatch.stop();
        log.info("(restTemplate) total time: {}", stopWatch.getTotalTimeSeconds());
    }
}
