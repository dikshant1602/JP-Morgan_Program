package com.jpmc.midascore.kafka;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Component
public class TransactionListener {

    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    public TransactionListener(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
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

        sender.getBalance().setAmount(sender.getBalance().getAmount() - amount);
        recipient.getBalance().setAmount(recipient.getBalance().getAmount() + amount);

        TransactionRecord record = new TransactionRecord((double) amount, sender, recipient);
        transactionRecordRepository.save(record);
    }
}