package top.pcat.study.Banner;

import top.pcat.study.R;

import java.util.ArrayList;
import java.util.List;

public class DataBean {
    public Integer imageRes;
    public Integer imageUrl;
    public String title;
    public int viewType;

    public DataBean(Integer imageUrl,Integer imageRes, String title, int viewType) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
        this.imageUrl=imageUrl;
    }

    public DataBean(Integer imageUrl, String title, int viewType) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.viewType = viewType;
    }

    public static List<DataBean> getTestData() {
        List<DataBean> list = new ArrayList<>();
        //list.add(new DataBean(R.drawable.image11,R.drawable.image1, "相信自己,你努力的样子真的很美", 2));
        list.add(new DataBean(R.drawable.image22,R.drawable.image2, "极致简约,梦幻小屋", 3));
        list.add(new DataBean(R.drawable.image33,R.drawable.image3, "夏季新搭配", 2));
        return list;
    }
}
