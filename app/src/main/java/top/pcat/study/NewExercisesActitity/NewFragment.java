package top.pcat.study.NewExercisesActitity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import top.pcat.study.R;

import org.jetbrains.annotations.NotNull;

public class NewFragment extends Fragment{
    NewExercisesActitity newExercisesActitity;
    TextView textView;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //在这里实现ui更新的效果
            textView.setText("cnmb");
        }
    };

    public static NewFragment newInstance(int s, String s1) {
        NewFragment fragment = new NewFragment();
        Bundle args = new Bundle();
        args.putString("ARG_SHOW_TEXT", String.valueOf(s));
        args.putString("LV_SHOW_TEXT", s1);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.new_fragment, container, false);
        textView= rootView.findViewById(R.id.text);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        newExercisesActitity = (NewExercisesActitity) context;
        newExercisesActitity.setHandler(handler);
    }
}