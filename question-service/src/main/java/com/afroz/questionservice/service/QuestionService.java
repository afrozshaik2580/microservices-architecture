package com.afroz.questionservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.afroz.questionservice.dao.QuestionDao;
import com.afroz.questionservice.model.Question;
import com.afroz.questionservice.model.QuestionWrapper;
import com.afroz.questionservice.model.Response;

@Service
public class QuestionService {
    @Autowired
    private QuestionDao questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

	public List<Integer> generateQuestions(String category, Integer numQuestions) {
		List<Integer> questionIds=questionDao.findRandomQuestionsByCategory(category, numQuestions);
		return questionIds;
	}

	public List<QuestionWrapper> getQuestionsByIds(List<Integer> questionIds) {
		List<QuestionWrapper> wrappers=new ArrayList<QuestionWrapper>();
		
		for(Integer i: questionIds) {
			Question q=questionDao.findById(i).get();
			
			QuestionWrapper qw=new QuestionWrapper();
			qw.setQuestionTitle(q.getQuestionTitle());
			qw.setOption1(q.getOption2());
			qw.setOption2(q.getOption2());
			qw.setOption3(q.getOption3());
			qw.setOption4(q.getOption4());
			qw.setId(q.getId());
			
			wrappers.add(qw);
		}
		
		return wrappers;
	}

	public Integer getScore(List<Response> responses) {
        int right = 0;
        for(Response response : responses){
        	
        		Question q=questionDao.findById(response.getId()).get();
            if(response.getResponse().equals(q.getRightAnswer()))
                right++;

        }
        return right;
	}
}
