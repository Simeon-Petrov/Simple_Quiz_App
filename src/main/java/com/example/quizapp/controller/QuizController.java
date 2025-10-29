package com.example.quizapp.controller;

import com.example.quizapp.model.Question;
import com.example.quizapp.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"submittedAnswers", "quizStartTime"})
public class QuizController {

    private final QuizService quizService;
    private static final long QUIZ_DURATION_SECONDS = 10 * 60;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @ModelAttribute("submittedAnswers")
    public Map<Integer, Integer> setUpSubmittedAnswers() {
        return new HashMap<>();
    }

    @GetMapping("/")
    public String displayQuiz(
            @RequestParam(value = "index", defaultValue = "0") int index,
            @ModelAttribute("submittedAnswers") Map<Integer, Integer> submittedAnswers,
            Model model,
            SessionStatus sessionStatus,
            HttpSession session) {

        Long quizStartTime = (Long) session.getAttribute("quizStartTime");

        if (quizStartTime == null) {
            quizStartTime = System.currentTimeMillis();
            session.setAttribute("quizStartTime", quizStartTime);
        }

        long elapsedMillis = System.currentTimeMillis() - quizStartTime;
        long remainingSeconds = QUIZ_DURATION_SECONDS - (elapsedMillis / 1000);

        if (remainingSeconds <= 0) {
            sessionStatus.setComplete();
            return "redirect:/submit";
        }

        List<Question> questions = quizService.getQuestions();

        if (index < 0 || index >= questions.size()) {
            return "redirect:/";
        }

        Question currentQuestion = questions.get(index);

        model.addAttribute("currentQuestion", currentQuestion);
        model.addAttribute("currentIndex", index);
        model.addAttribute("totalQuestions", questions.size());
        model.addAttribute("remainingSeconds", remainingSeconds);

        if (submittedAnswers.containsKey(index)) {
            model.addAttribute("selectedAnswer", submittedAnswers.get(index));
        }
        return "quiz-single-question";
    }

    @PostMapping("/answer")
    public String handleAnswer(
            @RequestParam("currentIndex") int currentIndex,
            @RequestParam(value = "answer", required = false) Integer selectedAnswer,
            @RequestParam("action") String action,
            @ModelAttribute("submittedAnswers") Map<Integer, Integer> submittedAnswers) {


        if (selectedAnswer != null) {
            submittedAnswers.put(currentIndex, selectedAnswer);
        }

        List<Question> questions = quizService.getQuestions();

        if ("submit".equals(action)) {
            return "redirect:/submit";
        }

        int nextIndex = currentIndex;

        if ("next".equals(action) && (currentIndex + 1) < questions.size()) {
            nextIndex = currentIndex + 1;
        } else if ("previous".equals(action) && (currentIndex - 1) >= 0) {
            nextIndex = currentIndex - 1;
        }

        return "redirect:/?index=" + nextIndex;
    }

    @GetMapping("/submit")
    public String submitQuiz(
            @ModelAttribute("submittedAnswers") Map<Integer, Integer> submittedAnswers,
            Model model,
            SessionStatus sessionStatus,
            HttpSession session) {

        Map<String, Object> quiResults = quizService.calculateResult(submittedAnswers);

        int score = (int) quiResults.get("score");
        int totalQuestions = (int) quiResults.get("totalQuestions");

        List<Map<String, Object>> incorrectAnswers = (List<Map<String, Object>>) quiResults.get("incorrectAnswers");

        session.removeAttribute("quizStartTime");
        sessionStatus.setComplete();

        String message;
        if (score == totalQuestions) {
            message = "Great job!";
        } else if (score >= (totalQuestions / 2 + 1)) {
            message = "You passed!";
        } else {
            message = "Try again!";
        }

        model.addAttribute("score", score);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("message", message);

        model.addAttribute("incorrectAnswers", incorrectAnswers);

        return "results";
    }
}