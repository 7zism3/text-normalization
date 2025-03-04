package com.sevenhallo.text.normalization;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class TextProcessor {

    private final KeywordProcessor keywordProcessor;
    private static final String rulesFilePath = "rules.json";

    public TextProcessor() throws IOException {
        this(rulesFilePath);
    }

    // Constructor: Khởi tạo và đọc các rules từ file JSON
    public TextProcessor(String rulesFilePath) throws IOException {
        this.keywordProcessor = new KeywordProcessor();
        Map<String, List<String>> rules = loadRules(rulesFilePath);
        rules.forEach(keywordProcessor::addKeywordsFromDict);
    }

    // Phương thức xử lý nhận vào chuỗi và trả về chuỗi đã sửa
    public String processText(String input) {
        if (input == null || input.trim().isEmpty()) {
            return input; // Nếu chuỗi null hoặc rỗng thì trả lại chính nó
        }
        // Loại bỏ "_" và thay thế từ khóa
        return keywordProcessor.replaceKeywords(input.trim().replace("_", " "));
    }

    // Phương thức đọc rules từ file JSON
    private static Map<String, List<String>> loadRules(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Lấy tệp từ thư mục resources
        InputStream inputStream = TextProcessor.class.getClassLoader()
                .getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new FileNotFoundException("File không tồn tại trong resources: " + filePath);
        }
        return objectMapper.readValue(inputStream, new TypeReference<Map<String, List<String>>>() {
        });
    }
}