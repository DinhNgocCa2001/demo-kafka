package com.example.kafka_demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class MessageConsumerWorker implements Runnable {
    private final KafkaConsumer<String, String> consumer;
    private final MessageHandler handler;
    private final int timeDelay;
    private volatile boolean running = true;

    public MessageConsumerWorker(String topic, Properties config, MessageHandler handler, int timeDelay) {
        this.consumer = new KafkaConsumer<>(config);
        this.handler = handler;
        consumer.subscribe(Collections.singletonList(topic));
        this.timeDelay = timeDelay;
    }

    @Override
    public void run() {
        try {
            while (running) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    handler.handle(record.value());
                    try {
                        Thread.sleep(timeDelay); // 🕓 Delay 500 milliseconds
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // restore interrupt status
                        break; // hoặc continue, tùy logic
                    }
                }
                consumer.commitSync(); // manual commit
            }
        } catch (WakeupException e) {
            // shutdown triggered
        } finally {
            consumer.close();
        }
    }

    public void shutdown() {
        running = false;
        consumer.wakeup(); // break poll() safely
    }
}
