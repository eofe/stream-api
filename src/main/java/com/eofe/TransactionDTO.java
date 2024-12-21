package com.eofe;

import java.time.LocalDateTime;

public class TransactionDTO {

    private Long id;

    private String cardNumber;

    private Double amount;

    private LocalDateTime timestamp;

    public TransactionDTO(Long id, LocalDateTime timestamp, Double amount, String cardNumber) {
        this.id = id;
        this.timestamp = timestamp;
        this.amount = amount;
        this.cardNumber = cardNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public static TransactionDTO map(Transaction tx){
        return new TransactionDTO(tx.getId(), tx.getTimestamp(), tx.getAmount(), tx.getCardNumber());
    }
}
