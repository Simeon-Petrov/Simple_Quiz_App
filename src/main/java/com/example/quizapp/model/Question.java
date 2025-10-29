package com.example.quizapp.model;

import java.util.List;

public class Question {
    private String text;
    private List<String> options;
    private String correctAnswerText;

    public Question() {
    }

    public Question(String text, List<String> options, String correctAnswerText) {
        this.text = text;
        this.options = options;
        this.correctAnswerText = correctAnswerText;
    }

    public String getText() { return text; }
    public List<String> getOptions() { return options; }
    public String getCorrectAnswerText() { return correctAnswerText; }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setCorrectAnswerText(String correctAnswerText) {
        this.correctAnswerText = correctAnswerText;
    }
}