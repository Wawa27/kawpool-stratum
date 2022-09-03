package com.kawpool.stratum.ravend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kawpool.stratum.core.configuration.Configuration;
import com.kawpool.stratum.ravend.dtos.server.GetBlockTemplateResultDto;
import com.kawpool.stratum.ravend.dtos.server.ShareValidationResultDto;
import com.kawpool.stratum.ravend.dtos.server.SubmitShareResultDto;
import com.kawpool.stratum.ravend.models.ShareSubmission;
import com.kawpool.stratum.ravend.dtos.client.RpcGetBlockTemplateDto;
import com.kawpool.stratum.ravend.dtos.client.RpcShareValidationDto;
import com.kawpool.stratum.ravend.dtos.client.RpcSubmitShareDto;
import com.kawpool.stratum.shared.rpc.RpcRequestDto;
import com.kawpool.stratum.shared.rpc.RpcResponseDto;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * TODO: add docs
 */
public class RavendHttpClient {
    private static final Logger LOGGER = LogManager.getLogger(RavendHttpClient.class);

    private final static Gson gson;
    private final static OkHttpClient httpClient;
    private final static String credentials;

    static {
        gson = new Gson();
        httpClient = new OkHttpClient();
        credentials = Credentials.basic(
                Configuration.getInstance().getDaemons()[0].getUsername(),
                Configuration.getInstance().getDaemons()[0].getPassword()
        );
    }

    public static RpcResponseDto<GetBlockTemplateResultDto, String> getBlockTemplate() throws IOException {
        Request request = new Request.Builder()
                .url(Configuration.getInstance().getDaemons()[0].getHost())
                .header("Authorization", credentials)
                .post(RequestBody.create(gson.toJson(new RpcGetBlockTemplateDto()), MediaType.parse("application/json")))
                .build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        ResponseBody body = response.body();
        if (body == null) {
            // TODO: add rpc fallbacks
            throw new RuntimeException("Could not get validation from RPC server");
        }
        Type blockTemplateType = new TypeToken<RpcResponseDto<GetBlockTemplateResultDto, Object>>(){}.getType();
        return new Gson().fromJson(body.string(), blockTemplateType);
    }

    public static RpcResponseDto<ShareValidationResultDto, Object> validateShare(RpcShareValidationDto rpcShareValidationDto) throws IOException {
        LOGGER.info("Validating share : " + rpcShareValidationDto);
        String $this$toRequestBody = gson.toJson(rpcShareValidationDto);
        Request request = new Request.Builder()
                .url(Configuration.getInstance().getDaemons()[0].getHost())
                .header("Authorization", credentials)
                .post(RequestBody.create($this$toRequestBody, MediaType.parse("application/json")))
                .build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        ResponseBody body = response.body();
        if (body == null) {
            // TODO: add rpc fallbacks
            throw new RuntimeException("Could not get validation from RPC server");
        }
        Type shareValidationResultType = new TypeToken<RpcResponseDto<ShareValidationResultDto, String>>(){}.getType();
        String string = body.string();
        return new Gson().fromJson(string, shareValidationResultType);
    }

    public static RpcResponseDto<String, String> submitShare(RpcSubmitShareDto submitShareDto) throws IOException {
        Request request = new Request.Builder()
                .url(Configuration.getInstance().getDaemons()[0].getHost())
                .header("Authorization", credentials)
                .post(RequestBody.create(gson.toJson(submitShareDto), MediaType.parse("application/json")))
                .build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        ResponseBody body = response.body();
        if (body == null) {
            // TODO: add rpc fallbacks
            throw new RuntimeException("Could not get validation from RPC server");
        }
        Type submitShareType = new TypeToken<RpcResponseDto<String, String>>(){}.getType();
        return new Gson().fromJson(body.string(), submitShareType);
    }
}
