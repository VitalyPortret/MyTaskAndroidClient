package ru.portretov.mytaskandroidclient.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.portretov.mytaskandroidclient.entity.Task;

/**
 * Created by adminvp on 11/29/17.
 */

public class DataUtil {

    public static Task postTask(Task... tasks) throws IOException {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedReader reader = null;
        BufferedOutputStream bos = null;
        URL url;

        try {
            url = new URL(ServerURL.URL_POST_TASK);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("content-type", "application/json");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            String taskJsonString = JSON.toJSONString(tasks[0]);
            outputStream = connection.getOutputStream();

            bos = new BufferedOutputStream(outputStream);
            byte[] data = taskJsonString.getBytes("UTF-8");
            bos.write(data);
            connection.connect();
            bos.flush();

            int HttpResult = connection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                inputStream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                Log.e("str",stringBuilder.toString());
                return (Task) JSON.parse(stringBuilder.toString());
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
