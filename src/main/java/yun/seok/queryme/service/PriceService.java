package yun.seok.queryme.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceService {

    private final UtilityService utility;

    public List<Integer> test() throws IOException {
        URL url = utility.getSecuritiesItemURL("삼성전자", 100, 1);

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
