package com.jpmc.midascore.config;

import com.jpmc.midascore.foundation.Transaction;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, Transaction> transactionConsumerFactory(KafkaProperties properties) {
        Map<String, Object> props = properties.buildConsumerProperties(null);

        JsonDeserializer<Transaction> deserializer = new JsonDeserializer<>(Transaction.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        return new DefaultKafkaConsumerFactory<>(
            props,
            new StringDeserializer(),
            deserializer
        );
    }

    // --- THIS METHOD HAS BEEN UPDATED ---
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Transaction> transactionListenerContainerFactory(
            ConsumerFactory<String, Transaction> transactionConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, Transaction> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(transactionConsumerFactory);
        return factory;
    }
}
