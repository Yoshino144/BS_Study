package top.pcat.study.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.apkfuns.logutils.LogUtils;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import top.pcat.study.Dialog.beans.Goods;
import top.pcat.study.Dialog.beans.GoodsType;
import top.pcat.study.Dialog.beans.IGoodsBase;
import top.pcat.study.R;
import top.pcat.study.Exercises.ExercisesActivity;

import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * 继承自BottomSheetDialogFragment，
 * 由于是使用Dialog实现，所以不可以添加其他的fragment
 *
 * @author admin on 2018/9/11.
 */
public class GoodsDialogFragment extends BottomSheetDialogFragment implements addClickListener,OnGoodsBaseClickListener, View.OnClickListener {
    protected RecyclerView vList;
    private TextView vTitle;
    private ICallBack iCallBack;
    private String pagesize="res";
    private View vBack;
    private List<Fruit> fruitList = new ArrayList<>();
    private Handler handler;
    private int timu_size = 0;
    private String timu_name = "";

    protected final List<IGoodsBase> goodsBaseList = new ArrayList<>();


    private boolean isGoodsTypeShowing;

    private MyDialog dialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog = new MyDialog(getContext(), getTheme());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ExercisesActivity activity=(ExercisesActivity) getActivity();
        handler=activity.handler2;
        Bundle bundle = getArguments();
        if (bundle != null) {
            timu_size = bundle.getInt("size");
            timu_name = bundle.getString("name");
        }
        return inflater.inflate(R.layout.fragment_goods_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        vBack = view.findViewById(R.id.back);
        vTitle = view.findViewById(R.id.title);

        view.findViewById(R.id.close).setOnClickListener(this);
        vBack.setOnClickListener(this);
        vBack.setVisibility(View.INVISIBLE);
        vTitle.setText("题目");

        initFruits();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager layoutManager = new
                StaggeredGridLayoutManager(6, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList,this);
        recyclerView.setAdapter(adapter);
    }

    public void sendMessage(ICallBack callBack){
        callBack.get_message_from_Fragment(pagesize);
    }

    private void initFruits() {
        String json = read(timu_name+"Flag.json");
        LogUtils.d("列表加载json读取结果",json);
        for (int i = 1; i <= timu_size; i++) {
            String flag = "no";
            flag = readSj(json,i);
            LogUtils.d("列表加载第"+i+"是",flag);
            Fruit apple = new Fruit(
                    getRandomLengthName(String.valueOf(i)), R.drawable.abc,flag);
            fruitList.add(apple);
        }
    }

    private String readSj(String tempInfo,int postion){
        try {
            JSONArray jsonArray = new JSONArray(tempInfo);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String lv = jsonObject.getString(String.valueOf(postion));
                return lv;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "error";
    }

    private String read(String file) {
        String result = "";
        try {
            FileInputStream fin = getActivity().openFileInput(file);
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

    private String getRandomLengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1; i++) {
            builder.append(name);
        }
        return builder.toString();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //去掉父布局的背景
        View view = getView();
        if (view != null) {
            View parent = (View) view.getParent();
            if (parent != null) {
                parent.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                backToGoodsType();
                break;
            case R.id.close:
                dialog.cancel();
                break;
            default:
                break;
        }
    }

    protected void backToGoodsType() {
    }

    @Override
    public void onGoodsBaseClick(IGoodsBase goodsBase) {
        if (goodsBase instanceof GoodsType) {
            //点击商品种类，跳转到该种类商品
            List<Goods> list = ((GoodsType) goodsBase).getGoodsList();
            goodsBaseList.clear();
            goodsBaseList.addAll(list);

            vBack.setVisibility(View.VISIBLE);
            vTitle.setText(goodsBase.getName());
            isGoodsTypeShowing = false;
        } else if (goodsBase instanceof Goods) {
            //点击商品，弹出提示
            makeText(getActivity(), goodsBase.getName(), LENGTH_SHORT).show();
        }
    }

    @Override
    public void addClick(int position) {
       // Toast.makeText(getActivity(), "Fragment"+String.valueOf(position), LENGTH_SHORT).show();
        //iCallBack.get_message_from_Fragment(String.valueOf(position));
        //pagesize=String.valueOf(position);
        new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message= new Message();
                    message.what = position;
                    handler.sendMessage(message);
                    dialog.cancel();
                }
            }).start();
       // getActivity().setCurrentView(position);
    }

    private class MyDialog extends BottomSheetDialog {
        MyDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        @Override
        public void onBackPressed() {
            if (isGoodsTypeShowing) {
                cancel();
            } else {
                backToGoodsType();
            }
        }

        @Override
        public void cancel() {
            BottomSheetBehavior<?> behavior = getBehavior();
            if (behavior == null || behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                super.cancel();
            } else {
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }


        @Override
        protected void onStart() {
            setup();
            super.onStart();
            final BottomSheetBehavior<?> behavior = getBehavior();
            if (behavior != null) {
                /*
                window有一个黑色背景的渐变动画，
                延迟300ms，等动画结束在执行菜单弹出动画。
                否则看起来很突兀
                 */
                getView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }, 300);
            }
        }

        private void setup() {
            BottomSheetBehavior<?> behavior = getBehavior();
            if (behavior == null) {
                return;
            }
            behavior.setSkipCollapsed(true);
            behavior.setPeekHeight(0);
        }

        public BottomSheetBehavior<FrameLayout> getBehavior() {
            try {
                Field field = getClass().getSuperclass().getDeclaredField("mBehavior");
                if (field == null) {
                    return null;
                }
                field.setAccessible(true);
                return (BottomSheetBehavior<FrameLayout>) field.get(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
