package top.pcat.study.Curriculum;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import top.pcat.study.R;

import static top.pcat.study.MainActivity.getStatusBarHeight;

public class Collection extends AppCompatActivity {
    private LinearLayout back_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection);

        int barsize=getStatusBarHeight(this);
        LinearLayout bar_wei = findViewById(R.id.bar_id);
        bar_wei.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, barsize));

        back_but = findViewById(R.id.back);
        back_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
