package top.pcat.study.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.knowledge.mnlin.RollTextView;

import in.arjsna.swipecardlib.SwipeCardView;
import top.pcat.study.FastBlur.FastBlurActivity;
import top.pcat.study.Setting.SettingsActivity;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.R;
import top.pcat.study.View.CardsAdapter;
import top.pcat.study.View.LogUtils;
import top.pcat.study.WrongQuestion.WrongQuestion;
import top.pcat.study.user.SignActivity;
import top.pcat.study.user.User;

import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class BlankFragment4 extends Fragment  {
    private static final String ARG_SHOW_TEXT = "text";
    private static final String LV_SHOW_TEXT = "lvtext";

    private SharedPreferences mSpf;
    private String mContentText;
    private String mLvText;
    private LinearLayout user;
    private FileTool ft;
    private String name;
    private String lv;
    boolean loginflag;
    private LinearLayout upData_but;
    private LinearLayout set;
    private LinearLayout wrongQuestion;
    private LinearLayout yejian;

    public BlankFragment4() {}

    public static BlankFragment4 newInstance(String param1,String lv) {
        BlankFragment4 fragment = new BlankFragment4();
        Bundle args = new Bundle();
        args.putString(ARG_SHOW_TEXT, param1);
        args.putString(LV_SHOW_TEXT,lv);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContentText = getArguments().getString(ARG_SHOW_TEXT);
            mLvText = getArguments().getString(LV_SHOW_TEXT);
        }
    }
    private ArrayList<CardsAdapter.Card> al = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_blank_fragment4, container, false);
        getDummyData(al);
        CardsAdapter arrayAdapter = new CardsAdapter(getContext(), al );
        SwipeCardView swipeCardView = (SwipeCardView) rootView.findViewById(R.id.swipe_card_view);
        swipeCardView.setAdapter(arrayAdapter);
        swipeCardView.setFlingListener(new SwipeCardView.OnCardFlingListener() {
            @Override
            public void onCardExitLeft(Object dataObject) {
                LogUtils.i( "Left Exit");
            }

            @Override
            public void onCardExitRight(Object dataObject) {
                LogUtils.i( "Right Exit");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                LogUtils.i( "Adater to be empty");
                //add more items to adapter and call notifydatasetchanged
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                LogUtils.i("Scroll");
            }

            @Override
            public void onCardExitTop(Object dataObject) {
                LogUtils.i( "Top Exit");
            }

            @Override
            public void onCardExitBottom(Object dataObject) {
                LogUtils.i("Bottom Exit");
            }
        });

        TextView contentTv = rootView.findViewById(R.id.usernameshow);
        contentTv.setText(mContentText);
        yejian = rootView.findViewById(R.id.yejian);
        //TextView banben = rootView.findViewById(R.id.banben);
        mSpf = super.getActivity().getSharedPreferences("yejian",MODE_PRIVATE);
        PackageInfo pi = null;
        try {
            pi=getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("版本号：",pi.versionName);
        //banben.setText("当前版本号："+pi.versionName);
        LinearLayout lvView = rootView.findViewById(R.id.lvView);

        File path = new File(getActivity().getFilesDir().getAbsolutePath()+"/Login.txt");
        if(ft.isFileExists(path.toString())){
            lv = readInfo(readlv());
            //name = read();
            JSONObject jsonObject = null;
//            try {
//                jsonObject = new JSONObject(lv);
//                name = jsonObject.getString("name");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
            loginflag = true;
            File path2 = new File(getActivity().getFilesDir().getAbsolutePath()+"/UserImg.png");
            if(ft.isFileExists(path2.toString())){
                //ImageView headImage = getActivity().findViewById(R.id.hearImg);

                Bitmap bitmap = BitmapFactory.decodeFile(getActivity().getFilesDir().getAbsolutePath()+"/UserImg.png");
//            headImage.setImageBitmap(bitmap);

                CircleImageView img = rootView.findViewById(R.id.hearImg);
                img.setImageBitmap(bitmap);
            }
            else{
            }
        }
        else{
            lvView.setVisibility(View.GONE);
            name = "未登录 | 点击登录";
            lv = "0";
            loginflag = false;
        }

        TextView nametext = rootView.findViewById(R.id.usernameshow);
        nametext.setText(name);

        TextView lvtext = rootView.findViewById(R.id.lvtext);
        lvtext.setText("Lv."+lv);
        return rootView;
    }

    private void getDummyData(ArrayList<CardsAdapter.Card> al) {
        CardsAdapter.Card card = new CardsAdapter.Card();
        card.name = "Card1";
        card.imageId = R.drawable.image1;
        al.add(card);

        CardsAdapter.Card card2 = new CardsAdapter.Card();
        card2.name = "Card2";
        card2.imageId = R.drawable.image1;
        al.add(card2);
        CardsAdapter.Card card3 = new CardsAdapter.Card();
        card3.name = "Card3";
        card3.imageId = R.drawable.image1;
        al.add(card3);
        CardsAdapter.Card card4 = new CardsAdapter.Card();
        card4.name = "Card4";
        card4.imageId = R.drawable.image1;
        al.add(card4);
    }

    public String readInfo(String tempInfo){
        try {

            JSONObject jsonObject = new JSONObject(tempInfo);
                //String lv = jsonObject.getString("lv");
            name = jsonObject.getString("name");
            String lv ="10";
                return lv;
        } catch (JSONException e) {
            Toast.makeText(getActivity(),"信息读取错误",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return "error";
    }

    public String read() {
        String result = "";
        try {
            FileInputStream fin = getActivity().openFileInput("Login.txt");
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer,"UTF-8");
            fin.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "未登录 | 点击登录";
        } catch (IOException e) {
            e.printStackTrace();
            return "未登录 | 点击登录";
        }
    }

    public String readlv() {
        String result = "";
        try {
            FileInputStream fin = getActivity().openFileInput("UserInfo");
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer,"UTF-8");
            fin.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "未登录 | 点击登录";
        } catch (IOException e) {
            e.printStackTrace();
            return "未登录 | 点击登录";
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

        RollTextView rollTextView = getActivity().findViewById(R.id.rtv_temp);
        rollTextView.setAppearCount(1)
                .setInterval(1500)
                //.setOrderVisible(true)
                //.setEndText("查看", true)
                .setLayoutResource(R.layout.item_roll_text_view)
                .setRollDirection(0)

                .setOnItemClickListener((parent, view, position, id) -> Log.d("点击位置：=========", String.valueOf(position)))
        .refreshData(Arrays.asList("服务器测试中"
                , "可能遇到不稳定状况"
                , "你正在使用测试版"));

        String a="123";
        if(loginflag == true) a="true";
        else a="false";

//        LinearLayout youhui = getActivity().findViewById(R.id.youhui);
//        youhui.setOnClickListener(v->{
//            Intent intent01=new Intent();
//            intent01.setClass(getActivity(), NewExercisesActitity.class);
//            startActivity(intent01);
//        });

//        upData_but = getActivity().findViewById(R.id.upData_but);
//        upData_but.setOnClickListener(v -> {
////            Intent intent01=new Intent();
////            intent01.setClass(getActivity(), UpData.class);
////            startActivity(intent01);
//        });

        user = getActivity().findViewById(R.id.user);
        user.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            if(!loginflag){
                Intent intent01=new Intent();
                intent01.setClass(getActivity(), SignActivity.class);
                intent01.putExtra("page",3);
                getActivity().finish();
                startActivity(intent01);
            }
            else{
                Intent intent01=new Intent();
                intent01.setClass(getActivity(), User.class);
                getActivity().finish();
                startActivity(intent01);
            }
        });

        LinearLayout shape = getActivity().findViewById(R.id.shape);
        shape.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getActivity(), FastBlurActivity.class);
            View view = getActivity().getWindow().getDecorView();

            Bitmap bmp1;
            view.destroyDrawingCache();
            view.setDrawingCacheEnabled(true);
            bmp1 = view.getDrawingCache();
            save(bmp1);
            startActivity(intent);
        });

        set = getActivity().findViewById(R.id.set);
        set.setOnClickListener(v -> {
            Intent intent01=new Intent();
            intent01.setClass(getActivity(), SettingsActivity.class);
            startActivity(intent01);
        });

        wrongQuestion = getActivity().findViewById(R.id.wrongQuestion);
//        ShadowDrawable.setShadowDrawable(wrongQuestion,Color.parseColor("#ffffff"), dpToPx(7),
//                Color.parseColor("#f5f5f7"), 0, 0, dpToPx(-10));
        wrongQuestion.setOnClickListener(v -> {
            Intent intent01=new Intent();
            intent01.setClass(getActivity(), WrongQuestion.class);
            startActivity(intent01);
        });
        TextView ye_text = getActivity().findViewById(R.id.ye_text);
        ImageView ye_img = getActivity().findViewById(R.id.ye_img);
        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(mode == Configuration.UI_MODE_NIGHT_YES){
            yejian.setBackgroundResource(R.drawable.shape_yejian);
            ye_text.setText("日间模式");
            ye_img.setImageResource(R.drawable.ri);
        }else if(mode == Configuration.UI_MODE_NIGHT_NO) {
            yejian.setBackgroundResource(R.drawable.shape_test);
            ye_text.setText("夜间模式");
            ye_img.setImageResource(R.drawable.four_night);
        }
        yejian.setOnClickListener(v->{
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

            if(mode == Configuration.UI_MODE_NIGHT_YES) {
                writeInfo(getActivity(),"no");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
                writeInfo(getActivity(),"yes");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                // blah blah
            }
        });

    }

    private void writeInfo(FragmentActivity view, String val) {
        SharedPreferences.Editor editor = mSpf.edit();
        editor.putString("name",val);
        editor.apply();
    }
    public String readInfo(View view) {
        String info = mSpf.getString("name","");
        return info;
    }

    private int dpToPx(int dp) {
        return (int) (Resources.getSystem().getDisplayMetrics().density * dp + 0.5f);
    }

    public void save(Bitmap bmp1) {
        String FILENAME = "Bitm.png";
        FileOutputStream fos = null;
        try {
            fos = getActivity().openFileOutput(FILENAME, MODE_PRIVATE);
            bmp1.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}