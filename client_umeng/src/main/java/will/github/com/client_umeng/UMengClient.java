package will.github.com.client_umeng;

import android.content.Context;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengCallback;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.entity.UMessage;
import com.umeng.message.tag.TagManager;

import will.github.com.client_master.Logger;
import will.github.com.client_master.ReMessageListener;
import will.github.com.client_master.RePushClient;
import will.github.com.client_master.RePushMessage;

/**
 * Created by will on 2017/8/7.
 */

public class UMengClient implements RePushClient {

    public static final String NAME = "umeng";
    public static ReMessageListener sReMessageListener;
    private PushAgent mPushAgent;

    @Override
    public void registerPush(final Context con) {
        Context context = con.getApplicationContext();
        MobclickAgent.setScenarioType(context, MobclickAgent.EScenarioType.E_UM_NORMAL);
        mPushAgent = PushAgent.getInstance(context.getApplicationContext());

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                String custom = msg.custom;
                //收到消息
                RePushMessage rePushMessage = new RePushMessage();
                rePushMessage.setContent(custom);
                UMengClient.sReMessageListener.onNotificationMessageClicked(context, rePushMessage);
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        mPushAgent.setDisplayNotificationNumber(5); //最多展示5条

        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                RePushMessage message = new RePushMessage();
                message.setToken(deviceToken);
                UMengClient.sReMessageListener.onToken(con.getApplicationContext(), message);
            }

            @Override
            public void onFailure(String s, String s1) {
                Logger.e("友盟注册devicetoken失败 " + s + " " + s1);
//                UMPushManager.sMixMessageProvider.onToken(context, message);
            }
        });
    }

    @Override
    public void unRegisterPush(Context context) {
        if (mPushAgent == null) {
            return;
        }
        mPushAgent.disable(new IUmengCallback() {
            @Override
            public void onSuccess() {
                Logger.e("友盟不允许推送success");
            }

            @Override
            public void onFailure(String s, String s1) {
                Logger.e("友盟不允许使用推送failure");
            }
        });
    }

    @Override
    public void setAlias(Context context, String alias) {
        mPushAgent.addAlias(alias, "alias", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                Logger.e("友盟设置别名(alias)" + isSuccess);
            }
        });
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        mPushAgent.removeAlias(alias, "alias", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                Logger.e("友盟取消设置别名(alias)" + isSuccess);
            }
        });
    }

    @Override
    public void setTags(Context context, String... tags) {
        mPushAgent.getTagManager().add(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                //isSuccess表示操作是否成功
                Logger.e("友盟设置标签(tag)" + isSuccess);
            }
        }, tags);
    }

    @Override
    public void unsetTags(Context context, String... tags) {
        mPushAgent.getTagManager().delete(new TagManager.TCallBack() {
            @Override
            public void onMessage(final boolean isSuccess, final ITagManager.Result result) {
                Logger.e("友盟取消设置标签(tag)" + isSuccess);
            }
        }, tags);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void setMessageListener(ReMessageListener listener) {
        this.sReMessageListener = listener;
    }


    @Override
    public void disable(Context context) {
        PushAgent.getInstance(context).disable(new IUmengCallback() {
            @Override
            public void onSuccess() {
                Logger.e("友盟设置不可用成功");
            }

            @Override
            public void onFailure(String s, String s1) {
                Logger.e("友盟设置不可用失败");
            }
        });
    }

    @Override
    public void enable(Context context) {
        PushAgent.getInstance(context).enable(new IUmengCallback() {
            @Override
            public void onSuccess() {
                Logger.e("友盟设置可用成功");
            }

            @Override
            public void onFailure(String s, String s1) {
                Logger.e("友盟设置可用失败");
            }
        });
    }
}
