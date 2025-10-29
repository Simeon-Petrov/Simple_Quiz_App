package com.example.quizapp.service;

import com.example.quizapp.model.Question;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    private final List<Question> questions;

    public QuizService() {
        List<Question> initialQuestions = loadQuestionsFromJson();

        shuffleQuiz(initialQuestions);

        this.questions = initialQuestions;
    }

    private List<Question> loadQuestionsFromJson() {
        ObjectMapper mapper = new ObjectMapper();

        InputStream inputStream = getClass().getResourceAsStream("/quiz.json");

        if (inputStream == null) {
            System.err.println("Error: quiz.json file not found in resources folder! Returning empty list.");
            return Collections.emptyList();
        }

        try {
            List<Question> questions = mapper.readValue(inputStream, new TypeReference<List<Question>>() {});
            System.out.println("Questions loaded successfully from quiz.json: " + questions.size());
            return questions;
        } catch (IOException e) {
            System.err.println("Error processing JSON file: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private void shuffleQuiz(List<Question> listToShuffle) {
        Collections.shuffle(listToShuffle);

        for (Question question : listToShuffle) {
            Collections.shuffle(question.getOptions());
        }
    }

    public List<Question> getQuestions() {
        return questions;
    }


    public Map<String, Object> calculateResult(Map<Integer, Integer> submittedAnswers) {
        int score = 0;
        List<Map<String, Object>> incorrectAnswersInfo = new ArrayList<>();

        List<Question> currentQuestions = this.questions;

        for (Map.Entry<Integer, Integer> entry : submittedAnswers.entrySet()) {
            Integer questionIndex = entry.getKey();
            Integer selectedOptionIndex = entry.getValue();

            if (questionIndex >=  0 && questionIndex < currentQuestions.size()) {
                Question question = currentQuestions.get(questionIndex);

                String selectedAnswerText = question.getOptions().get(selectedOptionIndex);

                if (question.getCorrectAnswerText().equals(selectedAnswerText)) {
                    score++;
                } else {

                    Map<String, Object> errorInfo = new HashMap<>();
                    errorInfo.put("questionText", question.getText());
                    errorInfo.put("options", question.getOptions());
                    errorInfo.put("selectedAnswer", selectedAnswerText);
                    errorInfo.put("correctAnswer", question.getCorrectAnswerText());

                    incorrectAnswersInfo.add(errorInfo);
                }
            }
        }

        Map<String, Object> results = new HashMap<>();
        results.put("score", score);
        results.put("totalQuestions", currentQuestions.size());
        results.put("incorrectAnswers", incorrectAnswersInfo);

        return results;
    }
}