package com.kawpool.stratum.core.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kawpool.stratum.shared.jobs.Job;
import org.apache.commons.codec.DecoderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Custom adapter to job in json as an array of strings
 */
public class JobAdapter extends TypeAdapter<Job> {
    private static final Logger LOGGER = LogManager.getLogger(JobAdapter.class);

    @Override
    public Job read(JsonReader reader) {
        return null;
    }

    @Override
    public void write(JsonWriter writer, Job job) throws IOException {
        try {
            writer.beginArray();
            writer.value(job.getJobId());
            writer.value(job.getBlock().getHeaderHashHexadecimal());
            writer.value(job.getBlock().getSeedHash());
            writer.value(job.getTarget());
            writer.value(job.isForceUpdate());
            writer.value(job.getBlock().getHeader().getHeight());
            writer.endArray();
        } catch (DecoderException e) {
            LOGGER.error("Error during job encoding", e);
        }
    }
}