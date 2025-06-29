package com.jinouk.sca.global.base64;

import java.util.Base64;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Base64ImageConverter {
    public static String encodeToBase64(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Path.of(imagePath));
        String base64 = Base64.getEncoder().encodeToString(imageBytes);
        return "data:image/png;base64," + base64;  // 확장자에 맞춰서 바꿔야 함
    }
}
