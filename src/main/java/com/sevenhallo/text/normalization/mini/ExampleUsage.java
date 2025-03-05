package com.sevenhallo.text.normalization.mini;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class ExampleUsage {
    public static void main(String[] args) {
        TextNormalizer normalizer = new TextNormalizer();
        String result = normalizer.normalize("điều hoà"); // Kết quả: "lẩy bẩy"
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(result);
    }
}
