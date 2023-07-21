package com.ronellyson.smart_fast_food.data.model;

import com.ronellyson.smart_fast_food.data.model.contracts.PaymentMethod;

import java.math.BigDecimal;

public class CreditDebitCard implements PaymentMethod {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String securityCode;
    private Boolean isCredit;
    private Boolean isSelected;


    public CreditDebitCard(String cardNumber, String cardHolderName, String expirationDate, String securityCode, Boolean isCredit) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.securityCode = securityCode;
        this.isCredit = isCredit;
        this.isSelected = false;
    }

    @Override
    public void makePayment(BigDecimal amount) {
        // Logic to process payment with credit or debit card
        String paymentType = isCredit ? "Credit" : "Debit";
        System.out.println("Payment of " + paymentType + " of $" + amount + " successfully processed with card " + cardNumber);
    }

    // Getter methods for the attributes
    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public Boolean isCredit() {
        return isCredit;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setIsSelected(Boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        String lastFourDigits = "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        String cardType = isCredit ? "Crédito" : "Débito";

        return "  Número do Cartão: '" + lastFourDigits + '\'' +
                "\n  Nome do Titular: '" + cardHolderName + '\'' +
                "\n  Data de Expiração: '" + expirationDate + '\'' +
                "\n  Tipo: " + cardType;
    }
}
