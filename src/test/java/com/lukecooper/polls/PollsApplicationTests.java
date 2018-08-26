package com.lukecooper.polls;

import java.net.URI;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PollsApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + this.port;
    }

	@Test
	public void controllerStatusShouldReturnOK() throws Exception {
        ResponseEntity<List> entity = this.restTemplate.getForEntity(getBaseUrl() + "/questions", List.class);
		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

    @Test
    public void getQuestionsShouldReturnInitialValue() throws Exception {
        assertThat(this.restTemplate.getForObject(getBaseUrl() + "/questions", String.class))
                .contains("Favourite programming language?");
    }

    @Test
    public void getQuestionShouldReturnInitialValue() throws Exception {
        assertThat(this.restTemplate.getForObject(getBaseUrl() + "/questions/1", String.class))
                .contains("Favourite programming language?");
    }

    @Test
    public void postQuestionShouldReturnCorrectValue() throws Exception {
        HttpEntity<Question> request = new HttpEntity<>(new Question("new question"));
        Question question = restTemplate.postForObject(getBaseUrl() + "/questions", request, Question.class);
        assertThat(question).isNotNull();
        assertThat(question.getQuestion()).isEqualToIgnoringCase("new question");
    }

    @Test
    public void postQuestionShouldReturnLocation() throws Exception {
        HttpEntity<Question> request = new HttpEntity<>(new Question("new question"));
        URI location = restTemplate.postForLocation(getBaseUrl() + "/questions", request);
        assertThat(location).isNotNull();
    }

}
