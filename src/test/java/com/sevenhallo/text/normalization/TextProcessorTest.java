package com.sevenhallo.text.normalization;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextProcessorTest {

    private TextProcessor textProcessor;
    private final String testFilePath = "data/test.txt"; // Đường dẫn tới file đầu vào
    private final String resultFilePath = "data/result.txt"; // Đường dẫn tới file mong đợi

    private List<String> inputLines;
    private List<String> expectedLines;

    @BeforeEach
    public void setUp() throws IOException {
        // Khởi tạo TextProcessor với rules từ resources
        textProcessor = new TextProcessor();

        // Truy cập file từ resources
        InputStream testFileStream = getClass().getClassLoader().getResourceAsStream(testFilePath);
        InputStream resultFileStream = getClass().getClassLoader().getResourceAsStream(resultFilePath);

        if (testFileStream == null || resultFileStream == null) {
            throw new IOException("Không thể tìm thấy file trong thư mục resources!");
        }

        // Đọc nội dung file đầu vào (test.txt)
        inputLines = new String(testFileStream.readAllBytes(), StandardCharsets.UTF_8)
                .lines()
                .toList();

        // Đọc nội dung file kết quả (result.txt)
        expectedLines = new String(resultFileStream.readAllBytes(), StandardCharsets.UTF_8)
                .lines()
                .toList();

        if (inputLines.size() != expectedLines.size()) {
            throw new IllegalStateException("Số lượng dòng giữa tệp đầu vào và tệp mong đợi không khớp!");
        }
    }

    @Test
    public void testAllLines() {
        // Kiểm tra tất cả các dòng thông qua vòng lặp
        for (int i = 0; i < inputLines.size(); i++) {
            String processedLine = textProcessor.processText(inputLines.get(i));
            System.out.println(processedLine);
            assertEquals(expectedLines.get(i), processedLine, "Dòng " + (i + 1) + " không khớp!");
        }
    }

    @Test
    public void testWordReplacementWithBoundaries() {
        KeywordProcessor processor = new KeywordProcessor();
        processor.addKeywordsFromDict("công ty", List.of("công ti"));
        processor.addKeywordsFromDict("lý", List.of("lí"));

        // Đầu vào chứa từ đúng ranh giới
        assertEquals("công ty nhỏ", processor.replaceKeywords("công ti nhỏ"));
        assertEquals("lý do không rõ", processor.replaceKeywords("lí do không rõ"));

        // Đầu vào không chứa từ đúng ranh giới
        assertEquals("công tincidunt", processor.replaceKeywords("công tincidunt")); // Không thay đổi

        // Đầu vào phức tạp với dấu câu
        assertEquals("công ty, lý do.", processor.replaceKeywords("công ti, lí do."));
    }

    @Test
    public void testTextProcessorIntegration() throws IOException {
        TextProcessor textProcessor = new TextProcessor();

        String input = "công ti nhỏ, lí do rõ ràng.";
        String expected = "công ty nhỏ, lý do rõ ràng.";
        assertEquals(expected, textProcessor.processText(input));
    }
}