package ru.portretov.mytaskandroidclient.util;

import com.alibaba.fastjson.JSON;

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
        BufferedReader reader = null;
        URL url;

        try {
            url = new URL(ServerURL.URL_POST_TASK);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            String taskString = JSON.toJSONString(tasks[0]);
//            connection.setRequestProperty("Content-Length", Integer.toString(taskString.getBytes("UTF-8").length));
            byte[] byteArray = taskString.getBytes("UTF-8");
            os.write(byteArray);
            connection.connect();

            inputStream  = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();

            String line = "";
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return (Task) JSON.parse(stringBuilder.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
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
    }
}
