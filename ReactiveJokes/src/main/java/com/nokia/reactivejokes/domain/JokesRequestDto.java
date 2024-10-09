package com.nokia.reactivejokes.domain;

import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table("jokes")
public class JokesRequestDto {
	
	@JsonProperty("id")
    private Integer id;

	@JsonProperty("setup")
    private String question;
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}
	
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@JsonProperty("punchline")
    private String answer;
	
	public JokesRequestDto(int i, String string, String string2) {
	   
	}
    
    @Override
	public String toString() {
		return "Jokes [id=" + id + ", question=" + question + ", answer=" + answer + "]";
	}


}
