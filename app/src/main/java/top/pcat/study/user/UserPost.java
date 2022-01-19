package top.pcat.study.user;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static android.media.AudioRecord.SUCCESS;
import static android.media.audiofx.Visualizer.ERROR;

public class UserPost {
    private Handler handler;

    public String md5Decode32(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException",e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10){
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public void Login(String username,String password) throws IOException {

        password = md5Decode32(password);
        String data = "username="+username+"&password="+password;
        String path = "http://http://192.168.31.238/web/user.php";
        URL url = new  URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0(compatible;MSIE 9.0;Windows NT 6.1;Trident/5.0)");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", data.length()+"");
        conn.setDoOutput(true);

        byte[] bytes = data.getBytes();
        conn.getOutputStream().write(bytes);
        int code = conn.getResponseCode();
        System.out.println("code=" + code);

        if(code == 200){
            InputStream is = conn.getInputStream();
            String  result = readStream(is);
            Message mas= Message.obtain();
            mas.what = SUCCESS;
            mas.obj = result;
            handler.sendMessage(mas);
            Log.d("123","123");

        }else{
            Message mas = Message.obtain();
            mas.what = ERROR;
            handler.sendMessage(mas);
        }
    }

    public static String readStream(InputStream is){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = is.read(buffer))!=-1) {
                baos.write(buffer,0,len);
            }
            baos.close();
            return new String(baos.toByteArray());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }
}
