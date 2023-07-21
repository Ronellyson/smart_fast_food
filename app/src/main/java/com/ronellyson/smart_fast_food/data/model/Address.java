package com.ronellyson.smart_fast_food.data.model;

public class Address {
    private String holder;
    private String street;
    private String number;
    private String neighborhood;
    private String zipCode;
    private String state;
    private String country;
    private String phone;
    private boolean isSelected;

    // Construtor
    public Address(String holder, String street, String number, String neighborhood,
                   String zipCode, String state, String country, String phone) {
        this.holder = holder;
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.zipCode = zipCode;
        this.state = state;
        this.country = country;
        this.phone = phone;
        this.isSelected = false;
    }

    // Getters e Setters
    public String getHolder() {
        return this.holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return this.neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String toString() {
        return "  Nome do Titular: '" + holder + '\'' +
                "\n  Rua: '" + street + '\'' +
                "\n  Número: '" + number + '\'' +
                "\n  Bairro: '" + neighborhood + '\'' +
                "\n  CEP: '" + zipCode + '\'' +
                "\n  Estado: '" + state + '\'' +
                "\n  País: '" + country + '\'' +
                "\n  Telefone: '" + phone + '\'';
    }
}

