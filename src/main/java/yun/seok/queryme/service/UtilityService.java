package yun.seok.queryme.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class UtilityService {

    @Value("${data.go.kr.securities.key}")
    String dataGoKrSecuritesKey;
    String securitesHostUrl = "https://apis.data.go.kr/1160100/service/GetStockSecuritiesInfoService/getStockPriceInfo?";


    URL getSecuritiesItemURL(String itemName, int numOfRows, int pageNo) throws MalformedURLException {
        String itmsNm = URLEncoder.encode(itemName, StandardCharsets.UTF_8);

        return new URL(
                securitesHostUrl + "serviceKey="
                        + dataGoKrSecuritesKey + "&"
                        + "numOfRows=" + numOfRows + "&"
                            + "pageNo=" + pageNo + "&"
                        + "resultType=json" + "&"
                        + "itmsNm=" + itmsNm);

    }
}
