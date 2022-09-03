package com.kawpool.stratum.payment;

public abstract class PaymentService {

    public PaymentService() {

    }

    public abstract void onShareFound(String minerAddress, String workerName, String target);

    public abstract void onBlockFound(String address, long coinbaseValue);
}
