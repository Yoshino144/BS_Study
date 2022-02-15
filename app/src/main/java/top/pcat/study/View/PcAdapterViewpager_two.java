package top.pcat.study.View;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.apkfuns.logutils.LogUtils;

import top.pcat.study.Utils.ReadAssets;
import top.pcat.study.R;
import top.pcat.study.Size.Examination;

import org.apache.http.util.EncodingUtils;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PcAdapterViewpager_two extends PagerAdapter {

    private String tid;
    private String type = "error";
    private String answer;
    private String subject;
    private int cha_id;
    private String A;
    private String B;
    private String C;
    private String D;
    private String analysis;
    private List<View> mViewList;
    private String data ;
    private String pc = "";
    private String[] flag =  new String[5000];
    private boolean[] Done = new boolean[5000];
    private Examination mContext;
    private ReadAssets readAssets;
    private View convertView;
    private int wrongSize = 0;
    private int trueSize = 0;
    private int allSize = 0;
    private int idsize = 0;
//    private Handler handler;
    private Handler handler;
    String tihao;
    String xuanxiang;
    private Examination examination_mContext;

    public PcAdapterViewpager_two(Examination context, boolean[] Done, String[] flag, List<View> mViewList, String data, String pc, String tihao, String xuanxiang, Handler handlerXuan) {
        this.mViewList = mViewList;
        this.data = data;
        this.pc = pc;
        this.mContext = context;
        this.handler = handlerXuan;
        this.tihao = tihao;
        this.xuanxiang = xuanxiang;
        this.flag = flag;
        this.Done = Done;
    }



    @Override
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NotNull
    @SuppressLint("HandlerLeak")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position),0);
        final PcAdapterViewpager_two.ViewHolder hold = new PcAdapterViewpager_two.ViewHolder();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x11) {
                    Bundle bundle = msg.getData();
                    String date = bundle.getString("msg");
                    LogUtils.d("===========",date);
                }
            }
        };



        hold.title =  container.findViewById(R.id.textmm);
        hold.text_A = container.findViewById(R.id.text_A);
        hold.text_B = container.findViewById(R.id.text_B);
        hold.text_C = container.findViewById(R.id.text_C);
        hold.text_D = container.findViewById(R.id.text_D);
        hold.aaa = container.findViewById(R.id.AAA);
        hold.bbb = container.findViewById(R.id.BBB);
        hold.ccc = container.findViewById(R.id.CCC);
        hold.ddd = container.findViewById(R.id.DDD);
        hold.image_A = container.findViewById(R.id.image_A);
        hold.image_B = container.findViewById(R.id.image_B);
        hold.image_C = container.findViewById(R.id.image_C);
        hold.image_D = container.findViewById(R.id.image_D);
        hold.wrongaa = container.findViewById(R.id.wrongaa);
        hold.trueaa = container.findViewById(R.id.trueaa);
        hold.jiexi = container.findViewById(R.id.jiexi);

        String path = data + ".json";
        //String json = getFromAssets("c++.json");
        String json = read(data+".json");
        LogUtils.d("PcAda======","加载科目====="+data);
        readSj(json,position);

        //查看解析
        hold.trueaa.setOnClickListener(v->{
            hold.trueaa.setVisibility(View.GONE);
            hold.wrongaa.setVisibility(View.VISIBLE);
        });



            //LogUtils.d("页数==============", String.valueOf(position));
            //判断题型
            //选择题
            if (type.matches("Choice")) {

                hold.title.setText(subject);
                hold.text_A.setText(A);
                hold.text_B.setText(B);
                hold.text_C.setText(C);
                hold.text_D.setText(D);
                hold.jiexi.setText(analysis);

                //            private String[] flag =  new String[5000];  选的啥
                //            private boolean[] Done = new boolean[5000]; 做没做
                //判断是否语音选择
                LogUtils.d("判断语音选项",tihao + "===" + xuanxiang);
                if (tihao.matches("null") && xuanxiang.matches("null")) {
                    LogUtils.d("没有语音选项"," ");
//                    if (!Done[position]) {
//                    } else {
                        getAnswer(json, position);

                        LogUtils.d("===================", "没做" + position);
                        //选择
                        hold.aaa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LogUtils.d("点击了一下A");
                                flag[position] = "A";
                                mContext.setFlag(position,"A");
                                getId(json, position);
                                getAnswer(json, position);
                                if (true) {
                                    allSize += 1;
                                    Done[position] = true;
                                    mContext.setDone(position);
                                    hold.image_A.setImageResource(R.drawable.dooo);
                                    hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
                                    hold.image_B.setImageResource(R.drawable.bbb);
                                    hold.text_B.setTextColor(Color.parseColor("#9a9a9a"));
                                    hold.image_C.setImageResource(R.drawable.ccc);
                                    hold.text_C.setTextColor(Color.parseColor("#9a9a9a"));
                                    hold.image_D.setImageResource(R.drawable.ddd);
                                    hold.text_D.setTextColor(Color.parseColor("#9a9a9a"));
                                    LogUtils.d("点击了A选项，题号" + position, flag[position] + "答案" + answer);
                                    if (answer.matches("A")) {
//                                        hold.trueaa.setVisibility(View.VISIBLE);
//                                        hold.image_A.setImageResource(R.drawable.ttrue);
//                                        hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
                                        trueSize += 1;
                                        mContext.addtrue(position+1);
                                    } else {
//                                        hold.wrongaa.setVisibility(View.VISIBLE);
                                        mContext.addwrong(position+1,answer+cha_id+subject);
                                        wrongSize += 1;
//                                        hold.image_A.setImageResource(R.drawable.ttrue);
//                                        hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
//                                        if (answer.matches("A")) {
//                                            hold.image_A.setImageResource(R.drawable.ttrue);
//                                            hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
//                                        } else if (answer.matches("B")) {
//                                            hold.image_B.setImageResource(R.drawable.ttrue);
//                                            hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
//                                        } else if (answer.matches("C")) {
//                                            hold.image_C.setImageResource(R.drawable.ttrue);
//                                            hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
//                                        } else if (answer.matches("D")) {
//                                            hold.image_D.setImageResource(R.drawable.ttrue);
//                                            hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
//                                        }
                                    }
                                }
                            }
                        });

                        hold.bbb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getId(json, position);
                                flag[position] = "B";
                                mContext.setFlag(position,"B");
                                getAnswer(json, position);

                                if (true) {
                                    allSize += 1;
                                    LogUtils.d("点击了B选项，题号" + position, flag[position] + "答案" + answer);
                                    Done[position] = true;
                                    mContext.setDone(position);

                                    hold.image_A.setImageResource(R.drawable.aaa);
                                    hold.text_A.setTextColor(Color.parseColor("#9a9a9a"));
                                    hold.image_B.setImageResource(R.drawable.dooo);
                                    hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
                                    hold.image_C.setImageResource(R.drawable.ccc);
                                    hold.text_C.setTextColor(Color.parseColor("#9a9a9a"));
                                    hold.image_D.setImageResource(R.drawable.ddd);
                                    hold.text_D.setTextColor(Color.parseColor("#9a9a9a"));

                                    if (answer.matches("B")) {
//                                        hold.trueaa.setVisibility(View.VISIBLE);
//                                        hold.image_B.setImageResource(R.drawable.ttrue);
//                                        hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
                                        trueSize += 1;
                                        mContext.addtrue(position+1);
                                    } else {
//                                        hold.wrongaa.setVisibility(View.VISIBLE);
                                        mContext.addwrong(position+1,answer+cha_id+subject);
                                        wrongSize += 1;
//                                        hold.image_B.setImageResource(R.drawable.ttrue);
//                                        hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
//                                        if (answer.matches("A")) {
//                                            hold.image_A.setImageResource(R.drawable.ttrue);
//                                            hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
//                                        } else if (answer.matches("B")) {
//                                            hold.image_B.setImageResource(R.drawable.ttrue);
//                                            hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
//                                        } else if (answer.matches("C")) {
//                                            hold.image_C.setImageResource(R.drawable.ttrue);
//                                            hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
//                                        } else if (answer.matches("D")) {
//                                            hold.image_D.setImageResource(R.drawable.ttrue);
//                                            hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
//                                        }
                                    }
                                }
                            }
                        });

                        hold.ccc.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                flag[position] = "C";
                                mContext.setFlag(position,"C");
                                getId(json, position);
                                getAnswer(json, position);

                                if (true) {
                                    allSize += 1;
                                    LogUtils.d("点击了C选项，题号" + position, flag[position] + "答案" + answer);
                                    Done[position] = true;
                                    mContext.setDone(position);

                                    hold.image_A.setImageResource(R.drawable.aaa);
                                    hold.text_A.setTextColor(Color.parseColor("#9a9a9a"));
                                    hold.image_B.setImageResource(R.drawable.bbb);
                                    hold.text_B.setTextColor(Color.parseColor("#9a9a9a"));
                                    hold.image_C.setImageResource(R.drawable.dooo);
                                    hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
                                    hold.image_D.setImageResource(R.drawable.ddd);
                                    hold.text_D.setTextColor(Color.parseColor("#9a9a9a"));

                                    if (answer.matches("C")) {
//                                        hold.trueaa.setVisibility(View.VISIBLE);
//                                        hold.image_C.setImageResource(R.drawable.ttrue);
//                                        hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
                                        trueSize += 1;
                                        mContext.addtrue(position+1);
                                    } else {
//                                        hold.wrongaa.setVisibility(View.VISIBLE);
                                        mContext.addwrong(position+1,answer+cha_id+subject);
                                        wrongSize += 1;
//                                        hold.image_C.setImageResource(R.drawable.ttrue);
//                                        hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
//                                        if (answer.matches("A")) {
//                                            hold.image_A.setImageResource(R.drawable.ttrue);
//                                            hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
//                                        } else if (answer.matches("B")) {
//                                            hold.image_B.setImageResource(R.drawable.ttrue);
//                                            hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
//                                        } else if (answer.matches("C")) {
//                                            hold.image_C.setImageResource(R.drawable.ttrue);
//                                            hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
//                                        } else if (answer.matches("D")) {
//                                            hold.image_D.setImageResource(R.drawable.ttrue);
//                                            hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
//                                        }
                                    }
                                }
                            }
                        });

                        hold.ddd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                flag[position] = "D";
                                mContext.setFlag(position,"D");
                                getId(json, position);
                                getAnswer(json, position);

                                if (true) {
                                    allSize += 1;
                                    Done[position] = true;
                                    mContext.setDone(position);

                                    hold.image_A.setImageResource(R.drawable.aaa);
                                    hold.text_A.setTextColor(Color.parseColor("#9a9a9a"));
                                    hold.image_B.setImageResource(R.drawable.bbb);
                                    hold.text_B.setTextColor(Color.parseColor("#9a9a9a"));
                                    hold.image_C.setImageResource(R.drawable.ccc);
                                    hold.text_C.setTextColor(Color.parseColor("#9a9a9a"));
                                    hold.image_D.setImageResource(R.drawable.dooo);
                                    hold.text_D.setTextColor(Color.parseColor("#1cabfa"));

                                    LogUtils.d("点击了D选项，题号" + position, flag[position] + "答案" + answer);
                                    if (answer.matches("D")) {
//                                        hold.trueaa.setVisibility(View.VISIBLE);
//                                        hold.image_D.setImageResource(R.drawable.ttrue);
//                                        hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
                                        trueSize += 1;
                                        mContext.addtrue(position+1);
                                    } else {
//                                        hold.wrongaa.setVisibility(View.VISIBLE);
                                        mContext.addwrong(position+1,answer+cha_id+subject);
                                        wrongSize += 1;
//                                        hold.image_D.setImageResource(R.drawable.ttrue);
//                                        hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
//                                        if (answer.matches("A")) {
//                                            hold.image_A.setImageResource(R.drawable.ttrue);
//                                            hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
//                                        } else if (answer.matches("B")) {
//                                            hold.image_B.setImageResource(R.drawable.ttrue);
//                                            hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
//                                        } else if (answer.matches("C")) {
//                                            hold.image_C.setImageResource(R.drawable.ttrue);
//                                            hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
//                                        } else if (answer.matches("D")) {
//                                            hold.image_D.setImageResource(R.drawable.ttrue);
//                                            hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
//                                        }
                                    }
                                }
                            }
                        });
//                    }
                }
                else{
                    //语音选择了
                    LogUtils.d("语音选择了", String.valueOf(position) + "===== "+tihao + xuanxiang );
                    int tiNum = Integer.parseInt(tihao);
                    if(tiNum-1 == position){
                        getAnswer(json, position);
                        getId(json, position);
                        if(xuanxiang.matches("a")){
                            LogUtils.d("语音选择了" + xuanxiang, "答案" +answer);
                            LogUtils.d("点击了A选项，题号" + position, flag[position] + "答案" + answer);
                            flag[position] = "A";
                            mContext.setFlag(position,"A");
                            if (Done[position] == false) {
                                allSize += 1;
                                Done[position] = true;
                                mContext.setDone(position);
                                LogUtils.d("点击了A选项，题号" + position, flag[position] + "答案" + answer);
                                if (answer.matches("A")) {
//                                    hold.trueaa.setVisibility(View.VISIBLE);
                                    hold.image_A.setImageResource(R.drawable.ttrue);
                                    hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
                                    trueSize += 1;
                                    mContext.addtrue(position+1);
                                } else {
//                                    hold.wrongaa.setVisibility(View.VISIBLE);
                                    mContext.addwrong(position+1,answer+cha_id+subject);
                                    wrongSize += 1;
                                    hold.image_A.setImageResource(R.drawable.wrong);
                                    hold.text_A.setTextColor(Color.parseColor("#fd6767"));
                                    if (answer.matches("A")) {
                                        hold.image_A.setImageResource(R.drawable.ttrue);
                                        hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
                                    } else if (answer.matches("B")) {
                                        hold.image_B.setImageResource(R.drawable.ttrue);
                                        hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
                                    } else if (answer.matches("C")) {
                                        hold.image_C.setImageResource(R.drawable.ttrue);
                                        hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
                                    } else if (answer.matches("D")) {
                                        hold.image_D.setImageResource(R.drawable.ttrue);
                                        hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
                                    }
                                }
                            }

                        }else if(xuanxiang.matches("b")){

                            LogUtils.d("点击了B选项，题号" + position, flag[position] + "答案" + answer);
                            flag[position] = "B";
                            mContext.setFlag(position,"B");

                            if (Done[position] == false) {
                                allSize += 1;
                                LogUtils.d("点击了B选项，题号" + position, flag[position] + "答案" + answer);
                                Done[position] = true;
                                mContext.setDone(position);
                                if (answer.matches("B")) {
//                                    hold.trueaa.setVisibility(View.VISIBLE);
                                    hold.image_B.setImageResource(R.drawable.ttrue);
                                    hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
                                    trueSize += 1;
                                    mContext.addtrue(position+1);
                                } else {
//                                    hold.wrongaa.setVisibility(View.VISIBLE);
                                    mContext.addwrong(position+1,answer+cha_id+subject);
                                    wrongSize += 1;
                                    hold.image_B.setImageResource(R.drawable.wrong);
                                    hold.text_B.setTextColor(Color.parseColor("#fd6767"));
                                    if (answer.matches("A")) {
                                        hold.image_A.setImageResource(R.drawable.ttrue);
                                        hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
                                    } else if (answer.matches("B")) {
                                        hold.image_B.setImageResource(R.drawable.ttrue);
                                        hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
                                    } else if (answer.matches("C")) {
                                        hold.image_C.setImageResource(R.drawable.ttrue);
                                        hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
                                    } else if (answer.matches("D")) {
                                        hold.image_D.setImageResource(R.drawable.ttrue);
                                        hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
                                    }
                                }
                            }

                        }else if(xuanxiang.matches("c")){

                            flag[position] = "C";
                            mContext.setFlag(position,"C");

                            if (Done[position] == false) {
                                allSize += 1;
                                LogUtils.d("点击了C选项，题号" + position, flag[position] + "答案" + answer);
                                Done[position] = true;
                                mContext.setDone(position);
                                if (answer.matches("C")) {
//                                    hold.trueaa.setVisibility(View.VISIBLE);
                                    hold.image_C.setImageResource(R.drawable.ttrue);
                                    hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
                                    trueSize += 1;
                                    mContext.addtrue(position+1);
                                } else {
//                                    hold.wrongaa.setVisibility(View.VISIBLE);
                                    mContext.addwrong(position+1,answer+cha_id+subject);
                                    wrongSize += 1;
                                    hold.image_C.setImageResource(R.drawable.wrong);
                                    hold.text_C.setTextColor(Color.parseColor("#fd6767"));
                                    if (answer.matches("A")) {
                                        hold.image_A.setImageResource(R.drawable.ttrue);
                                        hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
                                    } else if (answer.matches("B")) {
                                        hold.image_B.setImageResource(R.drawable.ttrue);
                                        hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
                                    } else if (answer.matches("C")) {
                                        hold.image_C.setImageResource(R.drawable.ttrue);
                                        hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
                                    } else if (answer.matches("D")) {
                                        hold.image_D.setImageResource(R.drawable.ttrue);
                                        hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
                                    }
                                }
                            }

                        }else if(xuanxiang.matches("d")){
                            flag[position] = "D";
                            mContext.setFlag(position,"D");

                            if (Done[position] == false) {
                                allSize += 1;
                                Done[position] = true;
                                mContext.setDone(position);

                                LogUtils.d("点击了D选项，题号" + position, flag[position] + "答案" + answer);
                                if (answer.matches("D")) {
//                                    hold.trueaa.setVisibility(View.VISIBLE);
                                    hold.image_D.setImageResource(R.drawable.ttrue);
                                    hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
                                    trueSize += 1;
                                    mContext.addtrue(position+1);
                                } else {
//                                    hold.wrongaa.setVisibility(View.VISIBLE);
                                    mContext.addwrong(position+1,answer+cha_id+subject);
                                    wrongSize += 1;
                                    hold.image_D.setImageResource(R.drawable.wrong);
                                    hold.text_D.setTextColor(Color.parseColor("#fd6767"));
                                    if (answer.matches("A")) {
                                        hold.image_A.setImageResource(R.drawable.ttrue);
                                        hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
                                    } else if (answer.matches("B")) {
                                        hold.image_B.setImageResource(R.drawable.ttrue);
                                        hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
                                    } else if (answer.matches("C")) {
                                        hold.image_C.setImageResource(R.drawable.ttrue);
                                        hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
                                    } else if (answer.matches("D")) {
                                        hold.image_D.setImageResource(R.drawable.ttrue);
                                        hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
                                    }
                                }
                            }
                        }
                    }

                }


            } else {

            }



        return mViewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return String.valueOf(position+1);
    }


    public void getAnswer(String tempInfo,int postion){
        try {
            JSONArray jsonArray = new JSONArray(tempInfo);
            JSONObject jsonObject = jsonArray.getJSONObject(postion);
            answer  = jsonObject.getString("problem_answer");
            String id = jsonObject.getString("problem_id");
            LogUtils.d(id+"题答案",  answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getId(String tempInfo,int postion){
        try {
            JSONArray jsonArray = new JSONArray(tempInfo);
            JSONObject jsonObject = jsonArray.getJSONObject(postion);
            idsize  = Integer.parseInt(jsonObject.getString("problem_id"));

            LogUtils.d(postion+"题答案",  answer);
        } catch (JSONException e) {
            e.printStackTrace();//rr
        }
    }

    public void readSj(String tempInfo,int postion){
        try {
            JSONArray jsonArray = new JSONArray(tempInfo);
            JSONObject jsonObject = jsonArray.getJSONObject(postion);
            tid = jsonObject.getString("problem_id");
            type = jsonObject.getString("problem_type");
            //answer  = jsonObject.getString("Answer");
            subject = jsonObject.getString("problem_content");
            A = jsonObject.getString("A");
            B = jsonObject.getString("B");
            C = jsonObject.getString("C");
            D = jsonObject.getString("D");
            analysis = jsonObject.getString("problem_analysis");
            cha_id = Integer.parseInt(jsonObject.getString("chapter_id"));

           // LogUtils.d("pcpc", tid + type + answer + subject + A + B + C + D + analysis);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getFromAssets(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader( mContext.getResources().getAssets().open(fileName) );
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String read(String file) {
        String result = "";
        try {
            FileInputStream fin = mContext.openFileInput(file);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer,"UTF-8");
            fin.close();
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }


    public class ViewHolder {
        TextView title;
        TextView text_A;
        TextView text_B;
        TextView text_C;
        TextView text_D;
        LinearLayout aaa;
        LinearLayout bbb;
        LinearLayout ccc;
        LinearLayout ddd;
        ImageView image_A;
        ImageView image_B;
        ImageView image_C;
        ImageView image_D;
        LinearLayout wrongaa;
        LinearLayout trueaa;
        TextView jiexi;
    }

    private class BaseFragment {
    }
}
