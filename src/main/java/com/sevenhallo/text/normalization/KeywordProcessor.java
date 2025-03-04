package com.sevenhallo.text.normalization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class KeywordProcessor {
    private final Map<String, String> keywordMap;
    private Pattern keywordPattern;

    public KeywordProcessor() {
        this.keywordMap = new HashMap<>(); // Map từ khóa thay thế
    }


    public void addKeywordsFromDict(String key, List<String> keywords) {
        for (String keyword : keywords) {
            keywordMap.put(keyword, key);
        }
    }

    public List<KeywordMatch> extractKeywords(String text) {
        List<KeywordMatch> founds = new ArrayList<>();
        for (Map.Entry<String, String> entry : keywordMap.entrySet()) {
            String keyword = entry.getKey();
            int index = text.indexOf(keyword);
            while (index != -1) {
                founds.add(new KeywordMatch(keyword, entry.getValue(), index));
                index = text.indexOf(keyword, index + 1);
            }
        }
        return founds;
    }

//    public String replaceKeywords(String text) {
//        // Sắp xếp các từ khóa dựa trên độ dài, ưu tiên từ dài hơn
//        List<Map.Entry<String, String>> sortedEntries = keywordMap.entrySet()
//                .stream()
//                .sorted((a, b) -> b.getKey().length() - a.getKey().length()) // Sắp xếp từ khóa theo chiều dài giảm dần
//                .collect(Collectors.toList());
//
//        // Thay thế từng cụm từ theo thứ tự ưu tiên
//        for (Map.Entry<String, String> entry : sortedEntries) {
//            String keyword = entry.getKey();
//            String replacement = entry.getValue();
//
//            // Regex để thay thế từ khóa đứng độc lập (tránh nối tiếp các từ khác)
//            String regex = "(?<!\\S)" + Pattern.quote(entry.getKey()) + "(?=([.,!?\\s]|$))";
//            text = text.replaceAll(regex, replacement);
//        }
//        return text;
//    }

    public String replaceKeywords(String text) {
        for (Map.Entry<String, String> entry : keywordMap.entrySet()) {
            String keyword = entry.getKey();
            String replacement = entry.getValue();

            // Regex để thay thế từ khóa đứng độc lập (tránh nối tiếp các từ khác)
            String regex = Pattern.quote(entry.getKey()) + "(?=([.,!?\\s]|$))";
            text = text.replaceAll(regex, replacement);
        }
        return text;
    }

}
