package yun.seok.queryme.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/price")
@RequiredArgsConstructor
public class PriceController {

    @Value("${data.go.kr.securities.key}")
    String dataGoKrKey;

    @GetMapping("/test")
    public List<Integer> test() throws IOException {

        String itmsNm = URLEncoder.encode("삼성전자", StandardCharsets.UTF_8);

        URL url = new URL("https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo?"
                + "serviceKey=" + dataGoKrKey + "&"
                + "numOfRows=10" + "&"
                + "pageNo=1" + "&"
                + "resultType=json" + "&"
                + "itmsNm=" + itmsNm);


        BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        String response = bf.readLine();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);

        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

        List<Integer> result = new ArrayList<>();
        if (itemsNode.isArray()) {
            for (JsonNode itemNode : itemsNode) {
                String clpr = itemNode.get("clpr").asText();
                result.add(Integer.parseInt(clpr));
            }
        }
        return result;
    }
}
