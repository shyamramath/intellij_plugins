package com.java.webservice;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.progress.ProgressIndicator;
import com.java.constants.AppConstants;
import com.java.models.OpenAICorporateRequestModel;
import com.java.utils.FileUtils;
import com.java.utils.JiraAPIUtilities;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public class OkHttpWebserviceCall {

    Logger logger =  Logger.getInstance(OkHttpWebserviceCall.class);

    /**
     *
     * @return
     */
    OkHttpClient getUnsafeOKhttpClient(){
        final TrustManager[] trustAllCert = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }
                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };
        final SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null,trustAllCert,new java.security.SecureRandom());
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            final SSLSocketFactory sslSocketFactory  =sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory,(X509TrustManager) trustAllCert[0]);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return false;
                }
            });
            OkHttpClient okHttpClient = builder.build();
            return  okHttpClient;
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

    okhttp3.RequestBody getRequestBody(OpenAICorporateRequestModel openAIRequestModel) {
        try {
            final MediaType JSON = MediaType.get("application/json");
            okhttp3.RequestBody body = okhttp3.RequestBody.create(openAIRequestModel.toString(), JSON);
            return body;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String makeOpenAIAPICall(String requestJson){
        String accesToken = AppConstants.OPEN_AI_KEY;
        final MediaType JSON = MediaType.get("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(requestJson, JSON);
        Request request= new Request.Builder().url(AppConstants.OPEN_AI_API_ENDPOINT).post(body).addHeader("Authorization", "Bearer "+accesToken).build();
        try(Response response =new OkHttpClient().newCall(request).execute()){
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return  e.getLocalizedMessage();
        }
    }


    /***
     * APICall for corporate GEN-AI Calls
     * @param openAIRequestModel
     * @return
     */
    public String makeCorporateOpenAICall(String openAIRequestModel, ProgressIndicator indicator){

        final MediaType JSON = MediaType.get("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(openAIRequestModel.toString(), JSON);
        Request request= new Request.Builder().url(AppConstants.CORP_OPEN_AI_API_ENDPOINT).post(body)
                .addHeader("Authorization", "Bearer "+AppConstants.CORP_OPEN_AI_API_TOKEN).build();

        indicator.setText("Calling the corp backend endpoint .... ");
        logger.info("Calling the corp backend endpoint,  "+AppConstants.CORP_OPEN_AI_API_ENDPOINT);

        try(Response response =getUnsafeOKhttpClient().newCall(request).execute()){
            return response.body().string();
        } catch (Exception e) {
            indicator.setText(" Error : while calling the open AI API .... (" +e.getMessage()  +") " );
            logger.info(" Error : while calling the open AI API, (" +e.getMessage()  +") ");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            e.printStackTrace();
            return  e.getLocalizedMessage();
        }

    }


//    static OkHttpClient client = new OkHttpClient();
//    static String runget(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
//    }



//    String post(String url, String json) throws IOException {
//        RequestBody body = RequestBody.create(json, JSON);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
//    }

//    private static final String IMGUR_CLIENT_ID = "...";
//    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

//    public void run() throws Exception {
//
//        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("title", "Square Logo")
//                .addFormDataPart("image", "logo-square.png",RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
//                .build();
//
//        Request request = new Request.Builder()
//                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
//                .url("https://api.imgur.com/3/image")
//                .post(requestBody)
//                .build();
//
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//            System.out.println(response.body().string());
//        }
//    }



}
