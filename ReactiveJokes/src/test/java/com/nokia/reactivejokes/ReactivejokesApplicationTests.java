package com.nokia.reactivejokes;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReactivejokesApplicationTests {
	
	@Autowired
    private WebTestClient webTestClient;

//	@Test
//	public void testGetJokesEndpoint() {
//	    System.out.println("1. Sending GET request to /jokes with count=5");
//
//	    webTestClient.get()
//	        .uri("/jokes?count=5")
//	        .accept(MediaType.APPLICATION_JSON)
//	        .exchange() 
//	        .expectStatus()
//	        .isOk()
//	        .expectBody()
//	        .consumeWith(response -> {
//	            System.out.println("2. Status is OK");
//	            System.out.println("3. Response body: " + new String(response.getResponseBody()));
//	        });
	//}
	
	@Test
	public void testGetJokesWithNegativeCount() {
	    System.out.println("Testing with negative count=-1");

	    webTestClient.get()
	        .uri("/jokes?count=-1")
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus()
	        .is5xxServerError()
	        .expectBody()
	        .consumeWith(response -> {
	            System.out.println("Response Body: " + new String(response.getResponseBody()));
	        });
}
	
	@Test
	public void testGetJokesWithoutCount() {
	    System.out.println("Testing without count parameter");

	    webTestClient.get()
	        .uri("/jokes")
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus()
	        .is5xxServerError()
	        .expectBody()
	        .consumeWith(response -> {
	            System.out.println("Response Body: " + new String(response.getResponseBody()));
	        });
	}
	
	
	@Test
	public void testGetJokesWithZeroCount() {
	    System.out.println("Testing with count=0");

	    webTestClient.get()
	        .uri("/jokes?count=0")
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus()
	        .is5xxServerError()
	        .expectBody()
	        .consumeWith(response -> {
	            System.out.println("Response Body: " + new String(response.getResponseBody()));
	        });
	}
	
	@Test
	public void testNonExistingEndpoint() {
	    System.out.println("Testing non-existing endpoint");

	    webTestClient.get()
	        .uri("/non-existing-endpoint")
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus()
	        .isNotFound()
	        .expectBody()
	        .consumeWith(response -> {
	            System.out.println("Response Body: " + new String(response.getResponseBody()));
	        });
	}
	
	@Test
	public void testWithNonPrperEndpoint() {
	    System.out.println("Testing non-existing endpoint");

	    webTestClient.get()
	        .uri("/jokescount=0")
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus()
	        .isNotFound()
	        .expectBody()
	        .consumeWith(response -> {
	            System.out.println("Response Body: " + new String(response.getResponseBody()));
	        });
	}
	
	@Test
	public void testGetJokesWithoutParam() {
	    System.out.println("Testing without count parameter");

	    webTestClient.get()
	        .uri("/jokes")
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus()
	        .is5xxServerError()
	        .expectBody()
	        .consumeWith(response -> {
	            System.out.println("Response Body: " + new String(response.getResponseBody()));
	        });
	
	
}
	
	@Test
	public void testGetJokesWithString() {
	    System.out.println("Testing with negative count=-1");

	    webTestClient.get()
	        .uri("/jokes?count=ten")
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus()
	        .isBadRequest()
	        .expectBody()
	        .consumeWith(response -> {
	            System.out.println("Response Body: " + new String(response.getResponseBody()));
	        });
}
	@Test
	public void testGetJokesInvalid() {
	    System.out.println("1. Sending GET request to /jokes with count=5");

	    webTestClient.get()
	        .uri("/jokes?count=")
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange() 
	        .expectStatus()
	       .is5xxServerError()
	        .expectBody()
	        .consumeWith(response -> {
	            System.out.println("2. Status is OK");
	            System.out.println("3. Response body: " + new String(response.getResponseBody()));
	        });
}
	
	@Test
	public void testGetJokesWithInvalidQueryParam() {
	    System.out.println("Testing with invalid query parameter");

	    webTestClient.get()
	        .uri("/jokes?invalidParam=5")
	        .accept(MediaType.APPLICATION_JSON)
	        .exchange()
	        .expectStatus()
	        .is5xxServerError()
	        .expectBody()
	        .consumeWith(response -> {
	            System.out.println("Response Body: " + new String(response.getResponseBody()));
	        });
}
	
//	@Test
//	public void testGetJokesWithUnsupportedMediaType() {
//	    System.out.println("Testing with unsupported media type");
//
//	    webTestClient.get()
//	        .uri("/jokes?count=5")
//	        .accept(MediaType.APPLICATION_XML)
//	        .exchange()
//	        .expectStatus()
//	        .is5xxServerError()
//	        .expectBody()
//	        .consumeWith(response -> {
//	            byte[] responseBody = response.getResponseBody();
//	            if (responseBody != null) {
//	                System.out.println("Response Body: " + new String(responseBody));
//	            } else {
//	                System.out.println("Response Body is null");
//	            }
//	        });
//	}
	
	
}




