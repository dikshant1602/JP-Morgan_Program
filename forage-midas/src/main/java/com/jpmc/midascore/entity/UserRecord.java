package com.jpmc.midascore.entity;

import com.jpmc.midascore.foundation.Balance;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class UserRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Balance balance;

    public UserRecord() {
    }

    public UserRecord(String name, float initialBalance) {
        this.name = name;
        Balance newBalance = new Balance();
        newBalance.setAmount(initialBalance);
        newBalance.setUser(this);
        this.balance = newBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Balance getBalance() {
        return balance;
    }

    public void setBalance(Balance balance) {
        this.balance = balance;
    }
}