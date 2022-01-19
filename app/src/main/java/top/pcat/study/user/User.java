package top.pcat.study.user;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.apkfuns.logutils.LogUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.google.gson.Gson;
import top.pcat.study.BuildConfig;
import top.pcat.study.FastBlur.FastBlur;
import top.pcat.study.Utils.FileTool;
import top.pcat.study.Utils.FileUtil;
import top.pcat.study.MainActivity;
import top.pcat.study.R;
import top.pcat.study.Utils.StatusBarUtil;
import top.pcat.study.View.ClipImageActivity;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static top.pcat.study.Utils.FileUtil.getRealFilePathFromUri;

public class User extends AppCompatActivity implements View.OnClickListener {
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //头像1
    private CircleImageView headImage1;
    //头像2
    private ImageView headImage2;
    //调用照相机返回图片文件
    private File tempFile;
    private FileTool ft;
    // 1: qq, 2: weixin
    private int type;
    private List<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;
    private static boolean userflag = false;
    private MainActivity ma;
    private LinearLayout linear;
    private LinearLayout outuser;
    private LinearLayout birSet;
    private LinearLayout setSex;
    private LinearLayout setName;
    private LinearLayout setCity;
    private LinearLayout changeImg;
    private LinearLayout setSchool;
    private LinearLayout setCollege;
    private LinearLayout setMajor;
    private LinearLayout setGrade;
    private LinearLayout setOccupation;
    private String id = null;
    private String name = null;
    private String sex =null;
    private String birthday =null;
    private String city = null;
    private String school = null;
    private String college = null;
    private String major = null;
    private String grade = null;
    private String ocuu = null;
    private int mYear;
    private int mMonth;
    private int mDay;
    private OptionsPickerView pvOptions;
    boolean changeFlag = false;
    private String ttInfo;
    private int occuFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userdadta);
        DialogUIUtils.init(this);
//        Objects.requireNonNull(getSupportActionBar()).hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        StatusBarUtil.setStatusBarMode(this,true,R.color.touming);
        StatusBarUtil.setTranslucentStatus(this);


        outuser = findViewById(R.id.outuser);
        outuser.setOnClickListener(this);
        birSet = findViewById(R.id.birSet);
        birSet.setOnClickListener(this);
        setSex = findViewById(R.id.setSex);
        setSex.setOnClickListener(this);
        setName = findViewById(R.id.setName);
        setName.setOnClickListener(this);
        setCity = findViewById(R.id.setCity);
        setCity.setOnClickListener(this);
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        changeImg = findViewById(R.id.changeImg);
        changeImg.setOnClickListener(this);
        setSchool = findViewById(R.id.setSchool);
        setSchool.setOnClickListener(this);
        setCollege = findViewById(R.id.setCollege);
        setCollege.setOnClickListener(this);
        setMajor = findViewById(R.id.setMajor);
        setMajor.setOnClickListener(this);
        setGrade = findViewById(R.id.setGrade);
        setGrade.setOnClickListener(this);
        setOccupation = findViewById(R.id.setoccu);
        setOccupation.setOnClickListener(this);

        //读取用户信息
        String tempInfo = read("UserInfo");
        ttInfo = tempInfo;
        readInfo(tempInfo);

//        //设置不同身份显示不同数据
//        if(ocuu.equals("0")){
//            setSchool.setVisibility(View.GONE);
//            setCollege.setVisibility(View.GONE);
//            setMajor.setVisibility(View.GONE);
//            setGrade.setVisibility(View.GONE);
//            occuFlag =1;
//        }else {
//            setGrade.setVisibility(View.GONE);
//        }

        File path = new File(getFilesDir().getAbsolutePath()+"/UserImg.png");
        if(FileTool.isFileExists(path.toString())){
            LogUtils.d("头像存在");
            Bitmap bitmap = readlv();
            findViewById(R.id.userbg).getViewTreeObserver().addOnPreDrawListener(
                    () -> {
                        applyBlur(bitmap);
                        return true;
                    });

            ImageView headImage = findViewById(R.id.headImage);

            Bitmap bitmap2 = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath()+"/UserImg.png");
            headImage.setImageBitmap(bitmap2);
        }
        else{
            LogUtils.d("头像不存在");

        }
    }

    private void applyBlur(Bitmap bmp1) {
//        View view = getWindow().getDecorView();
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache(true);
//        /**
//         * 获取当前窗口快照，相当于截屏
//         */
//        //Bitmap bmp1 = view.getDrawingCache();
////        final Bitmap bmp1 = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
//        int height = getOtherHeight();
        /**
         * 除去状态栏和标题栏
         */
        View aa = findViewById(R.id.userbg);
        aa.getHeight();
        aa.getWidth();
        Log.d(""+bmp1.getWidth(),""+aa.getWidth());
        Log.d(""+bmp1.getHeight(),""+aa.getHeight());
        float bei= aa.getHeight()/bmp1.getHeight();
        int cha = (int) (bmp1.getHeight() - aa.getWidth()/bei)/2;
        Log.d(aa.getHeight()+"=="+cha,""+bmp1.getHeight()*bei);
        Bitmap bmp2 = Bitmap.createBitmap(bmp1, cha, 0, (int) (aa.getWidth()/bei), bmp1.getHeight());
        blur(bmp2, aa);
    }

    public Bitmap readlv() {
        Bitmap bmp = null;
        try {
            FileInputStream fin = openFileInput("UserImg.png");
            bmp = BitmapFactory.decodeStream(fin);
            fin.close();
            return bmp;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @SuppressLint("NewApi")
    private void blur(Bitmap bkg, View view) {
        //Log.d("ov=","bkg" + bkg.getWidth()+" "+bkg.getHeight());

        long startMs = System.currentTimeMillis();
        float scaleFactor = 1;//图片缩放比例；
        float radius = 20;//模糊程度

        Bitmap overlay = Bitmap.createBitmap(
                (int) bkg.getWidth(),
                (int) bkg.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()/ scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);


        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        //Log.d("ov="+overlay.getWidth()+" "+overlay.getHeight(),"bkg" + bkg.getWidth()+" "+bkg.getHeight());
//        Bitmap oo = Bitmap.createBitmap(overlay, 0, 0,bkg.getWidth(), bkg.getHeight());
        view.setBackground(new BitmapDrawable(getResources(), overlay));
        /**
         * 打印高斯模糊处理时间，如果时间大约16ms，用户就能感到到卡顿，时间越长卡顿越明显，如果对模糊完图片要求不高，可是将scaleFactor设置大一些。
         */
//        Log.i("jerome", "blur time:" + (System.currentTimeMillis() - startMs));
    }

    public Bitmap blurBitmap(Bitmap bitmap){

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(getApplicationContext());

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the Allocations (in/out) with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
        blurScript.setRadius(1.f);

        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //recycle the original bitmap
        bitmap.recycle();

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;


    }

    /**
     * 获取系统状态栏和软件标题栏，部分软件没有标题栏，看自己软件的配置；
     * @return
     */
    private int getOtherHeight() {
        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
        int titleBarHeight = contentTop - statusBarHeight;
        return statusBarHeight + titleBarHeight;
    }

    public void readInfo(String tempInfo){
        try {
            Log.d("读取文件结果============",tempInfo);
//            JSONArray jsonArray = new JSONArray(tempInfo);
//            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = new JSONObject(tempInfo);
                id = jsonObject.getString("id");
                name = jsonObject.getString("name");
                sex  = jsonObject.getString("sex");
                birthday  = jsonObject.getString("birthday");
                city = jsonObject.getString("city");
                school = jsonObject.getString("school");
                college = jsonObject.getString("college");
                major = jsonObject.getString("major");
                grade = jsonObject.getString("grade");
                ocuu = jsonObject.getString("position");
                setInfo();
//            }
        } catch (JSONException e) {
            Toast.makeText(User.this,"信息读取错误",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void setInfo(){
        Log.d("JSON读取结果========",id +name +sex +birthday +city +school +college +major +grade +ocuu);
        TextView textId = findViewById(R.id.idText);
        if(!id.matches("null"))textId.setText(id);
        TextView textName= findViewById(R.id.nameText);
        if(!name.matches("null"))textName.setText(name);
        TextView textSex = findViewById(R.id.sexText);
        if(!sex.matches("null"))textSex.setText(sex);
        TextView textBir = findViewById(R.id.birText);
        if(!birthday.matches("null"))textBir.setText(birthday);
        TextView textCity = findViewById(R.id.cityText);
        if(!city.matches("null"))textCity.setText(city);

        TextView textSchool = findViewById(R.id.schoolText);
        textSchool.setText(school);
        TextView textCollege= findViewById(R.id.collegeText);
        textCollege.setText(college);
        TextView textMajor = findViewById(R.id.majorText);
        textMajor.setText(major);
        TextView textGrade = findViewById(R.id.gradeText);
        textGrade.setText(grade);
        TextView textOccupation = findViewById(R.id.occuText);
        textOccupation.setText(ocuu);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.changeImg:
//                List<TieBean> strings = new ArrayList<TieBean>();
//                strings.add(new TieBean("拍照"));
//                strings.add(new TieBean("从相册选择图片"));
//                DialogUIUtils.showSheet(this, strings, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
//                    @Override
//                    public void onItemClick(CharSequence text, int position) {
//                        Log.d("===============",text + "---" + position);
//                        if(position == 0){
                            uploadHeadImage();
//                        }
//                    }
//
//                    @Override
//                    public void onBottomBtnClick() {
//                        Log.d("===============","取消");
//                    }
//                }).show();
                break;

            case R.id.outuser://退出登录

                new AlertDialog
                        .Builder(this)
                        .setTitle("提示：")
                        .setMessage("您确定退出账号吗？")
                        .setPositiveButton("容我再想想", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                File path = new File(getFilesDir().getAbsolutePath());
                                FileTool.deleteFile(path);
                                File path2 = new File(getFilesDir().getAbsolutePath());
                                FileTool.deleteFile(path2);
                                finish();
                                Intent it = new Intent(User.this, MainActivity.class);
                                it.putExtra("page",2);
                                startActivity(it);

                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.birSet:
                initData();
                DatePickerDialog datePickerDialog = new DatePickerDialog(User.this, onDateSetListener, mYear, mMonth, mDay);
                long timeStamp = System.currentTimeMillis();
                datePickerDialog.getDatePicker().setMaxDate(timeStamp);
                datePickerDialog.show();
                break;
            case R.id.setSex:
                change_sex();
                break;
            case R.id.setCity:
                if (isLoaded) {
                    showPickerView();
                } else {
                    Toast.makeText(User.this, "正在加载数据", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.setName:
                onCreateNameDialog();
                //Toast.makeText(User.this, "不可修改", Toast.LENGTH_SHORT).show();
                break;


            case R.id.setSchool:
                List<TieBean> strings = new ArrayList<TieBean>();
                strings.add(new TieBean("白城师范学院"));
                strings.add(new TieBean("以前你没得选择 现在也没有"));
                DialogUIUtils.showSheet(this, strings, "", Gravity.CENTER, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        showToast(text);
                        String temp = (String) text;
                        if(!temp.matches(school)){
                            TextView textSchool = findViewById(R.id.schoolText);
                            textSchool.setText(temp);
                            changeFlag = true;
                            saveJson("school",temp);
                            try {
                                PutFwq("school",temp);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).show();
                break;
            case R.id.setCollege:
                ;
                break;
            case R.id.setMajor:
                ;
                break;
            case R.id.setGrade:
                List<TieBean> stringss = new ArrayList<TieBean>();
                stringss.add(new TieBean("2016"));
                stringss.add(new TieBean("2017"));
                stringss.add(new TieBean("2018"));
                stringss.add(new TieBean("2019"));
                DialogUIUtils.showSheet(this, stringss, "", Gravity.CENTER, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        showToast(text);
                        String temp = (String) text;
                        if(!temp.matches(school)){
                            TextView textSchool = findViewById(R.id.schoolText);
                            textSchool.setText(temp);
                            changeFlag = true;
                            saveJson("school",temp);
                            try {
                                PutFwq("school",temp);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).show();
                break;
            case R.id.setoccu:
                List<TieBean> stringsss = new ArrayList<TieBean>();
                stringsss.add(new TieBean("学生"));
                stringsss.add(new TieBean("教师"));
                DialogUIUtils.showSheet(this, stringsss, "", Gravity.CENTER, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        showToast(text);
                        String temp = (String) text;
                        if(!temp.matches(ocuu)){
                            TextView textSchool = findViewById(R.id.occuText);
                            textSchool.setText(temp);

                            if(temp.contains("教师")){
                                setGrade.setVisibility(View.GONE);
                            }else if(temp.contains("学生")){
                                setGrade.setVisibility(View.INVISIBLE);
                            }

                            changeFlag = true;
                            saveJson("Occupation",temp);
                            try {
                                PutFwq("Occupation",temp);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).show();
                break;

            default:
                break;
        }
    }

    public void showToast(CharSequence msg) {
        DialogUIUtils.showToastLong(msg.toString());
    }

    /**
     * 上传头像
     */
    private void uploadHeadImage() {
//        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
//        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
//        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
//        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
//        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
//        popupWindow.setOutsideTouchable(true);
//        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
//        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
//        //popupWindow在弹窗的时候背景半透明
//        final WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.alpha = 0.5f;
//        getWindow().setAttributes(params);
//        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                params.alpha = 1.0f;
//                getWindow().setAttributes(params);
//            }
//        });
//
//        btnCarema.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //权限判断
//                if (ContextCompat.checkSelfPermission(User.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED
//                        || ContextCompat.checkSelfPermission(User.this, Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    //申请WRITE_EXTERNAL_STORAGE权限
//                    ActivityCompat.requestPermissions(User.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//                    ActivityCompat.requestPermissions(User.this, new String[]{Manifest.permission.CAMERA,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//                } else {
//                    gotoCamera();
//                }
//                popupWindow.dismiss();
//            }
//        });
//        btnPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //权限判断
//                if (ContextCompat.checkSelfPermission(User.this, Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    //申请READ_EXTERNAL_STORAGE权限
//                    ActivityCompat.requestPermissions(User.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
//                } else {
//                    //跳转到相册
//                    gotoPhoto();
//                }
//                popupWindow.dismiss();
//            }
//        });
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });



        AlertDialog.Builder builder = new AlertDialog.Builder(User.this); //定义一个AlertDialog
        String[] strarr = {"拍照","从相册选择","取消"};
        builder.setItems(strarr, (arg0, arg1) -> {
            if (arg1 == 0) {
                                //权限判断
                if (ContextCompat.checkSelfPermission(User.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(User.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(User.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                    ActivityCompat.requestPermissions(User.this, new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                } else {
                    gotoCamera();
                }
            }else if(arg1 == 1){
                                //权限判断
                if (ContextCompat.checkSelfPermission(User.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(User.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
            }else{

            }

        });
        builder.show();
    }

    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }

    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "Android/com.pc.pc/image/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(User.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
            ActivityCompat.requestPermissions(User.this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            startActivityForResult(intent, REQUEST_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        LogUtils.d(requestCode);
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    ImageView headImage = findViewById(R.id.headImage);
                    headImage.setImageBitmap(bitMap);
                    saveBitmap(bitMap,"pc");
                    //saveImg(bitMap);

                    try {
                        LogUtils.d("uploadImg");
                        uploadImg(bitMap);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    //PutImg(getFilesDir().getAbsolutePath()+"/UserImg.png");
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......

                    deleteDir(getCacheDir());

                }
                break;
        }
    }


    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    public void saveImg(Bitmap bitmap) {

    }

    public void saveBitmap(Bitmap bitmap,String path) {

        try {
            FileOutputStream fos = null;
            fos= openFileOutput("UserImg.png", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            Log.e("tag", "saveBitmap: " + e.getMessage());
        }
    }
    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }


    public void change_sex(){
        AlertDialog.Builder builder = new AlertDialog.Builder(User.this); //定义一个AlertDialog
        String[] strarr = {"男","女","未设置"};
        builder.setItems(strarr, (arg0, arg1) -> {
            String sex = "2";
            if (arg1 == 0) {
                sex = "男";
            }else if(arg1 == 1){
                sex = "女";
            }else{
                sex = "未设置";
            }
            TextView textSex = findViewById(R.id.sexText);
            String temp = textSex.getText().toString();
            if(!temp.matches(sex)){
                textSex.setText(sex);
                changeFlag = true;
                saveJson("sex",sex);
                try {
                    PutFwq("sex",sex);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        builder.show();
    }


    public void saveJson(String key,String val){
        try {
            JSONObject jsonObject = null;
            JSONArray jsonArray = new JSONArray(ttInfo);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                jsonObject.put(key,val);
            }
            String zxc = "["+ jsonObject +"]";
            ttInfo = zxc;
            saveInfo(zxc);
            Log.d("=============", zxc);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void PutFwq(String key,String val) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.31.238/web/putfwq.php");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setUseCaches(false);
                    connection.connect();

                    String body = "id="+id+"&key="+key + "&val=" + val;
                    //Log.d(username,password);
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
                    writer.write(body);
                    writer.close();

                    Log.d("PUT====================",body);

                    int responseCode = connection.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                    }
                    else{
                        Toast.makeText(User.this,"网络连接失败",Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(User.this,"登录失败  请联系开发者",Toast.LENGTH_SHORT).show();
                }
            }
        }).start();

    }

    public void uploadImg(Bitmap bitmap2) throws MalformedURLException {

        String imgString = bitmapToBase64(bitmap2);
        Log.d("Base64================",imgString);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //上传图片参数需要与服务端沟通，我就不多做解释了，我添加的都是我们服务端需要的
        //你们根据情况自行更改
        //另外网络请求我就不多做解释了

        Log.d("url================","http://192.168.31.238:12345/users/"+id);
        URL url = new URL("http://192.168.31.238:12345/users/"+id);
        FormBody body = new FormBody.Builder().add("base64",imgString)
                .build();
        Request request = new Request.Builder().url(url).post(body).build();
        okHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
//                final String data = response.body().string();
//                Gson gson = new Gson();
//                final Beans bean = gson.fromJson(data, Beans.class);
//                Log.d(TAG, "onResponse: " + data);
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //加载图片用的Gilde框架，也可以自己自由选择，
//                        //""里面取决于服务端的返回值是否需要自行添加地址
//                        Glide.with(MainActivity.this).load(""+bean.getData().getUrl()).into(headIv);
//                    }
//                });
            }
        });
    }

    public static String bitmapToBase64(Bitmap bitmap) {

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.NO_WRAP);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void saveInfo(String temp) {
        String FILENAME = "UserInfo";
        FileOutputStream fos = null;
        try {
            //文件路径  /data/data/com.example.myapplication/files/
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(temp.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void onCreateNameDialog() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View nameView = layoutInflater.inflate(R.layout.dialog_setname, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(nameView);
        final EditText userInput = nameView.findViewById(R.id.changename_edit);
        final TextView name = findViewById(R.id.nameText);
        AlertDialog.Builder builder = alertDialogBuilder;
                builder.setPositiveButton("OK",
                         (dialog, which) -> {
                            String temp = name.getText().toString();
                            String temp2 = String.valueOf(userInput.getText());
                            if (!temp.matches(temp2)) {
                                name.setText(temp2);
                                saveJson("user_name",temp2);

                                try {
                                    PutFwq("user_name",temp2);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                changeFlag = true;
                            }
                        });

                builder.setNegativeButton("Cancel",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            finish();
            Intent it = new Intent(User.this, MainActivity.class);
            it.putExtra("page",3);
            startActivity(it);
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initData() {

        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;

            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).append("").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).append("").toString();
                }

            }
            TextView textBir = findViewById(R.id.birText);
            String temp = textBir.getText().toString();
            if(!temp.matches(days)){
                textBir.setText(days);
                saveJson("year",days);

                try {
                    PutFwq("year",days);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                changeFlag = true;
            }
        }
    };

    public String read(String file) {
        String result = "";
        try {
            FileInputStream fin = openFileInput(file);
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

    public void save(String temp) {
        String FILENAME = "Login.txt";
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(temp.getBytes());
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static boolean getuserFlag(){
        return userflag;
    }

    public static void SetFlag(boolean bftemp){
        userflag = bftemp;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {
                        //Toast.makeText(User.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    //Toast.makeText(User.this, "Parse Succeed", Toast.LENGTH_SHORT).show();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Toast.makeText(User.this, "Parse Failed", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private void showPickerView() {

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, (options1, options2, options3, v) -> {
            String opt1tx = options1Items.size() > 0 ?
                    options1Items.get(options1).getPickerViewText() : "";

            String opt2tx = options2Items.size() > 0
                    && options2Items.get(options1).size() > 0 ?
                    options2Items.get(options1).get(options2) : "";

            String opt3tx = options2Items.size() > 0
                    && options3Items.get(options1).size() > 0
                    && options3Items.get(options1).get(options2).size() > 0 ?
                    options3Items.get(options1).get(options2).get(options3) : "";

            String tx = opt1tx + opt2tx + opt3tx;
            Toast.makeText(User.this, tx, Toast.LENGTH_SHORT).show();

            TextView textCity = findViewById(R.id.cityText);

            String temp = textCity.getText().toString();
            if(!temp.matches(tx)){
                textCity.setText(tx);
                saveJson("city",tx);

                try {
                    PutFwq("city",tx);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                changeFlag = true;
            }

        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK)
                .setContentTextSize(20)
                .setCancelText("返回哦")
                .setSubmitText("确定呀")
                .build();

        //pvOptions.setPicker(options1Items);//一级
        //pvOptions.setPicker(options1Items, options2Items);//二级
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级
        pvOptions.show();
    }

    private void initJsonData() {
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
        }
    }
}
