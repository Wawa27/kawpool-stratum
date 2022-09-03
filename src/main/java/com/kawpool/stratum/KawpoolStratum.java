package com.kawpool.stratum;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kawpool.stratum.core.JobDispatcherThread;
import com.kawpool.stratum.core.configuration.Configuration;
import com.kawpool.stratum.core.WorkerThread;
import com.kawpool.stratum.payment.PayPerLastNShareService;
import com.kawpool.stratum.payment.PaymentManager;
import com.kawpool.stratum.ravend.listeners.BlockListener;
import com.kawpool.stratum.ravend.RavendThread;
import com.kawpool.stratum.ravend.listeners.ShareValidationListener;
import com.kawpool.stratum.shared.jobs.Job;
import com.kawpool.stratum.core.adapters.JobAdapter;
import com.kawpool.stratum.statistics.StatisticsManager;
import org.apache.commons.codec.DecoderException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class KawpoolStratum {
    private static final Logger LOGGER = LogManager.getLogger(KawpoolStratum.class);
    public static Gson GSON;
    private static List<SocketAddress> blacklist;

    private static PaymentManager paymentManager;
    private static StatisticsManager statisticsManager;

    public static BigDecimal highestDifficultyShare;
    public static int numberOfZero;

    /**
     * Listeners
     */
    public final static List<ShareValidationListener> shareValidationListeners;
    public final static List<BlockListener> blockListeners;

    static {
        numberOfZero = 0;
        highestDifficultyShare = new BigDecimal(0);
        shareValidationListeners = new ArrayList<>();
        blockListeners = new ArrayList<>();
        try {
            paymentManager = new PaymentManager(new PayPerLastNShareService());
            statisticsManager = new StatisticsManager();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, DecoderException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        gsonBuilder.registerTypeAdapter(Job.class, new JobAdapter());
        GSON = gsonBuilder.create();
        blacklist = new ArrayList<>();

        ServerSocket serverSocket = new ServerSocket(3316);

        JobDispatcherThread jobDispatcherThread = new JobDispatcherThread();
        jobDispatcherThread.start();

        RavendThread ravendThread = new RavendThread();
        ravendThread.start();

        while (true) {
            LOGGER.log(Level.INFO, "Waiting for a new miner");
            Socket socket = serverSocket.accept();
            socket.setSoTimeout(Configuration.getInstance().getTimeout());
            if (blacklist.indexOf(socket.getRemoteSocketAddress()) > 0) {
                socket.close();
            }
            LOGGER.log(Level.INFO, "Miner connected : {}", socket.getRemoteSocketAddress());
            new WorkerThread(socket).start();
        }
    }

    public static void banMiner(WorkerThread worker) throws IOException {
        blacklist.add(worker.getSocket().getRemoteSocketAddress());
        worker.getSocket().close();
        worker.setKeepAlive(false);
    }

    public static void unbanIpAddress(SocketAddress address) {
        blacklist.remove(address);
    }
}
