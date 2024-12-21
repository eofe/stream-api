package com.eofe;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MockData {

    public List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Transaction transaction = new Transaction();
            transaction.setCardNumber("CARD" + i);
            transaction.setAmount(Math.round(Math.random() * 1000 * 100.0) / 100.0);
            transaction.setTimestamp(randomDate());
            transactions.add(transaction);
        }
        return transactions;
    }

    private LocalDateTime randomDate() {
        long minDay = LocalDate.of(2020, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2023, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay).atStartOfDay();
    }
}
