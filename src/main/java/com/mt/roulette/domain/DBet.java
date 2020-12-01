package com.mt.roulette.domain;

public class DBet {
    private int id;
    private int rouletteId;
    private int userId;
    private double moneyBet;
    private String chosenValue;
    private boolean betByNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRouletteId() {
        return rouletteId;
    }

    public void setRouletteId(int rouletteId) {
        this.rouletteId = rouletteId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getMoneyBet() {
        return moneyBet;
    }

    public void setMoneyBet(double moneyBet) {
        this.moneyBet = moneyBet;
    }

    public String getChosenValue() {
        return chosenValue;
    }

    public void setChosenValue(String chosenValue) {
        this.chosenValue = chosenValue;
    }

    public boolean getBetByNumber() {
        return betByNumber;
    }

    public void setBetByNumber(boolean betByNumber) {
        this.betByNumber = betByNumber;
    }
}
