package com.jinouk.sca.domain.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import com.jinouk.sca.domain.ai.repo.QuestRepository;
import com.jinouk.sca.domain.ai.repo.UserToQuestRepository;
import com.jinouk.sca.domain.ai.entity.quest;
import com.jinouk.sca.domain.ai.entity.userToquest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class extractAIService {

    private static final String PROJECT_ID = "codeus2025-01";
    private static final String LOCATION = "us-central1";
    private static final String MODEL_ID = "gemini-2.5-flash";

    private static final String ENDPOINT = String.format(
            "https://%s-aiplatform.googleapis.com/v1/projects/%s/locations/%s/publishers/google/models/%s:generateContent",
            LOCATION, PROJECT_ID, LOCATION, MODEL_ID
    );

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private UserToQuestRepository userToQuestRepository;

    public String buildPromptforExtractLocate(String sen) {
        return """
                너는 API로써 작동할 거고,
                사용자가 의성군에 위치한 A에 왔어. 퀘스트 줘와 같이 어느 곳에 갔는지와 퀘스트를 달라고 하는 의미를 갖는 문장을 네게 제시할 거야.
                그러면 너는 위 문장에서 위치에 해당하는 A와 너가 직접 창의적으로 만든 A에서 오프라인으로 할 수 있는 여러 미션(퀘스트)들 중 인증 사진을 찍을 수 있는 것으로 골라서,
                A와 너가 만든 미션(퀘스트)를 Key : Value로 갖는 JSON을 반환해.
                다른 것은 반환할 필요 없어. JSON만 반환하면 돼.
                
                사용자가 보낸 문장: %s
                """.formatted(sen);
    }

    public Map<String, String> extractLocateAndSave(String sentence, int userId) throws Exception {
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("src/main/resources/codeus2025-01-d82f7d53c599.json"))
                .createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));

        HttpCredentialsAdapter credentialsAdapter = new HttpCredentialsAdapter(credentials);
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory(request -> {
            request.setParser(new JsonObjectParser(jsonFactory));
            credentialsAdapter.initialize(request);
        });

        String prompt = buildPromptforExtractLocate(sentence);
        String requestJson = """
                {
                  "contents": [
                    {
                      "role": "user",
                      "parts": [
                        { "text": "%s" }
                      ]
                    }
                  ]
                }
                """.formatted(prompt);

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
        String jsonText;
        if (rawText instanceof String) {
            jsonText = ((String) rawText).strip();
        } else if (rawText instanceof List) {
            List<?> textList = (List<?>) rawText;
            jsonText = textList.isEmpty() ? "" : textList.get(0).toString().strip();
        } else {
            throw new RuntimeException("예상치 못한 응답 형식: " + rawText.getClass());
        }

        // 백틱 제거
        if (jsonText.startsWith("```")) {
            jsonText = jsonText.replaceAll("^```[a-zA-Z]*\\n?", "");
            jsonText = jsonText.replaceAll("\\n?```$", "");
        } else if (jsonText.startsWith("`") && jsonText.endsWith("`")) {
            jsonText = jsonText.substring(1, jsonText.length() - 1);
        }

        // JSON 파싱
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> extractedMap = mapper.readValue(jsonText, Map.class);


        return extractedMap;
    }
}