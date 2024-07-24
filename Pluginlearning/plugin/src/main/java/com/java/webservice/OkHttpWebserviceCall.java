package com.java.webservice;

import com.intellij.execution.rmi.ssl.SslSocketFactory;
import okhttp3.*;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public class OkHttpWebserviceCall {

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

    okhttp3.RequestBody getRequestBody(){
        okhttp3.RequestBody body = okhttp3.RequestBody.create("requ", JSON);
        return body;
    }

    Request  requestBuilder(){
        String accesToken = "";
        return  new Request.Builder().url("").post(getRequestBody()).addHeader("Authorization", "Bearer "+accesToken).build();
    }

    void makeCall(){
        try(Response response =getUnsafeOKhttpClient().newCall(requestBuilder()).execute()){
            String response1 = response.body().toString();
            System.out.println(" Log Response ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static OkHttpClient client = new OkHttpClient();
//    static String runget(String url) throws IOException {
//        Request request = new Request.Builder()
//                .url(url)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
//    }


    public static final MediaType JSON = MediaType.get("application/json");
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

    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

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

//    /**
//     *
//     * @param filePath
//     * @return
//     * @throws IOException
//     */
//    public static String createStories(String filePath) throws IOException {
//        Map<Integer, List<String>> map = new FastexcelHelper().readExcel(filePath);
//
//        StringBuilder responseMsg = new StringBuilder();
//        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//            List<String> value = entry.getValue();
//            String entry1= value.get(0);
//            String entry2= value.get(1);
//            String entry3= value.get(2);
//            String entry4= value.get(3);
//            String entry5= value.get(4);
//
//            JIRARequestModel model = new JIRARequestModel();
//            model.setTitleSummary(value.get(1));
//            model.setDescription(value.get(2));
//            model.setJiraIssueType(value.get(3));
//            model.setKey(value.get(4));
//            System.out.println(" Model Print : "+model.toString());
////          JIRAStoryHelper.jiraCreateIssue(entry2,entry3,"BUG");
//            responseMsg.append(JIRAStoryHelper.createJIRAIssue(model));
//        }
//        responseMsg.append("Yaaay !!! "+ map.size() +" Stories Created Successfully");
//        System.out.println(map);
//        return responseMsg.toString();
//    }


}
