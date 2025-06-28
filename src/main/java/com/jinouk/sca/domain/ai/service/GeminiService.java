package com.jinouk.sca.domain.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class GeminiService {

    private static final String PROJECT_ID = "codeus2025-01";
    private static final String LOCATION = "us-central1";
    private static final String MODEL_ID = "gemini-2.5-flash"; // ✅ 수정된 모델 ID
    private static final String ENDPOINT = String.format(
            "https://%s-aiplatform.googleapis.com/v1/projects/%s/locations/%s/publishers/google/models/%s:generateContent",
            LOCATION, PROJECT_ID, LOCATION, MODEL_ID
    );

    public String verifyQuestImage(MultipartFile image, String keyword) throws Exception {
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("src/main/resources/codeus2025-01-d82f7d53c599.json"))
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));

        HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(credentials);
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(request -> {
            request.setParser(new JsonObjectParser(jsonFactory));
            credentialsAdapter.initialize(request);
        });

        byte[] imageBytes = image.getBytes();
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);

        String prompt = """
                너는 API로써 작동할 거고,
                주어진 사진이 다음 키워드로 주어진 장소에서 촬영된 것인지 판단할 거야.
                키워드: %s
                오직 True 또는 False 중 하나로만 답변해.
                다른 설명, 말은 절대 하지 마.
                """.formatted(keyword);

        String requestJson = """
                {
                  "contents": [
                    {
                      "role": "user",
                      "parts": [
                        { "text": "%s" },
                        {
                          "inlineData": {
                            "mimeType": "%s",
                            "data": "%s"
                          }
                        }
                      ]
                    }
                  ]
                }
                """.formatted(prompt, image.getContentType(), base64Image);

        HttpContent content = new ByteArrayContent("application/json", requestJson.getBytes(StandardCharsets.UTF_8));
        HttpRequest request = requestFactory.buildPostRequest(new GenericUrl(ENDPOINT), content);
        request.setReadTimeout(30000);
        HttpResponse response = request.execute();

        // Gemini 응답 처리
        Map<?, ?> responseMap = response.parseAs(Map.class);
        List<?> candidates = (List<?>) responseMap.get("candidates");
        Map<?, ?> firstCandidate = (Map<?, ?>) candidates.get(0);
        Map<?, ?> contentMap = (Map<?, ?>) firstCandidate.get("content");
        List<?> parts = (List<?>) contentMap.get("parts");
        Map<?, ?> firstPart = (Map<?, ?>) parts.get(0);

        // 안전한 text 추출
        Object rawText = firstPart.get("text");
        String rawResponse;
        if (rawText instanceof String) {
            rawResponse = ((String) rawText).strip();
        } else if (rawText instanceof List) {
            List<?> textList = (List<?>) rawText;
            rawResponse = textList.isEmpty() ? "" : textList.get(0).toString().strip();
        } else {
            throw new RuntimeException("예상치 못한 응답 형식: " + rawText.getClass());
        }

        // 백틱 제거
        if (rawResponse.startsWith("```")) {
            rawResponse = rawResponse.replaceAll("^```[a-zA-Z]*\\n?", "");
            rawResponse = rawResponse.replaceAll("\\n?```$", "");
        } else if (rawResponse.startsWith("`") && rawResponse.endsWith("`")) {
            rawResponse = rawResponse.substring(1, rawResponse.length() - 1);
        }

        // 🔍 핵심 처리: 문자열 True/False 대응
        String normalized = rawResponse.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
        if (normalized.contains("true")) {
            return "정말 잘 하셨네요! 정답이에요!";
        } else if (normalized.contains("false")) {
            return "제가 생각하기에는 다른 곳의 사진인 것 같아요...";
        } else {
            throw new RuntimeException("판별 불가한 응답 형식: " + rawResponse);
        }
    }

}
