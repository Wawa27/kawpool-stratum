package com.kawpool.stratum.core;

import com.kawpool.stratum.core.configuration.Configuration;
import com.kawpool.stratum.core.rpc.*;
import com.kawpool.stratum.core.services.WorkerService;
import com.kawpool.stratum.ravend.models.ShareSubmission;
import com.kawpool.stratum.shared.jobs.Job;
import com.kawpool.stratum.shared.rpc.RpcNotifyDto;
import com.kawpool.stratum.shared.rpc.RpcRequestDto;
import org.apache.commons.codec.DecoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import static com.kawpool.stratum.KawpoolStratum.GSON;

public class WorkerThread extends Thread {
    private static final Logger LOGGER = LogManager.getLogger(WorkerThread.class);

    private Job currentJob;
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;
    private boolean keepAlive;
    private RpcMethodHandler chainMethodHandler;
    private final String workerId;
    private String workerName;
    private BigInteger reportedHashRate;
    private BigInteger reportedMinerId;

    /**
     * The total count of warning, if it exceeds a certain amount, the miner might be kicked and or blocklisted
     */
    private int warning;

    /**
     * We store miner's list of attempts to make sure they don't resubmit old ones
     */
    private List<ShareSubmission> submitAttempts;

    /**
     * Miner's address
     */
    private String address;
    private BigDecimal difficulty;

    public WorkerThread(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true);
        this.keepAlive = true;
        this.initializeMethodChain();
        this.workerId = Integer.toHexString((int) Math.floor(Math.random() * Integer.MAX_VALUE));
        this.warning = 0;
        this.address = "";
        this.workerName = "";
        this.difficulty = BigDecimal.valueOf(Configuration.getInitialDifficulty());
        this.submitAttempts = new ArrayList<>();
    }

    private void initializeMethodChain() {
        this.chainMethodHandler = new SubscribeRpcMethodHandler();
        this.chainMethodHandler.addAllMethods(new RpcMethodHandler[]{
                new AuthorizeRpcMethodHandler(),
                new ExtraNonceSubscribeRpcMethodHandler(),
                new SubmitRpcMethodHandler(),
                new SubmitHashRateMethodHandler(),
        });
    }

    @Override
    public void run() {
        try {
            while (keepAlive) {
                try {
                    String command = this.reader.readLine();
                    if (command != null) {
                        handleMessage(command);
                    } else {
                        disconnect();
                    }
                } catch (SocketTimeoutException e) {
                    LOGGER.info("Miner timeout {}", workerId);
                    LOGGER.info("Block {}", currentJob.getBlock().getDataHexadecimal());
                    LOGGER.error(e.getMessage());
                    disconnect();
                }
            }
        } catch (IOException | InterruptedException | DecoderException | RuntimeException e) {
            LOGGER.error(e.getMessage());
            disconnect();
        }
    }

    public void notifyNewJob(Job job) {
        this.submitAttempts.clear();
        this.currentJob = job;
        this.sendMessage(new RpcNotifyDto((int) Math.floor(Math.random() * Integer.MAX_VALUE), "mining.notify", job));
    }

    public void handleMessage(String message) throws UnsupportedRpcMethodException, InterruptedException, IOException, DecoderException {
        LOGGER.info("Received from {} : {}", workerId, message);
        chainMethodHandler.handle(this, GSON.fromJson(message, RpcRequestDto.class));
    }

    public void sendMessage(Object message) {
        String responseJson = GSON.toJson(message);
        LOGGER.info("Sending to {} : {}", workerId, responseJson);
        writer.println(responseJson);
    }

    private void disconnect() {
        try {
            LOGGER.info("Disconnecting miner (id: {}) ", workerId);
            socket.close();
            keepAlive = false;
            WorkerService.subscribedWorkers.remove(workerId);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void setChainMethodHandler(RpcMethodHandler chainMethodHandler) {
        this.chainMethodHandler = chainMethodHandler;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public BigInteger getReportedHashRate() {
        return reportedHashRate;
    }

    public void setReportedHashRate(BigInteger reportedHashRate) {
        this.reportedHashRate = reportedHashRate;
    }

    public BigInteger getReportedMinerId() {
        return reportedMinerId;
    }

    public void setReportedId(BigInteger reportedMinerId) {
        this.reportedMinerId = reportedMinerId;
    }

    public void setSubmitAttempts(List<ShareSubmission> submitAttempts) {
        this.submitAttempts = submitAttempts;
    }

    public void setDifficulty(BigDecimal difficulty) {
        this.difficulty = difficulty;
    }

    public RpcMethodHandler getChainMethodHandler() {
        return chainMethodHandler;
    }

    public List<ShareSubmission> getSubmitAttempts() {
        return submitAttempts;
    }

    public BigDecimal getDifficulty() {
        return difficulty;
    }

    public String getWorkerId() {
        return workerId;
    }

    public String getWorkerName() {
        return workerName;
    }

    private void setCurrentJob(Job currentJob) {
        this.currentJob = currentJob;
    }

    public Job getCurrentJob() {
        return currentJob;
    }

    public Socket getSocket() {
        return socket;
    }

    public BufferedReader getReader() {
        return reader;
    }

    public PrintWriter getWriter() {
        return writer;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    public RpcMethodHandler getMethod() {
        return chainMethodHandler;
    }

    public void setMethod(RpcMethodHandler rpcMethodHandler) {
        this.chainMethodHandler = rpcMethodHandler;
    }

    public RpcMethodHandler getRpcMethod() {
        return chainMethodHandler;
    }

    public void setRpcMethod(RpcMethodHandler rpcMethodHandler) {
        this.chainMethodHandler = rpcMethodHandler;
    }

    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
