package will.github.com.client_xiaomi;

import android.content.Context;

import java.util.List;

import will.github.com.client_master.ReConstants;
import will.github.com.client_master.ReMessageListener;
import will.github.com.client_master.RePushClient;

/**
 * Created by Wiki on 2017/6/1.
 */

public class MiPushClient implements RePushClient {
    public static ReMessageListener sMixMessageProvider;
    private String appId;
    private String appKey;

    public MiPushClient(String appId, String appKey) {
        this.appId = appId;
        this.appKey = appKey;
    }

    @Override
    public void registerPush(Context context) {
        com.xiaomi.mipush.sdk.MiPushClient.registerPush(context.getApplicationContext(), appId, appKey);
    }

    @Override
    public void unRegisterPush(Context context) {
        unsetAlias(context, null);
        com.xiaomi.mipush.sdk.MiPushClient.unregisterPush(context.getApplicationContext());
    }

    @Override
    public void setAlias(Context context, String alias) {
        if (!com.xiaomi.mipush.sdk.MiPushClient.getAllAlias(context).contains(alias)) {
            com.xiaomi.mipush.sdk.MiPushClient.setAlias(context, alias, null);
        }
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        List<String> allAlias = com.xiaomi.mipush.sdk.MiPushClient.getAllAlias(context);
        for (int i = 0; i < allAlias.size(); i++) {
            com.xiaomi.mipush.sdk.MiPushClient.unsetAlias(context, allAlias.get(i), null);
        }
    }

    @Override
    public void setTags(Context context, String... tags) {
        for (String tag : tags) {
            com.xiaomi.mipush.sdk.MiPushClient.subscribe(context, tag, null);
        }

    }

    @Override
    public void unsetTags(Context context, String... tags) {
        for (String tag : tags) {
            com.xiaomi.mipush.sdk.MiPushClient.unsubscribe(context, tag, null);
        }
    }


    @Override
    public String getName() {
        return ReConstants.PUSHNAME_XIAOMI;
    }

    @Override
    public void setMessageListener(ReMessageListener provider) {
        MiPushClient.sMixMessageProvider = provider;
    }


    @Override
    public void disable(Context context) {
        unRegisterPush(context);
    }

    @Override
    public void enable(Context context) {
        registerPush(context);
    }
}
