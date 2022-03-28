package top.pcat.study.Exercises;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.apkfuns.logutils.LogUtils;
import com.blankj.utilcode.util.FileIOUtils;

import top.pcat.study.Pojo.UserProblemData;
import top.pcat.study.Utils.ReadAssets;
import top.pcat.study.R;
import top.pcat.study.Size.Examination;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class PcAdapterViewpager extends PagerAdapter {

    private String tid;
    private String type = "error";
    private String answer;
    private String subject;
    private String A;
    private String B;
    private String C;
    private String D;
    private String analysis;
    private List<View> mViewList;
    private String data;
    private String pc = "";
    private String[] flag = new String[5000];
    private boolean[] Done = new boolean[5000];
    private ExercisesActivity mContext;
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

    public PcAdapterViewpager(ExercisesActivity context, boolean[] Done, String[] flag, List<View> mViewList, String data, String pc, String tihao, String xuanxiang, Handler handlerXuan) {
        this.mViewList = mViewList;
        this.data = data;  //题目文件名
        this.pc = pc; //题目信息
        this.mContext = context;
        this.handler = handlerXuan;
        this.tihao = tihao;
        this.xuanxiang = xuanxiang;
        this.flag = flag;
        this.Done = Done;
    }

    public PcAdapterViewpager(Examination context, boolean[] Done, String[] flag, List<View> mViewList, String data, String pc, String tihao, String xuanxiang, Handler handlerXuan) {
        this.mViewList = mViewList;
        this.data = data;
        this.pc = pc;
        this.examination_mContext = context;
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
        container.addView(mViewList.get(position), 0);
        final PcAdapterViewpager.ViewHolder hold = new PcAdapterViewpager.ViewHolder();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x11) {
                    Bundle bundle = msg.getData();
                    String date = bundle.getString("msg");
                    //LogUtils.d("===========",date);
                }
            }
        };


        hold.title = container.findViewById(R.id.textmm);
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
        String json = read(data );
        LogUtils.d("PcAda======"+"加载科目=====" + data);
        LogUtils.d("json==========="+json);
        readSj(json, position);

        //查看解析
        hold.trueaa.setOnClickListener(v -> {
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
            LogUtils.d("判断语音选项"+tihao + "===" + xuanxiang);
            if (tihao.matches("null") && xuanxiang.matches("null")) {
                LogUtils.d("没有语音选项"+ " ");
                if (Done[position] == true) {
                    hold.trueaa.setVisibility(View.VISIBLE);
                    getAnswer(json, position);
                    LogUtils.d("===================", "做了" + position + flag[position] + answer);
                    if (flag[position].matches(answer)) {
                        if (flag[position].matches("A")) {
                            hold.image_A.setImageResource(R.drawable.ttrue);
                            hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
                        } else if (flag[position].matches("B")) {
                            hold.image_B.setImageResource(R.drawable.ttrue);
                            hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
                        } else if (flag[position].matches("C")) {
                            hold.image_C.setImageResource(R.drawable.ttrue);
                            hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
                        } else if (flag[position].matches("D")) {
                            hold.image_D.setImageResource(R.drawable.ttrue);
                            hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
                        }
                    } else {
                        hold.wrongaa.setVisibility(View.VISIBLE);
                        if (flag[position].matches("A")) {
                            hold.image_A.setImageResource(R.drawable.wrong);
                            hold.text_A.setTextColor(Color.parseColor("#fd6767"));
                        } else if (flag[position].matches("B")) {
                            hold.image_B.setImageResource(R.drawable.wrong);
                            hold.text_B.setTextColor(Color.parseColor("#fd6767"));
                        } else if (flag[position].matches("C")) {
                            hold.image_C.setImageResource(R.drawable.wrong);
                            hold.text_C.setTextColor(Color.parseColor("#fd6767"));
                        } else if (flag[position].matches("D")) {
                            hold.image_D.setImageResource(R.drawable.wrong);
                            hold.text_D.setTextColor(Color.parseColor("#fd6767"));
                        }
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
                } else {
                    getAnswer(json, position);

                    LogUtils.d("===================", "没做" + position);
                    //选择
                    hold.aaa.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            flag[position] = "A";
                            mContext.setFlag(position, "A");
                            UserProblemData userProblemData = getId(json, position);
                            LogUtils.d("pcAdapter创建做题记录对象：" + userProblemData);
                            getAnswer(json, position);
                            if (Done[position] == false) {
                                allSize += 1;
                                Done[position] = true;
                                mContext.setDone(position);
                                LogUtils.d("点击了A选项，题号" + position, flag[position] + "答案" + answer);
                                if (answer.matches("A")) {
                                    hold.trueaa.setVisibility(View.VISIBLE);
                                    hold.image_A.setImageResource(R.drawable.ttrue);
                                    hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
                                    trueSize += 1;
                                    mContext.addtrue(idsize,userProblemData);
                                } else {
                                    hold.wrongaa.setVisibility(View.VISIBLE);
                                    mContext.addwrong(idsize,userProblemData);
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
                        }
                    });

                    hold.bbb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            UserProblemData userProblemData = getId(json, position);
                            flag[position] = "B";
                            mContext.setFlag(position, "B");
                            getAnswer(json, position);

                            if (Done[position] == false) {
                                allSize += 1;
                                LogUtils.d("点击了B选项，题号" + position, flag[position] + "答案" + answer);
                                Done[position] = true;
                                mContext.setDone(position);
                                if (answer.matches("B")) {
                                    hold.trueaa.setVisibility(View.VISIBLE);
                                    hold.image_B.setImageResource(R.drawable.ttrue);
                                    hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
                                    trueSize += 1;
                                    mContext.addtrue(idsize,userProblemData);
                                } else {
                                    hold.wrongaa.setVisibility(View.VISIBLE);
                                    mContext.addwrong(idsize,userProblemData);
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
                        }
                    });

                    hold.ccc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            flag[position] = "C";
                            mContext.setFlag(position, "C");
                            UserProblemData userProblemData = getId(json, position);
                            getAnswer(json, position);

                            if (Done[position] == false) {
                                allSize += 1;
                                LogUtils.d("点击了C选项，题号" + position, flag[position] + "答案" + answer);
                                Done[position] = true;
                                mContext.setDone(position);
                                if (answer.matches("C")) {
                                    hold.trueaa.setVisibility(View.VISIBLE);
                                    hold.image_C.setImageResource(R.drawable.ttrue);
                                    hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
                                    trueSize += 1;
                                    mContext.addtrue(idsize,userProblemData);
                                } else {
                                    hold.wrongaa.setVisibility(View.VISIBLE);
                                    mContext.addwrong(idsize,userProblemData);
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
                        }
                    });

                    hold.ddd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            flag[position] = "D";
                            mContext.setFlag(position, "D");
                            UserProblemData userProblemData =  getId(json, position);
                            getAnswer(json, position);

                            if (Done[position] == false) {
                                allSize += 1;
                                Done[position] = true;
                                mContext.setDone(position);

                                LogUtils.d("点击了D选项，题号" + position, flag[position] + "答案" + answer);
                                if (answer.matches("D")) {
                                    hold.trueaa.setVisibility(View.VISIBLE);
                                    hold.image_D.setImageResource(R.drawable.ttrue);
                                    hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
                                    trueSize += 1;
                                    mContext.addtrue(idsize,userProblemData);
                                } else {
                                    hold.wrongaa.setVisibility(View.VISIBLE);
                                    mContext.addwrong(idsize,userProblemData);
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
                    });
                }
            } else {
                //语音选择了
                LogUtils.d("语音选择了", String.valueOf(position) + "===== " + tihao + xuanxiang);
                int tiNum = Integer.parseInt(tihao);
                if (tiNum - 1 == position) {
                    getAnswer(json, position);
                    UserProblemData userProblemData = getId(json, position);
                    if (xuanxiang.matches("a")) {
                        LogUtils.d("语音选择了" + xuanxiang, "答案" + answer);
                        LogUtils.d("点击了A选项，题号" + position, flag[position] + "答案" + answer);
                        flag[position] = "A";
                        mContext.setFlag(position, "A");
                        if (Done[position] == false) {
                            allSize += 1;
                            Done[position] = true;
                            mContext.setDone(position);
                            LogUtils.d("点击了A选项，题号" + position, flag[position] + "答案" + answer);
                            if (answer.matches("A")) {
                                hold.trueaa.setVisibility(View.VISIBLE);
                                hold.image_A.setImageResource(R.drawable.ttrue);
                                hold.text_A.setTextColor(Color.parseColor("#1cabfa"));
                                trueSize += 1;
                                mContext.addtrue(idsize,userProblemData);
                            } else {
                                hold.wrongaa.setVisibility(View.VISIBLE);
                                mContext.addwrong(idsize,userProblemData);
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

                    } else if (xuanxiang.matches("b")) {

                        LogUtils.d("点击了B选项，题号" + position, flag[position] + "答案" + answer);
                        flag[position] = "B";
                        mContext.setFlag(position, "B");

                        if (Done[position] == false) {
                            allSize += 1;
                            LogUtils.d("点击了B选项，题号" + position, flag[position] + "答案" + answer);
                            Done[position] = true;
                            mContext.setDone(position);
                            if (answer.matches("B")) {
                                hold.trueaa.setVisibility(View.VISIBLE);
                                hold.image_B.setImageResource(R.drawable.ttrue);
                                hold.text_B.setTextColor(Color.parseColor("#1cabfa"));
                                trueSize += 1;
                                mContext.addtrue(idsize,userProblemData);
                            } else {
                                hold.wrongaa.setVisibility(View.VISIBLE);
                                mContext.addwrong(idsize,userProblemData);
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

                    } else if (xuanxiang.matches("c")) {

                        flag[position] = "C";
                        mContext.setFlag(position, "C");

                        if (Done[position] == false) {
                            allSize += 1;
                            LogUtils.d("点击了C选项，题号" + position, flag[position] + "答案" + answer);
                            Done[position] = true;
                            mContext.setDone(position);
                            if (answer.matches("C")) {
                                hold.trueaa.setVisibility(View.VISIBLE);
                                hold.image_C.setImageResource(R.drawable.ttrue);
                                hold.text_C.setTextColor(Color.parseColor("#1cabfa"));
                                trueSize += 1;
                                mContext.addtrue(idsize,userProblemData);
                            } else {
                                hold.wrongaa.setVisibility(View.VISIBLE);
                                mContext.addwrong(idsize,userProblemData);
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

                    } else if (xuanxiang.matches("d")) {
                        flag[position] = "D";
                        mContext.setFlag(position, "D");

                        if (Done[position] == false) {
                            allSize += 1;
                            Done[position] = true;
                            mContext.setDone(position);

                            LogUtils.d("点击了D选项，题号" + position, flag[position] + "答案" + answer);
                            if (answer.matches("D")) {
                                hold.trueaa.setVisibility(View.VISIBLE);
                                hold.image_D.setImageResource(R.drawable.ttrue);
                                hold.text_D.setTextColor(Color.parseColor("#1cabfa"));
                                trueSize += 1;
                                mContext.addtrue(idsize,userProblemData);
                            } else {
                                hold.wrongaa.setVisibility(View.VISIBLE);
                                mContext.addwrong(idsize,userProblemData);
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
        return String.valueOf(position + 1);
    }


    public void getAnswer(String tempInfo, int postion) {
        try {
            JSONArray jsonArray = new JSONArray(tempInfo);
            JSONObject jsonObject = jsonArray.getJSONObject(postion);
            answer = jsonObject.getString("problemAnswer");
            String id = jsonObject.getString("problemId");
            LogUtils.d(id + "题答案"+answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public UserProblemData getId(String tempInfo, int postion) {
        try {
            UserProblemData userProblemData = new UserProblemData();
            JSONArray jsonArray = new JSONArray(tempInfo);
            JSONObject jsonObject = jsonArray.getJSONObject(postion);
            idsize = Integer.parseInt(jsonObject.getString("problemId"));
            userProblemData.setProblemId(jsonObject.getInt("problemId"));
            userProblemData.setAnswer(jsonObject.getString("problemAnswer"));
            userProblemData.setChapterId(jsonObject.getInt("chapterId"));
            userProblemData.setSubjectId(jsonObject.getInt("subjectId"));

            LogUtils.d(postion + "题答案", answer);
            return userProblemData;
        } catch (JSONException e) {
            e.printStackTrace();//rr
        }
        return null;
    }

    public void readSj(String tempInfo, int postion) {

//        {
//            "problemId": 3,
//                "problemContent": "以下叙述不正确的是。",
//                "problemType": null,
//                "problemAnswer": "D",
//                "problemAnalysis": null,
//                "chapterId": 1,
//                "founderSubject": null,
//                "timeSubject": "2022-01-17T16:20:10.000+00:00",
//                "deleteSubject": 0,
//                "a": "一个C源程序可由一个或多个函数组成",
//                "c": "C程序的基本组成单位是函数",
//                "d": "在C程序中,注释说明只能位于一条语句的后面",
//                "b": "一个C源程序必须包含一个main函数"
//        }
        try {
            JSONArray jsonArray = new JSONArray(tempInfo);
            JSONObject jsonObject = jsonArray.getJSONObject(postion);
            tid = jsonObject.getString("problemId");
            type = jsonObject.getString("problemType");
            //answer  = jsonObject.getString("Answer");
            subject = jsonObject.getString("problemContent");
            A = jsonObject.getString("a");
            B = jsonObject.getString("b");
            C = jsonObject.getString("c");
            D = jsonObject.getString("d");
            analysis = jsonObject.getString("problemAnalysis");

            LogUtils.d("加载题目：题号："+tid+" 类型" + type + answer + subject + A + B + C + D + analysis);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(mContext.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public String read(String file) {
        LogUtils.d("读取"+mContext.getFilesDir().getAbsolutePath() + "/problems/"+file);
        String json = FileIOUtils.readFile2String(mContext.getFilesDir().getAbsolutePath() + "/problems/"+file);
        LogUtils.d("结果"+json);
        return json;
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
