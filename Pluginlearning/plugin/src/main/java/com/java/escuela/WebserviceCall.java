package com.java.escuela;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WebserviceCall {

    static OkHttpClient client = new OkHttpClient();
    static String runget(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }


    public static final MediaType JSON = MediaType.get("application/json");
    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    public void run() throws Exception {

        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "Square Logo")
                .addFormDataPart("image", "logo-square.png",RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
                .build();

        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            System.out.println(response.body().string());
        }

    }

    /**
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String createStories(String filePath) throws IOException {
        Map<Integer, List<String>> map = new FastexcelHelper().readExcel(filePath);
        for (Map.Entry<Integer, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            List<String> value = entry.getValue();
            String entry1= value.get(0);
            String entry2= value.get(1);
            String entry3= value.get(2);
            JIRAStoryHelper.jiraCreateIssue(entry2,entry3,"BUG");
        }
        System.out.println(map);
        return "Yaaay !!! "+ map.size() +" Stories Created Successfully";
    }


}
