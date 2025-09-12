package com.jpmc.midascore.kafka;

import java.util.Optional;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;

@Component
public class TransactionListener {

    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;
    private final RestTemplate restTemplate;

    public TransactionListener(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core", containerFactory = "transactionListenerContainerFactory")
    public void listen(Transaction transaction) {
        Optional<UserRecord> senderOpt = userRepository.findById(transaction.getSenderId());
        Optional<UserRecord> recipientOpt = userRepository.findById(transaction.getRecipientId());

        if (senderOpt.isEmpty() || recipientOpt.isEmpty()) {
            return;
        }

        UserRecord sender = senderOpt.get();
        UserRecord recipient = recipientOpt.get();
        float amount = transaction.getAmount();

        if (sender.getBalance().getAmount() < amount) {
            return;
        }

        // --- NEW LOGIC: Call the Incentives API ---
        String incentiveApiUrl = "http://localhost:8080/incentive";
        Incentive incentive = restTemplate.postForObject(incentiveApiUrl, transaction, Incentive.class);
        double incentiveAmount = incentive.getAmount();

        // --- UPDATE BALANCE LOGIC ---
        sender.getBalance().setAmount(sender.getBalance().getAmount() - amount);
        recipient.getBalance().setAmount(recipient.getBalance().getAmount() + amount + (float)incentiveAmount); // Add incentive to recipient

        TransactionRecord record = new TransactionRecord((double) amount, incentiveAmount, sender, recipient);
        transactionRecordRepository.save(record);
    }
}