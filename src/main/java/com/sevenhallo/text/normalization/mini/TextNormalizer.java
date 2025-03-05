package com.sevenhallo.text.normalization.mini;

import java.util.*;
import java.util.regex.Pattern;

public class TextNormalizer {
    private final Map<String, String> keywordMap;

    public TextNormalizer() {
        this.keywordMap = new HashMap<>();
        initializeRules();
    }

    private void initializeRules() {
        // Quy tắc chuyển đổi nguyên âm
        addRule("òa", "oà");
        addRule("óa", "oá");
        addRule("ỏa", "oả");
        addRule("õa", "oã");
        addRule("ọa", "oạ");

        addRule("òe", "oè");
        addRule("óe", "oé");
        addRule("ỏe", "oẻ");
        addRule("õe", "oẽ");
        addRule("ọe", "oẹ");

        addRule("ùy", "uỳ");
        addRule("úy", "uý");
        addRule("ủy", "uỷ");
        addRule("ũy", "uỹ");
        addRule("ụy", "uỵ");

        addRule("ùa", "uà");
        addRule("úa", "uá");
        addRule("ủa", "uả");
        addRule("ũa", "uã");
        addRule("ụa", "uạ");

        // Quy tắc thay thế từ
        addRule("công ty", "công ti");
        addRule("lý", "lí");
        addRule("xảy", "xẩy");
        addRule("bảy", "bẩy");
        addRule("gãy", "gẫy");
        addRule("tham công tiếc việc", "tham công tyếc việc");

        // Xử lý trường hợp "lẩy bẩy"
        addRule("lẩy bẩy", "lẩy bảy");
        addRule("lẩy bẩy", "lảy bảy");
    }

    private void addRule(String correct, String incorrect) {
        keywordMap.put(incorrect, correct);
    }

    public String normalize(String text) {
        if (text == null || text.trim().isEmpty()) {
            return text;
        }

        text = text.trim().replace("_", " ");

        // Sắp xếp các từ khóa theo độ dài (ngắn nhất trước)
        List<Map.Entry<String, String>> sortedEntries = keywordMap.entrySet()
                .stream()
                .sorted((a, b) -> a.getKey().length() - b.getKey().length())
                .toList();

        // Thay thế từng từ khóa
        for (Map.Entry<String, String> entry : sortedEntries) {
            String keyword = entry.getKey();
            String replacement = entry.getValue();
            String regex = Pattern.quote(keyword) + "(?=([.,!?\\s]|$))";
            text = text.replaceAll(regex, replacement);
        }

        return text;
    }

    // Class phụ trợ để lưu thông tin về các từ khóa tìm thấy
    public static class KeywordMatch {
        private final String keyword;
        private final String replacement;
        private final int position;

        public KeywordMatch(String keyword, String replacement, int position) {
            this.keyword = keyword;
            this.replacement = replacement;
            this.position = position;
        }

        public String getKeyword() { return keyword; }
        public String getReplacement() { return replacement; }
        public int getPosition() { return position; }
    }

    // Phương thức tìm kiếm các từ khóa trong văn bản
    public List<KeywordMatch> findKeywords(String text) {
        List<KeywordMatch> matches = new ArrayList<>();
        for (Map.Entry<String, String> entry : keywordMap.entrySet()) {
            String keyword = entry.getKey();
            int index = text.indexOf(keyword);
            while (index != -1) {
                matches.add(new KeywordMatch(keyword, entry.getValue(), index));
                index = text.indexOf(keyword, index + 1);
            }
        }
        return matches;
    }
}
