package com.example.kafka_demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class KafkaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoApplication.class, args);
	}

	@KafkaListener(topics = "my-topic", groupId = "group1")
	public void listenGroupFoo(String message) {
		log.info("Consumer1: Received Message in group foo" + message);
	}

	@KafkaListener(topics = "my-topic", groupId = "group1")
	public void listenGroupFoox1(String message) {
		log.info("Consumer2: Received Message in group foo: " + message);
	}
}
