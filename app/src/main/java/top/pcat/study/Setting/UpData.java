package top.pcat.study.Setting;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import top.pcat.study.R;

import static top.pcat.study.MainActivity.getStatusBarHeight;

public class UpData extends AppCompatActivity {
    private LinearLayout back_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updata);

        int barsize=getStatusBarHeight(this);
        LinearLayout bar_wei = findViewById(R.id.bar_id);
        LinearLayout updata = findViewById(R.id.updata);
        bar_wei.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, barsize));

        back_but = findViewById(R.id.back);
        back_but.setOnClickListener(v -> finish());

        updata = findViewById(R.id.updata);
        updata.setOnClickListener(v -> {
            Toast.makeText(this,"已是最新版",Toast.LENGTH_SHORT).show();
        });

    }
}
