package com.example.kafka_demo;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@Controller
@RequestMapping("kafka")
public class KafkaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaDemoApplication.class, args);
	}

	@PostConstruct
	public String testKafka() {
		String topic = "my-topic";
		String bootstrapServers = "127.0.0.1:9092";

		ExecutorService executor = Executors.newSingleThreadExecutor();
		MessageConsumerWorker worker = new MessageConsumerWorker(topic, KafkaConsumerConfig.defaultProps(bootstrapServers, "my-topic-consumer"), msg -> {
			// Custom processing
			System.out.println("Reciver: " + msg);
		}, 1000);
		executor.submit(worker);

		// shutdown hook
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			worker.shutdown();
			executor.shutdown();
		}));
		return "x";
	}
}
