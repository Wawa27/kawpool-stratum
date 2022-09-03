package com.kawpool.stratum.ravend.listeners;

public interface ShareValidationListener {

    void onValidShare(String minerAddress, String workerName, String target);

    void onInvalidShare(String minerAddress, String workerName, String target);
}
