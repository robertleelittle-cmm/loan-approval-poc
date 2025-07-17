package com.example.loan;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoanApplication {
    @JsonProperty("income")
    private double income;

    @JsonProperty("creditScore")
    private int creditScore;

    @JsonProperty("loanAmount")
    private double loanAmount;

    @JsonProperty("decision")
    private String decision;

    // Default constructor for serialization
    public LoanApplication() {}

    // Constructor for convenience
    public LoanApplication(double income, int creditScore, double loanAmount) {
        this.income = income;
        this.creditScore = creditScore;
        this.loanAmount = loanAmount;
    }

    // Getters and Setters
    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}