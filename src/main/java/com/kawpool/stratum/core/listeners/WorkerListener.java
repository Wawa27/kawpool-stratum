package com.kawpool.stratum.core.listeners;

public interface WorkerListener {

    void onStart();

    void onSubscribed();

    void onStop();
}
