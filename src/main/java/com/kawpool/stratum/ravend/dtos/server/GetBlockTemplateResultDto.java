package com.kawpool.stratum.ravend.dtos.server;

import com.google.gson.annotations.SerializedName;
import com.kawpool.stratum.shared.blocks.models.Block;
import com.kawpool.stratum.shared.blocks.models.BlockHeader;
import com.kawpool.stratum.shared.transactions.Transaction;
import com.kawpool.stratum.shared.transactions.TransactionUtils;
import org.apache.commons.codec.DecoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO: add docs
 */
public class GetBlockTemplateResultDto {
    private final static Logger LOGGER = LogManager.getLogger(GetBlockTemplateResultDto.class);

    private String[] capabilities;
    private int version;
    private String[] rules;
    private String vbAvailable;
    private int vbRequired;
    private String flags;
    private String target;
    private int mintime;
    private String[] mutable;
    private int sigoplimit;
    private String bits;
    private long height;

    @SerializedName("previousblockhash")
    private String previousBlockHash;

    @SerializedName("transactions")
    private TransactionDto[] transactionDtos;

    @SerializedName("coinbaseaux")
    private CoinbaseAuxDto coinbaseAuxDto;

    @SerializedName("coinbasevalue")
    private long coinbaseValue;

    @SerializedName("longpollid")
    private String longPollId;

    @SerializedName("noncerange")
    private String nonceRange;

    @SerializedName("sizelimit")
    private String sizeLimit;

    @SerializedName("weightlimit")
    private int weightLimit;

    @SerializedName("curtime")
    private long timestamp;

    @SerializedName("default_witness_commitment")
    private String defaultWitnessCommitment;

    public GetBlockTemplateResultDto() {

    }

    public Block toBlock() {
        BlockHeader blockHeader = new BlockHeader(
                this.version,
                this.previousBlockHash,
                this.timestamp,
                this.bits,
                this.height
        );

        List<Transaction> transactions = Arrays.stream(transactionDtos).map((TransactionDto transactionDto) -> {
            try {
                return TransactionUtils.getTransactionFromDto(transactionDto);
            } catch (DecoderException e) {
                LOGGER.error(e.getMessage());
            }
            return null;
        }).collect(Collectors.toList());

        Block block = new Block(blockHeader, transactions);
        block.setCoinbaseValue(this.coinbaseValue);
        return block;
    }

    public String[] getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String[] capabilities) {
        this.capabilities = capabilities;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String[] getRules() {
        return rules;
    }

    public void setRules(String[] rules) {
        this.rules = rules;
    }

    public String getVbAvailable() {
        return vbAvailable;
    }

    public void setVbAvailable(String vbAvailable) {
        this.vbAvailable = vbAvailable;
    }

    public int getVbRequired() {
        return vbRequired;
    }

    public void setVbRequired(int vbRequired) {
        this.vbRequired = vbRequired;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public void setPreviousBlockHash(String previousBlockHash) {
        this.previousBlockHash = previousBlockHash;
    }

    public TransactionDto[] getTransactions() {
        return transactionDtos;
    }

    public void setTransactions(TransactionDto[] transactionDtos) {
        this.transactionDtos = transactionDtos;
    }

    public CoinbaseAuxDto getCoinbaseAux() {
        return coinbaseAuxDto;
    }

    public void setCoinbaseAux(CoinbaseAuxDto coinbaseAuxDto) {
        this.coinbaseAuxDto = coinbaseAuxDto;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public long getCoinbaseValue() {
        return coinbaseValue;
    }

    public void setCoinbaseValue(long coinbaseValue) {
        this.coinbaseValue = coinbaseValue;
    }

    public String getLongPollId() {
        return longPollId;
    }

    public void setLongPollId(String longPollId) {
        this.longPollId = longPollId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getMintime() {
        return mintime;
    }

    public void setMintime(int mintime) {
        this.mintime = mintime;
    }

    public String[] getMutable() {
        return mutable;
    }

    public void setMutable(String[] mutable) {
        this.mutable = mutable;
    }

    public String getNonceRange() {
        return nonceRange;
    }

    public void setNonceRange(String nonceRange) {
        this.nonceRange = nonceRange;
    }

    public int getSigoplimit() {
        return sigoplimit;
    }

    public void setSigoplimit(int sigoplimit) {
        this.sigoplimit = sigoplimit;
    }

    public String getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(String sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public int getWeightLimit() {
        return weightLimit;
    }

    public void setWeightLimit(int weightLimit) {
        this.weightLimit = weightLimit;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getBits() {
        return bits;
    }

    public void setBits(String bits) {
        this.bits = bits;
    }

    public long getHeight() {
        return height;
    }

    public void setHeight(long height) {
        this.height = height;
    }

    public String getDefaultWitnessCommitment() {
        return defaultWitnessCommitment;
    }

    public void setDefaultWitnessCommitment(String defaultWitnessCommitment) {
        this.defaultWitnessCommitment = defaultWitnessCommitment;
    }

    @Override
    public String toString() {
        return "BlockTemplate{" +
                "capabilities=" + Arrays.toString(capabilities) +
                ", version=" + version +
                ", rules=" + Arrays.toString(rules) +
                ", vbAvailable='" + vbAvailable + '\'' +
                ", vbRequired=" + vbRequired +
                ", previousBlockHash='" + previousBlockHash + '\'' +
                ", transactionDtos=" + Arrays.toString(transactionDtos) +
                ", coinbaseAux=" + coinbaseAuxDto +
                ", flags='" + flags + '\'' +
                ", coinbaseValue=" + coinbaseValue +
                ", longPollId='" + longPollId + '\'' +
                ", target='" + target + '\'' +
                ", mintime=" + mintime +
                ", mutable=" + Arrays.toString(mutable) +
                ", nonceRange='" + nonceRange + '\'' +
                ", sigoplimit=" + sigoplimit +
                ", sizeLimit='" + sizeLimit + '\'' +
                ", weightLimit=" + weightLimit +
                ", timestamp=" + timestamp +
                ", bits='" + bits + '\'' +
                ", height=" + height +
                ", defaultWitnessCommitment='" + defaultWitnessCommitment + '\'' +
                '}';
    }
}