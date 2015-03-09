package com.spring.model;

/**
 * @author DEEPAK
 */

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

public class Polls {

	
	private int id;
	@NotEmpty @NotNull
	private String question;
	private String expired_at;
	@NotEmpty @NotNull
	private String started_at;
	@NotEmpty @NotNull
	private String[] choices;
	private int[]results={0,0};

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

	public int[] getResults() {
		return this.results;
	}

	public void setResults(int[] res) {
		this.results=res;
	}
}
