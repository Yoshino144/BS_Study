package top.pcat.study.Setting;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import top.pcat.study.R;
import top.pcat.study.Utils.StatusBarUtil;
import com.suke.widget.SwitchButton;

import static top.pcat.study.MainActivity.getStatusBarHeight;

public class Setting extends AppCompatActivity {
    private LinearLayout back_but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        StatusBarUtil.setStatusBarMode(this,true,R.color.touming);

        int barsize=getStatusBarHeight(this);
        LinearLayout bar_wei = findViewById(R.id.bar_id);
        bar_wei.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, barsize));

        SwitchButton switchButton = (SwitchButton) findViewById(R.id.switch_button);

        switchButton.setChecked(true);
        switchButton.isChecked();
        //switchButton.toggle();
        //switchButton.toggle(false);
        //switchButton.setShadowEffect(true);
        //switchButton.setEnabled(false);
        //switchButton.setEnableEffect(false);
        switchButton.setOnCheckedChangeListener((view, isChecked) -> {
            //TODO do your job
        });

        back_but = findViewById(R.id.back);
        back_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
