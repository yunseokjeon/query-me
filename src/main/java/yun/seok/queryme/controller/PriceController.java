package yun.seok.queryme.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class PriceController {

    @Value("${data.go.kr.key}")
    String dataGoKrKey;

    @GetMapping("/test")
    public String test() {

        WebClient webClient = WebClient.create("https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService");

        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getStockPriceInfo")
                        .queryParam("serviceKey", dataGoKrKey)
                        .queryParam("numOfRows", "10")
                        .queryParam("pageNo", "1")
                        .queryParam("resultType","json")
                        .queryParam("itmsNm","삼성전자")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return response;
    }
}
