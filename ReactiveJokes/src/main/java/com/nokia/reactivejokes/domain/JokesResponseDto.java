package com.nokia.reactivejokes.domain;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "Represents a joke")
public class JokesResponseDto {
	
	@Schema(description = "Unique identifier of the joke", example = "a9d50357-8609-4138-8a5e-9cb453fbe85d")
	private String id;
	@Schema(description = "The joke question", example = "What did the ocean say to the shore?")
    private String question;
	@Schema(description = "The answer to the joke", example = "Nothing, it just waved.")
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
