package com.jpmc.midascore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TransactionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserRecord sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserRecord recipient;

    public TransactionRecord() {}

    public TransactionRecord(Double amount, UserRecord sender, UserRecord recipient) {
        this.amount = amount;
        this.sender = sender;
        this.recipient = recipient;
    }
    
    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public UserRecord getSender() { return sender; }
    public void setSender(UserRecord sender) { this.sender = sender; }
    public UserRecord getRecipient() { return recipient; }
    public void setRecipient(UserRecord recipient) { this.recipient = recipient; }
}