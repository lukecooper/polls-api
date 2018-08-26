package com.lukecooper.polls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/questions")
public class ChoiceRestController {

    private final ChoiceRepository choiceRepository;
    private final QuestionRepository questionRepository;

    @Autowired
    ChoiceRestController(ChoiceRepository choiceRepository, QuestionRepository questionRepository) {
        this.choiceRepository = choiceRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping
    Collection<Question> getQuestions() {
        return this.questionRepository.findAll();
    }

    @GetMapping("/{questionId}")
    Question getQuestion(@PathVariable Long questionId) {
        return this.questionRepository
                .findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException(questionId));
    }

    @PostMapping
    ResponseEntity<?> postQuestion(@RequestBody Question input) {
        Question result = this.questionRepository.save(new Question(input.getQuestion()));
        for(Choice choice : input.getChoices()) {
            this.choiceRepository.save(Choice.from(result, choice));
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity.created(location).body(result);
    }

}