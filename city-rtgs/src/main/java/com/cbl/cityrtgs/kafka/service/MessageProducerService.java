package com.cbl.cityrtgs.kafka.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@ComponentScan
@Slf4j
public class MessageProducerService {
    private final KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    public void sendMessage(String topic, Object obj) {
        Map<String, Object> map = new HashMap<>();
        map.put("request", obj);
        // kafkaTemplate.send(topic, map);

        // Send message asynchronously
        ListenableFuture future = kafkaTemplate.send(topic, map);

        // Add callback for handling success or failure
        future.addCallback(new ListenableFutureCallback() {
            @Override
            public void onSuccess(Object result) {
                System.out.println("Message sent successfully: " + result);
                log.trace("Message sent successfully: " + result);
            }

            @Override
            public void onFailure(@NotNull Throwable ex) {
                System.err.println("Failed to send message: " + ex.getMessage());
                log.error("Failed to send message: " + ex.getMessage());
            }
        });
    }
}
