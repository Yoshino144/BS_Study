package top.pcat.study.WrongQuestion;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import top.pcat.study.R;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WrongOne extends AppCompatActivity {
    private String timuJson;
    private String tid;
    private String type = "error";
    private String answer;
    private String subject;
    private String A;
    private String B;
    private String C;
    private String D;
    private String analysis;
    private int allSize = 0;

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
    private LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrong_one);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        Intent intent = getIntent();
        String kemu = intent.getStringExtra("kemu_name");
        String ti = intent.getStringExtra("ti");
        Log.d("科目", kemu+ti);
        if(ti==null || ti.equals("null")) ti="1";
        String titi = String.valueOf(ti.charAt(8));
        int tiHao = Integer.parseInt(titi);
        assert kemu != null;
        if (kemu.contains("Cpp")) {
            String tt = "Cpp" + kemu.charAt(3);
            timuJson = read(tt+".json");
        }
        readInfo(timuJson, tiHao);


        title = findViewById(R.id.textmm);
        text_A = findViewById(R.id.text_A);
        text_B = findViewById(R.id.text_B);
        text_C = findViewById(R.id.text_C);
        text_D = findViewById(R.id.text_D);
        aaa = findViewById(R.id.AAA);
        bbb = findViewById(R.id.BBB);
        ccc = findViewById(R.id.CCC);
        ddd = findViewById(R.id.DDD);
        image_A = findViewById(R.id.image_A);
        image_B = findViewById(R.id.image_B);
        image_C = findViewById(R.id.image_C);
        image_D = findViewById(R.id.image_D);
        wrongaa = findViewById(R.id.wrongaa);
        trueaa = findViewById(R.id.trueaa);
        jiexi = findViewById(R.id.jiexi);

        title.setText(subject);
        text_A.setText(A);
        text_B.setText(B);
        text_C.setText(C);
        text_D.setText(D);
        jiexi.setText(analysis);


        aaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.matches("A")) {
                    trueaa.setVisibility(View.VISIBLE);
                    image_A.setImageResource(R.drawable.ttrue);
                    text_A.setTextColor(Color.parseColor("#1cabfa"));
                } else {
                    wrongaa.setVisibility(View.VISIBLE);
                    image_A.setImageResource(R.drawable.wrong);
                    text_A.setTextColor(Color.parseColor("#fd6767"));
                    if (answer.matches("A")) {
                        image_A.setImageResource(R.drawable.ttrue);
                        text_A.setTextColor(Color.parseColor("#1cabfa"));
                    } else if (answer.matches("B")) {
                        image_B.setImageResource(R.drawable.ttrue);
                        text_B.setTextColor(Color.parseColor("#1cabfa"));
                    } else if (answer.matches("C")) {
                        image_C.setImageResource(R.drawable.ttrue);
                        text_C.setTextColor(Color.parseColor("#1cabfa"));
                    } else if (answer.matches("D")) {
                        image_D.setImageResource(R.drawable.ttrue);
                        text_D.setTextColor(Color.parseColor("#1cabfa"));
                    }
                }

            }
        });

        bbb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (answer.matches("B")) {
                    trueaa.setVisibility(View.VISIBLE);
                    image_B.setImageResource(R.drawable.ttrue);
                    text_B.setTextColor(Color.parseColor("#1cabfa"));
                } else {
                    wrongaa.setVisibility(View.VISIBLE);
                    image_B.setImageResource(R.drawable.wrong);
                    text_B.setTextColor(Color.parseColor("#fd6767"));
                    if (answer.matches("A")) {
                        image_A.setImageResource(R.drawable.ttrue);
                        text_A.setTextColor(Color.parseColor("#1cabfa"));
                    } else if (answer.matches("B")) {
                        image_B.setImageResource(R.drawable.ttrue);
                        text_B.setTextColor(Color.parseColor("#1cabfa"));
                    } else if (answer.matches("C")) {
                        image_C.setImageResource(R.drawable.ttrue);
                        text_C.setTextColor(Color.parseColor("#1cabfa"));
                    } else if (answer.matches("D")) {
                        image_D.setImageResource(R.drawable.ttrue);
                        text_D.setTextColor(Color.parseColor("#1cabfa"));
                    }

                }
            }
        });

        ccc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer.matches("C")) {
                    trueaa.setVisibility(View.VISIBLE);
                    image_C.setImageResource(R.drawable.ttrue);
                    text_C.setTextColor(Color.parseColor("#1cabfa"));
                } else {
                    wrongaa.setVisibility(View.VISIBLE);
                    image_C.setImageResource(R.drawable.wrong);
                    text_C.setTextColor(Color.parseColor("#fd6767"));
                    if (answer.matches("A")) {
                        image_A.setImageResource(R.drawable.ttrue);
                        text_A.setTextColor(Color.parseColor("#1cabfa"));
                    } else if (answer.matches("B")) {
                        image_B.setImageResource(R.drawable.ttrue);
                        text_B.setTextColor(Color.parseColor("#1cabfa"));
                    } else if (answer.matches("C")) {
                        image_C.setImageResource(R.drawable.ttrue);
                        text_C.setTextColor(Color.parseColor("#1cabfa"));
                    } else if (answer.matches("D")) {
                        image_D.setImageResource(R.drawable.ttrue);
                          text_D.setTextColor(Color.parseColor("#1cabfa"));
                    }
                }

            }
        });

        ddd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (answer.matches("D")) {
                    trueaa.setVisibility(View.VISIBLE);
                    image_D.setImageResource(R.drawable.ttrue);
                    text_D.setTextColor(Color.parseColor("#1cabfa"));
                } else {
                    wrongaa.setVisibility(View.VISIBLE);
                    image_D.setImageResource(R.drawable.wrong);
                    text_D.setTextColor(Color.parseColor("#fd6767"));
                    if (answer.matches("A")) {
                        image_A.setImageResource(R.drawable.ttrue);
                        text_A.setTextColor(Color.parseColor("#1cabfa"));
                    } else if (answer.matches("B")) {
                        image_B.setImageResource(R.drawable.ttrue);
                        text_B.setTextColor(Color.parseColor("#1cabfa"));
                    } else if (answer.matches("C")) {
                        image_C.setImageResource(R.drawable.ttrue);
                        text_C.setTextColor(Color.parseColor("#1cabfa"));
                    } else if (answer.matches("D")) {
                        image_D.setImageResource(R.drawable.ttrue);
                        text_D.setTextColor(Color.parseColor("#1cabfa"));
                    }
                }

            }
        });

    }

    public void readInfo(String tempInfo, int tiHao) {
        try {
            JSONArray jsonArray = new JSONArray(tempInfo);
            JSONObject jsonObject = jsonArray.getJSONObject(tiHao - 1);
            tid = jsonObject.getString("problem_id");
            type = jsonObject.getString("problem_type");
            //answer  = jsonObject.getString("Answer");
            subject = jsonObject.getString("problem_content");
            A = jsonObject.getString("A");
            B = jsonObject.getString("B");
            C = jsonObject.getString("C");
            D = jsonObject.getString("D");
            analysis = jsonObject.getString("problem_analysis");
            answer = jsonObject.getString("problem_answer");

            Log.d("daan========",answer);


        } catch (JSONException e) {
            Toast.makeText(this, "信息读取错误", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public String read(String file) {
        String result = "";
        try {
            FileInputStream fin = openFileInput(file);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            result = EncodingUtils.getString(buffer, "UTF-8");
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

}
