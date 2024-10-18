package com.afroz.quizservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.afroz.quizservice.dao.QuizDao;
import com.afroz.quizservice.feign.QuestionServiceFeign;
import com.afroz.quizservice.model.QuestionWrapper;
import com.afroz.quizservice.model.Quiz;
import com.afroz.quizservice.model.Response;

@Service
public class QuizService {

    @Autowired
    private QuizDao quizDao;
    
    @Autowired
    private QuestionServiceFeign questionServiceFeign;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
    	
    		List<Integer> questionIds=questionServiceFeign.generateQuestions(category, numQ).getBody();

//        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
    		
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questionIds);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        
        List<QuestionWrapper> questionWrappers=
        		questionServiceFeign.getQuestions(quiz.getQuestions()).getBody();
        

        return new ResponseEntity<>(questionWrappers, HttpStatus.OK);

    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        
        int right=questionServiceFeign.getSore(responses).getBody();
        
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
