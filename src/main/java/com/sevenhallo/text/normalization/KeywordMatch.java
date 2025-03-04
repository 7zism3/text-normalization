package com.sevenhallo.text.normalization;

class KeywordMatch {
    private final String matchedKeyword;
    private final String replacedKeyword;
    private final int start;

    public KeywordMatch(String matchedKeyword, String replacedKeyword, int start) {
        this.matchedKeyword = matchedKeyword;
        this.replacedKeyword = replacedKeyword;
        this.start = start;
    }

    public String getMatchedKeyword() {
        return matchedKeyword;
    }

    public String getReplacedKeyword() {
        return replacedKeyword;
    }

    public int getStart() {
        return start;
    }

    @Override
    public String toString() {
        return "KeywordMatch{" +
                "matchedKeyword='" + matchedKeyword + '\'' +
                ", replacedKeyword='" + replacedKeyword + '\'' +
                ", start=" + start +
                '}';
    }
}
