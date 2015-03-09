package com.spring.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author DEEPAK
 */


public class PollsWR {

	
	private int id;
	@NotEmpty @NotNull
	private String question;
	@NotEmpty @NotNull
	private String[] choices;
	private String expired_at;
	@NotEmpty @NotNull
	private String started_at;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getExpiredat() {
		return expired_at;
	}
	public void setExpiredat(String expired_at) {
		this.expired_at = expired_at;
	}
	public String getStartat() {
		return started_at;
	}
	public void setStartat(String started_at) {
		this.started_at = started_at;
	}

	public void setChoice(String[] choices) {
		this.choices = choices;
	}

	public String[] getChoice() {
		return this.choices;
	}

}
