package com.airtribe.ridewise.model;

import java.util.Date;

/**
 * @author Priyanka Pitla
 */
public class FareReceipt {

    private String rideId;
    private double amount;
    private Date generatedAt;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Date generatedAt) {
        this.generatedAt = generatedAt;
    }

    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    @Override
    public String toString() {
        return String.format("FareReceipt{amount=%.2f, rideId='%s', generatedAt=%s}",
                this.amount, this.rideId, this.generatedAt);
    }
}
