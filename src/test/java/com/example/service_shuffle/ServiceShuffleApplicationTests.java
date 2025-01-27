package com.example.service_shuffle;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class ServiceShuffleApplicationTests{

	@Value("${log.service.url}")
	private String logServiceUrl;

	@Test
	public void testShuffle_validInput() {
		RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
		ShuffleController controller = new ShuffleController(restTemplateMock);

		int inputNumber = 5;
		ResponseEntity<List<Integer>> response = controller.shuffle(inputNumber);

		List<Integer> result = response.getBody();

		// Checking size and ensuring no duplicates
		assertNotNull(result);
		assertEquals(5, result.size());
		assertTrue(result.containsAll(List.of(1, 2, 3, 4, 5)));
	}

	@Test
	public void testShuffle_invalidInput() {
		RestTemplate restTemplateMock = Mockito.mock(RestTemplate.class);
		ShuffleController controller = new ShuffleController(restTemplateMock);

		int invalidNumber = 1001;

		// Expecting IllegalArgumentException
		Exception exception = assertThrows(IllegalArgumentException.class, () -> controller.shuffle(invalidNumber));
		assertEquals("Number must be between 1 and 1000", exception.getMessage());
	}


}
