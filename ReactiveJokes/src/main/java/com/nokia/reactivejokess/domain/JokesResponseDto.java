package com.nokia.reactivejokess.domain;

import java.util.UUID;

public class JokesResponseDto {
	
	private String id;
    private String question;
    private String answer;

    public JokesResponseDto(String id, String question, String answer) {
    	this.id = (id != null) ? id : UUID.randomUUID().toString();
        this.question = question;
        this.answer = answer;
    }


    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

}
