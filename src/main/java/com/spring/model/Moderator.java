package com.spring.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.net.URL;
import java.util.Date;



@SuppressWarnings({ "unused", "serial" })
public class Moderator implements Serializable{

	private int id;
	private String createdDate;
	@NotEmpty @NotNull @Email
	private String emailId;
	@NotEmpty @NotNull @Size(min=2)
	private String password;
	@NotEmpty @NotNull @Size(min=2, max=30)
	private String name;
	
	public int getId() {
		return id;
	}
	
	public String getEmailId() {
		return emailId;
	}
	
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public void setId(int Id) {
		this.id = Id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setCreatedDate(String Date) {
		this.createdDate = Date;
	}
	public String getCreatedDate() {
		return this.createdDate;
	}
	
	

	
}