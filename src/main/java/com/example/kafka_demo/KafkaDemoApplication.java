package com.example.kafka_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class KafkaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoApplication.class, args);
	}

	@KafkaListener(topics = "my-topic", groupId = "group1")
	public void listenGroupFoo(String message) {
		System.out.println("Consumer1: Received Message in group foo" + message);
	}

	@KafkaListener(topics = "my-topic", groupId = "group1")
	public void listenGroupFoox1(String message) {
		System.out.println("Consumer2: Received Message in group foo: " + message);
	}
}
