package com.jpmc.midascore.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Balance;
import com.jpmc.midascore.repository.UserRepository;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final UserRepository userRepository;

    public BalanceController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Balance getBalance(@RequestParam Long userId) {
        Optional<UserRecord> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
            // If the user is found, return their actual balance
            return userOpt.get().getBalance();
        } else {
            // If user is not found, return a new Balance object with 0 amount
            Balance zeroBalance = new Balance();
            zeroBalance.setAmount(0);
            return zeroBalance;
        }
    }
}