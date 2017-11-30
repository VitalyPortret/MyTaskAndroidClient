package ru.portretov.mytaskandroidclient.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
            url = new URL("http://10.0.2.2:8080/api/tasks/message");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("content-type", "application/json");
            connection.setRequestProperty("accept-encoding", "gzip, deflate");
            connection.setRequestProperty("accept-language", "en-US,en;q=0.8");
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            JSONObject ap = new JSONObject();
            ap.put("text", "dfsfdsfdsfds");
            ap.put("id", "sddkshfuidsijfkdsokf");
            ap.put("delivered", true);

//            Заработало
//            OutputStream os = connection.getOutputStream();
//            byte[] data = ap.toString().getBytes("UTF-8");
//            os.write(data);
//            connection.connect();

//          Заработало
            BufferedOutputStream os = new BufferedOutputStream(connection.getOutputStream());
            byte[] data = ap.toString().getBytes("UTF-8");
            os.write(data);
            connection.connect();
            os.flush();
            os.close();

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
            }

//            return (Task) JSON.parse(stringBuilder.toString());
            return tasks[0];
        } catch (JSONException e) {
            e.printStackTrace();
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
        return null;
    }
}
