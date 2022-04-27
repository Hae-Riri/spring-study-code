# spring-study-code

srping에 관련된 의문을 해결하기 위해 학습용 테스트를 작성합니다.

### 1. WebClient vs RestTemplate

```shell
2022-04-14 11:04:17.330  INFO 76559 --- [           main] c.e.s.webclient.RestTemplateRunner       : (restTemplate) resultFor5Sec: Done!
2022-04-14 11:04:27.360  INFO 76559 --- [           main] c.e.s.webclient.RestTemplateRunner       : (restTemplate) resultFor10Sec: Done!
2022-04-14 11:04:27.363  INFO 76559 --- [           main] c.e.s.webclient.RestTemplateRunner       : (restTemplate) result: 15.24417925
2022-04-14 11:04:27.423  INFO 76559 --- [           main] c.e.s.webclient.WebClientRunner          : (webclient) start!
2022-04-14 11:04:32.751  INFO 76559 --- [ctor-http-nio-2] c.e.s.webclient.WebClientRunner          : (webclient) resultFor5Sec : Done!
2022-04-14 11:04:32.751  INFO 76559 --- [ctor-http-nio-2] c.e.s.webclient.WebClientRunner          : (webclient) result(5Sec): 5.327490417
2022-04-14 11:04:37.710  INFO 76559 --- [ctor-http-nio-3] c.e.s.webclient.WebClientRunner          : (webclient) resultFor10Sec : Done!
2022-04-14 11:04:37.710  INFO 76559 --- [ctor-http-nio-3] c.e.s.webclient.WebClientRunner          : (webclient) result(10Sec): 10.286018083
```

**RestTemplate**은 두 동작 모두 main 스레드에서 실행됐고, 동기적으로 진행이 되었기 때문에 둘을 모두 호출하는데에 총 15초가 걸렸다. <br>
반면, **WebClient**는 일단 스레드 자체도 각각 다른 스레드에서 실행됐고 비동기적으로 진행되어서 전부 다 끝나는 데엔 총 10초가 걸렸다. <br><br>

retrieve() :

- response를 어떻게 발췌할건지를 선언할 때 사용. toEntity? bodyToMono?.
- bodyToMono의 경우, 디폴트로 4xx, 5xx 에러를 webclientException으로 주니까 이걸 핸들링하고 싶을 때 retrieve().onStatus(HttpStatus::is4xxClientError~...)를 사용하길 권장함.

bodyToMono() :

- 리턴 타입이 Mono<내객체>

toEntity() :

- 리턴타입이 Mono<ResponseEntity<내객체>>
- Mono<ResponseEntity>>는 promise와 비슷함. 기본적으로는 '내가 결과를 받으면 거기에 responseEntity가 있을거야'를 의미하지만 나는 일단 그게 언제일지 몰라. 그래서 지금 당장은 Mono로 갖고 있을거야. 그래서 너가 map,
  flatMpa 등을 활용해서 발췌하고 뭔가 변형시킬 때 너는 항상 Mono를 리턴받아. 왜냐하면 우리는 실제 값을 갖고 있지 않거든. 그저 파이프라인을 구추갛ㄹ 뿐인거야. 우리가 실제로 그 값을 갖게 되었을 때 그때 application이
  ResponseEntity로 받으면 좋겠다고 약속해두는거야.
