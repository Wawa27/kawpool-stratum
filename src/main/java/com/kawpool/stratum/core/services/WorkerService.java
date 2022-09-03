package com.kawpool.stratum.core.services;

import com.kawpool.stratum.core.configuration.Configuration;
import com.kawpool.stratum.core.WorkerThread;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class WorkerService {
    public final static ConcurrentHashMap<String, WorkerThread> subscribedWorkers;

    static {
        subscribedWorkers = new ConcurrentHashMap<>();
    }

    public static Collection<WorkerThread> getWorkers() {
        return subscribedWorkers.values();
    }

    public static WorkerThread findWorkerById(String workerId) {
        return subscribedWorkers.get(workerId);
    }

    public static void disconnect(String workerId) {
        subscribedWorkers.remove(workerId);
    }

    public static void subscribe(String workerId) {

    }

    public static void warn(String workerId) {
        WorkerThread workerThread = findWorkerById(workerId);
        workerThread.setWarning(workerThread.getWarning() + 1);
        if (workerThread.getWarning() > Configuration.getInstance().getWarningUntilBan()) {
            WorkerService.ban(workerId);
        }
    }

    public static void ban(String workerId) {

    }
}
