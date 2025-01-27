package com.example.service_shuffle;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/v1")
public class ShuffleController {

    private final RestTemplate restTemplate;

    @Value("${log.service.url}")
    private String logServiceUrl;

    public ShuffleController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/shuffle")
    public ResponseEntity<List<Integer>> shuffle(@RequestBody int number) {
        // Валідація вхідного числа
        if (number < 1 || number > 1000) {
            throw new IllegalArgumentException("Number must be between 1 and 1000");
        }

        // Генеруємо список чисел від 1 до number
        List<Integer> numbers = IntStream.rangeClosed(1, number)
                .boxed()
                .collect(Collectors.toList());

        // Перемішуємо список
        Collections.shuffle(numbers);

        // Відправляємо лог-запит асинхронно
        sendLogRequestAsync(number);

        // Повертаємо результат
        return ResponseEntity.ok(numbers);
    }

    @Async
    public void sendLogRequestAsync(int number) {
        try {
            // Створюємо повідомлення для логування
            String logMessage = "Shuffled array for number: " + number;

            // Формуємо URL через UriComponentsBuilder
            String url = UriComponentsBuilder
                    .fromHttpUrl(logServiceUrl) // Базовий URL
                    .path("/api/v1/log")        // Додаємо шлях
                    .toUriString();

            // Логування URL для дебагу
            System.out.println("Sending POST request to: " + url);

            // Виконуємо POST запит до service-log
            restTemplate.postForObject(url, logMessage, Void.class);
        } catch (Exception e) {
            // Логування помилок у разі невдачі
            System.err.println("Failed to send log request: " + e.getMessage());
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        // Обробка IllegalArgumentException і повернення 400 Bad Request
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
