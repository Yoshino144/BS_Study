package top.pcat.study.Share.tencent.qq;

import com.mob.MobSDK;
import top.pcat.study.Share.DemoUtils;
import top.pcat.study.Share.ResourcesManager;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;


/**
 * Created by yjin on 2017/6/22.
 */

public class QQShare {
    private PlatformActionListener platformActionListener;

    public final static String LINK_URL = "http://m.93lj.com/sharelink/";
    //public final static String LINK_URL = "http://m.93lj.com/sharelink?mobid=ziqMNf";
    public final static String LINK_TEXT = "loopShare 重磅上线！一键实现分享闭环！错过它，就错过了全世界~";


    public QQShare(PlatformActionListener mListener) {
        this.platformActionListener = mListener;
        String[] pks = {"com.tencent.mobileqq", "com.tencent.mobileqqi", "com.tencent.qqlite", "com.tencent.minihd.qq", "com.tencent.tim"};
        DemoUtils.isValidClient(pks);
    }

    public void shareWebPager() {
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setText(LINK_TEXT);
        shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
        shareParams.setTitleUrl(LINK_URL);
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(shareParams);
    }

    public void shareImage() {
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
        shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
        //shareParams.setImageUrl("http://pic28.photophoto.cn/20130818/0020033143720852_b.jpg");
        platform.setPlatformActionListener(platformActionListener);
        shareParams.setShareType(Platform.SHARE_IMAGE);
        platform.share(shareParams);
    }

    public void shareMusic() {
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
        shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
        shareParams.setTitleUrl(ResourcesManager.getInstace(MobSDK.getContext()).getTitleUrl());
        shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
        shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
        shareParams.setMusicUrl(ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl());
        shareParams.setShareType(Platform.SHARE_MUSIC);
        platform.setPlatformActionListener(platformActionListener);
        platform.share(shareParams);
    }

    public void shareWebPager(PlatformActionListener mListener) {
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
        shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
        shareParams.setTitleUrl(ResourcesManager.getInstace(MobSDK.getContext()).getTitleUrl());
        shareParams.setShareType(Platform.SHARE_WEBPAGE);
        platform.setPlatformActionListener(mListener);
        platform.share(shareParams);
    }

    public void shareImage(PlatformActionListener mListener) {
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
        shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
        shareParams.setShareType(Platform.SHARE_IMAGE);
        platform.setPlatformActionListener(mListener);
        platform.share(shareParams);
    }

    public void shareMusic(PlatformActionListener mListener) {
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
        shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
        shareParams.setTitleUrl(ResourcesManager.getInstace(MobSDK.getContext()).getTitleUrl());
        shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
        shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
        shareParams.setMusicUrl(ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl());
        shareParams.setShareType(Platform.SHARE_MUSIC);
        platform.setPlatformActionListener(mListener);
        platform.share(shareParams);
    }

    public void shareQQMiniProgram() {
        Platform platform = ShareSDK.getPlatform(QQ.NAME);
        Platform.ShareParams shareParams = new Platform.ShareParams();
        shareParams.setText("QQ小程序");
        shareParams.setTitle("QQ互联");
        shareParams.setTitleUrl("http://www.qq.com/");
        shareParams.setImageUrl("http://www.3wyu.com/wp-content/uploads/6e0eaf15gy1fvr5tnm2dfj20f108gtad.jpg");
        shareParams.setShareType(Platform.QQ_MINI_PROGRAM);
        shareParams.setQQMiniProgramAppid("1108318575"); //官方的
        shareParams.setQQMiniProgramPath("page/share/index.html?share_name=QQ%E9%9F%B3%E4%B9%90&share_key=5aIqFGg&from=disk");
        shareParams.setQQMiniProgramType("");
        platform.setPlatformActionListener(platformActionListener);
        platform.share(shareParams);
    }


}
