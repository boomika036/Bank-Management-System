package model;

public class Transaction {

    private final String type;
    private final double amount;
    private final int otherAccountId;

    public Transaction(String type, double amount, int otherAccountId) {
        this.type = type;
        this.amount = amount;
        this.otherAccountId = otherAccountId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public int getOtherAccountId() {
        return otherAccountId;
    }
}