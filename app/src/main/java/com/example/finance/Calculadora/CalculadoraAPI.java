package com.example.finance.Calculadora;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CalculadoraAPI {

    public void verificarPalabra(ApiResponse response, String operacion) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    String urlFormat = "https://api.mathjs.org/v4/" + "?expr=" + URLEncoder.encode(operacion, StandardCharsets.UTF_8.name());

                    URL url = new URL(urlFormat);
                    HttpURLConnection http = (HttpURLConnection)url.openConnection();
                    http.setRequestMethod("GET");
                    http.setDoOutput(false);
                    http.setRequestProperty("Content-Type", "application/json");

                    int responseCode = http.getResponseCode();
                    BufferedReader responseReader;
                    StringBuilder apiResponse = new StringBuilder();
                    boolean hasError = false;
                    if (responseCode >= 200 && responseCode <= 299) {
                        responseReader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                    } else {
                        hasError = true;
                        responseReader = new BufferedReader(new InputStreamReader(http.getErrorStream()));
                    }

                    String strCurrentLine;
                    while ((strCurrentLine = responseReader.readLine()) != null) {
                        apiResponse.append(strCurrentLine);
                    }

                    String responseString = apiResponse.toString();

                    //Agregar aqui para solo 2 decimales
                    responseString = String.format("%.2f", Double.valueOf(responseString));


                    if (!hasError) {
                        response.onSuccess(responseString);
                    } else {
                        response.onError(responseString);
                    }

                    http.disconnect();

                    executorService.shutdownNow();

                } catch (Exception e) {
                    response.onError(e.getMessage());
                }

            }
        });

    }

    public interface ApiResponse {
        void onSuccess(String response);
        void onError(String error);
    }

}
