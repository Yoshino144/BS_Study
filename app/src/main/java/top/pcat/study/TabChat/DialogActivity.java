package top.pcat.study.TabChat;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;
import android.widget.EditText;

import com.apkfuns.logutils.LogUtils;

import top.pcat.study.R;
import top.pcat.study.TabChat.Fragment.ClassItemFragment;

public class DialogActivity extends Activity implements View.OnClickListener {
    private Button btnSubmit;
    private EditText etComment;
    private int type;

    private Handler mMsgHander ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        type = getIntent().getIntExtra("type", 0);
        //点击空白处让Activity消失，可在Style中设置
//        setFinishOnTouchOutside(true);

        setWindow();

        etComment = (EditText) findViewById(R.id.et_comment);
        new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
//                InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                InputMethodManager inputManager =
                        (InputMethodManager) etComment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(etComment, 0);
                return false;
            }
        }).sendEmptyMessageDelayed(0, 300);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        etComment.setHint(type==1?"输入班级名":"输入班级id");
    }

    private void setWindow() {
        //窗口对齐屏幕宽度
        Window win = this.getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;//设置对话框置顶显示
        win.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                Intent intent = new Intent();
                LogUtils.d("jiangyao"+type);
                intent.putExtra("type",type);
                intent.putExtra("value",etComment.getText().toString());
                setResult(Activity.RESULT_OK,intent);
                finish();
                break;
        }
    }

}