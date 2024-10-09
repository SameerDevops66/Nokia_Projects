package com.nokia.reactivejokess;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactivejokessApplicationTests {
	
	@Autowired
    private WebTestClient webTestClient;

	@Test
	 public void testGetJokesEndpoint() {
        webTestClient.get()
            .uri("/jokes?count=5")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody()
            .jsonPath("$.length()").isEqualTo(1);
    }
}
