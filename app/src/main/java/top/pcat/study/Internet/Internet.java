package top.pcat.study.Internet;

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

public class Internet {
    private String key;
    private String val;
    private String url;
    private String res = "null";

    public Internet(String keyy,String vall,String urll) throws IOException {
        key = keyy;
        val = vall;
        url = urll;
        GetData();
    }

    public String GetData() throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
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
                    //LogUtils.d(username,password);
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

                        res = response.toString();
                        LogUtils.d("Internet类","res============="+res);
                    }
                    else{
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return res;
    }

}
