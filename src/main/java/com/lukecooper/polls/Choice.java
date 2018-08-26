package com.lukecooper.polls;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Choice {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Question question;

    private String choice;

    private long votes;

    private Choice() { }

    public Choice(final Question question, final String choice, final long votes) {
        this.question = question;
        this.choice = choice;
        this.votes = votes;
    }

    public Choice(final String choice) {
        this(null, choice, 0);
    }

    public static Choice from(Question question, Choice choice) {
        return new Choice(question, choice.getChoice(), choice.getVotes());
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public String getChoice() {
        return choice;
    }

    public Long getVotes() {
        return votes;
    }
}
