package com.lukecooper.polls;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String question;

    @JsonProperty("published_at")
    private Date publishedAt;

    @OneToMany(mappedBy = "question")
    private List<Choice> choices = new ArrayList<>();

    private Question() { }

    public Question(final String question) {
        this.question = question;
        this.publishedAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public List<Choice> getChoices() {
        return choices;
    }
}
