package top.pcat.study.Size;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.R;
import top.pcat.study.Utils.StatusBarUtil;
import top.pcat.study.View.CircleRippleView;
import com.apkfuns.logutils.LogUtils;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OHHHHHH  extends AppCompatActivity {

    private ImageView mHeadImage;
    private ImageView mRadarImage;
    private FileTool ft;
    private CircleRippleView mCircleRippleView;
    private String[] flag =  new String[5000];
    private int Size;
    private TextView size;
    private TextView wrongsize;
    private TextView truesize;
    int wei_size=0;
    int true_size=0;
    int wrong_size=0;
    private LinearLayout two_page;
    private ArrayList<ContentItem> objects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ohhhhhhhh);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        StatusBarUtil.setStatusBarMode(this,true,R.color.touming);
        two_page = findViewById(R.id.two_page);
        Intent intent2 = getIntent();
        Size = Integer.parseInt(intent2.getStringExtra("size"));
        initUI();
        String json = read("randomFlag.json");
        readSj(json);

        size=findViewById(R.id.size);
        wrongsize=findViewById(R.id.wrongsize);
        truesize=findViewById(R.id.truesize);
        Re_Size();
        size.setText("题数"+String.valueOf(Size));
        wrongsize.setText("错误题数"+String.valueOf(wrong_size));
        truesize.setText("正确题数"+String.valueOf(true_size)+"未作"+wei_size);
        two_page.setVisibility(View.VISIBLE);

        PieChart pieChart = (PieChart) findViewById(R.id.chart);
//        //设置手势滑动事件
//        pieChart.setOnChartGestureListener((OnChartGestureListener) this);
//        //设置数值选择监听
//        pieChart.setOnChartValueSelectedListener((OnChartValueSelectedListener) this);
//        //设置描述文本
//        pieChart.getDescription().setEnabled(false);
//        //设置支持触控手势
//        pieChart.setTouchEnabled(true);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(true_size, "正确"));
        entries.add(new PieEntry(wrong_size, "错误"));
        entries.add(new PieEntry(wei_size, "未作"));
        PieDataSet set = new PieDataSet(entries, " ");
        //set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setColors(Color.parseColor("#b9ef83"), Color.parseColor("#ffd28c"), Color.parseColor("#80d9ea"));
        PieData data = new PieData(set);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.setBackgroundColor(Color.parseColor("#f7f7f7"));
        pieChart.setMaxAngle(180f); // HALF CHART
        pieChart.setRotationAngle(180f);
        pieChart.setCenterTextOffset(0, -20);
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(false);
        //pieChart.setCenterTextTypeface(tfLight);
        pieChart.setCenterText(generateCenterSpannableText());
        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.invalidate();

        //objects.add(34, new ContentItem("Even More Line Charts","dfj,"));
        MyAdapter adapter = new MyAdapter(this, objects);
        ListView lv = findViewById(R.id.listView1);
        lv.setAdapter(adapter);
//
//        int listViewHeight = 0;
//        int adaptCount = adapter.getCount();
//        for(int i=0;i<adaptCount;i++){
//            View temp = adapter.getView(i,null,lv);
//            temp.measure(0,0);
//            listViewHeight += temp.getMeasuredHeight();
//        }
//        ViewGroup.LayoutParams layoutParams = lv.getLayoutParams();
//        layoutParams.width = ViewGroup.LayoutParams.FILL_PARENT;
//        layoutParams.height = listViewHeight + (lv.getDividerHeight() * (adaptCount +1));
//        lv.setLayoutParams(layoutParams);
    }


    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("结果\n总题数："+String.valueOf(Size));
        s.setSpan(new RelativeSizeSpan(1f), 0, 2, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), 2, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()),  2, s.length(), 0);
        return s;
    }

    private void Re_Size(){
        for(int i = 1; i <= Size;i++){
            if(flag[i] == null){
                wei_size+=1;
            }else{
                if(flag[i].contains("wrong")){
                    wrong_size+=1;
                }else if(flag[i].contains("true")){
                    true_size+=1;
                }else{
                    wei_size+=1;
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            File path = new File(getFilesDir().getAbsolutePath()+"/randomFlag.json");

            FileTool.deleteFile(path);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initUI() {
        mRadarImage = (ImageView) findViewById(R.id.radar_image);
        Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.radar_search_animation);
        mRadarImage.startAnimation(mAnimation);
        mCircleRippleView = (CircleRippleView) findViewById(R.id.radar_bg_image);
        mHeadImage = (ImageView)findViewById(R.id.radar_head_image);
        File path2 = new File(getFilesDir().getAbsolutePath()+"/UserImg.png");
        if(ft.isFileExists(path2.toString())){
            Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath()+"/UserImg.png");
            mHeadImage.setImageBitmap(bitmap);
        }
    }

    public String read(String file) {
        String result = "";
        try {
            FileInputStream fin = openFileInput(file);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer,"UTF-8");
            fin.close();
            LogUtils.d("读取到的json"+result);
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public void readSj(String tempInfo) {
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;
        try {
            jsonArray = new JSONArray(tempInfo);
            jsonObject = jsonArray.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        objects.add(0, new ContentItem("错题"));
        int j=1;
        for (int i = 1; i <= Size;i++){
            try {
                assert jsonObject != null;
                flag[i] = jsonObject.getString(String.valueOf(i));
                if(flag[i].contains("wrong")){
                    String answer = flag[i].substring(6, 7);
                    String cha_id = flag[i].substring(7, 8);
                    String title = flag[i].substring(8);
                    LogUtils.d("第"+cha_id+"章 "+title+"   答案："+answer);
                    objects.add(j, new ContentItem("第"+cha_id+"章 "+title,"答案："+answer));
                    j+=1;
                }
            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

}
