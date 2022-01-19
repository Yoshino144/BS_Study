package top.pcat.study.Utils;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.util.EncodingUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadFiles extends AppCompatActivity {
    public String read(String path) {
        String result = "";
        try {
            FileInputStream fin = openFileInput(path);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer,"UTF-8");
            fin.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "error_0x00NotFound";
        } catch (IOException e) {
            e.printStackTrace();
            return "error_0x00IOError";
        }
    }
}
