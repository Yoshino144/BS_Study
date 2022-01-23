package top.pcat.study.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import top.pcat.study.Utils.FileTool;
import top.pcat.study.PushService.FragAdapter;
import top.pcat.study.PushService.Fragment2;
import top.pcat.study.PushService.Fragment3;
import top.pcat.study.R;
import top.pcat.study.Ranking.ClassAdapter;
import top.pcat.study.Ranking.ClassList;
import top.pcat.study.Ranking.GuanList;
import top.pcat.study.Ranking.MesList;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment6 extends Fragment  {


    public static BlankFragment6 newInstance() {
        return new BlankFragment6();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.blank_fragment6_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}
