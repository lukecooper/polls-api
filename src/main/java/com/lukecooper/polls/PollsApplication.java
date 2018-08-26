package com.lukecooper.polls;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PollsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PollsApplication.class, args);
	}

	@Bean
    CommandLineRunner init(QuestionRepository questionRepository, ChoiceRepository choiceRepository) {
		return args -> {
            Question question = questionRepository.save(new Question("Favourite programming language?"));
            choiceRepository.save(new Choice(question, "Swift", 2048));
            choiceRepository.save(new Choice(question, "Python", 1024));
            choiceRepository.save(new Choice(question, "Objective-C", 512));
            choiceRepository.save(new Choice(question, "Ruby", 256));
		};
	}
}
