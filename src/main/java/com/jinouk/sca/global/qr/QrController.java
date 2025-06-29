package com.jinouk.sca.global.qr;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class QrController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String FASTAPI_BASE_URL = "https://qr-api-942476057907.asia-northeast3.run.app";

    @PostMapping("/generate-qr-link")
    public ResponseEntity<Map<String, String>> generateQrLink(@RequestParam String data) {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("data", data);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);


        @SuppressWarnings("unchecked")
        ResponseEntity<Map<String, String>> response = (ResponseEntity<Map<String, String>>)
                (ResponseEntity<?>) restTemplate.postForEntity(
                        FASTAPI_BASE_URL + "/generate-qr", request, Map.class
                );



        if (response.getStatusCode() == HttpStatus.OK) {

            String qrRelativeUrl = (String) response.getBody().get("qr_url");
            String fullUrl = FASTAPI_BASE_URL + qrRelativeUrl;

            Map<String, String> result = Map.of("qrImageUrl", fullUrl);
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(500).body(Map.of("error", "QR 생성 실패"));
        }
    }
}
