package top.pcat.study.Examples;

import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Examples {


    public void GetYixuan(String key, String val, String url) throws IOException {
        new Thread(() -> {

            try {
                URL uu = new URL(url);
                LogUtils.d("Internet类","url============="+uu);
                HttpURLConnection connection = (HttpURLConnection) uu.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setUseCaches(false);
                connection.connect();

                String body = key + "="+val;
                LogUtils.d("key=========val====",body);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                writer.write(body);
                writer.close();

                int responseCode = connection.getResponseCode();
                if(responseCode == HttpURLConnection.HTTP_OK){
                    InputStream inputStream = connection.getInputStream();
                    String result = String.valueOf(inputStream);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine())!= null){
                        response.append(line);
                    }

//                    tempTest  = response.toString();
//                    cwjHandler.post(mUpdateResults);
//
//                    LogUtils.d("用户已选======",tempTest);


//                    progressDiaLogUtils.dismiss();
                }
                else{
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}
