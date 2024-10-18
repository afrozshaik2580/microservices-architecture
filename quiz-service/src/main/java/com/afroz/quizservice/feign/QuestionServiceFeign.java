package com.afroz.quizservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.afroz.quizservice.model.QuestionWrapper;
import com.afroz.quizservice.model.Response;

@FeignClient("QUESTION-SERVICE")
public interface QuestionServiceFeign {

	
	@PostMapping("question/generate")
    public ResponseEntity<List<Integer>> generateQuestions(@RequestParam String category, @RequestParam Integer numQuestions);
    
    @PostMapping("question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestions(@RequestBody List<Integer> questionIds);
    
    @PostMapping("question/getScore")
    public ResponseEntity<Integer> getSore(@RequestBody List<Response> responses);
}
