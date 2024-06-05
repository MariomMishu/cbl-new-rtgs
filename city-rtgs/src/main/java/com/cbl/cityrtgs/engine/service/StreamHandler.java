package com.cbl.cityrtgs.engine.service;

import java.io.IOException;
import java.net.*;

public class StreamHandler extends URLStreamHandler {
    @Override
    protected URLConnection openConnection(URL u) {

        try {
            URL url = new URL(u.toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setRequestMethod("POST");

            return httpURLConnection;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
