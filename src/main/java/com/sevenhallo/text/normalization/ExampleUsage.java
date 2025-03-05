package com.sevenhallo.text.normalization;

import java.io.IOException;
import java.io.PrintStream;

public class ExampleUsage {
    public static void main(String[] args) throws IOException {
        // Tạo một TextProcessor với file rules
        TextProcessor textProcessor = new TextProcessor();

        // Văn bản đầu vào
        String input = "điều hoà";

        // Xử lý văn bản
        String result = textProcessor.processText(input);

        // Kết quả
        System.setOut(new PrintStream(System.out, true, "UTF-8"));
        System.out.println("Đầu vào: " + input);
        System.out.println("Kết quả: " + result);
    }
}