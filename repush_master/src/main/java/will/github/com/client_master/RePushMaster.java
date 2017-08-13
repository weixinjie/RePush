package will.github.com.client_master;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by will on 2017/8/12.
 */

public class RePushMaster {

    private static Map<String, RePushClient> sPushManagerMap = new HashMap<>();
    private static String sUsePushName;
    private static ReSelector sSelector;
    private static Class<? extends RePushIntentService> sRePushIntentServiceClass;


    private RePushMaster() {

    }

    public static void setPushIntentService(Class<? extends RePushIntentService> sRePushIntentServiceClass) {
        RePushMaster.sRePushIntentServiceClass = sRePushIntentServiceClass;
    }

    public static void setSelector(ReSelector selector) {
        sSelector = selector;
        sUsePushName = sSelector.select(sPushManagerMap, Build.BRAND);
    }

    public static String getCurrentPlatForm() {
        return sUsePushName;
    }

    public static void addPushClient(RePushClient pushAdapter) {
        sPushManagerMap.put(pushAdapter.getName(), pushAdapter);
        pushAdapter.setMessageListener(mMixMessageListener);
    }

    public static void registerPush(Context context) {
        Set<String> keys = sPushManagerMap.keySet();
        for (String key : keys) {
            if (key.equals(sUsePushName)) {
                sPushManagerMap.get(key).registerPush(context);
            } else {
                sPushManagerMap.get(key).unRegisterPush(context);
            }
        }
    }

    private static RePushClient getPushClient() {
        if (sUsePushName == null) {
            throw new RuntimeException("you need setSelector or setUsePushName");
        }
        return sPushManagerMap.get(sUsePushName);
    }

    public static void unRegisterPush(Context context) {
        getPushClient().unRegisterPush(context);
    }

    public static void setUsePushName(String sUsePushName) {
        RePushMaster.sUsePushName = sUsePushName;
    }

    public static void setAlias(Context context, String alias) {
        getPushClient().setAlias(context, alias);
    }

    public static void unsetAlias(Context context, String alias) {
        getPushClient().unsetAlias(context, alias);
    }

    public static void setTags(Context context, String... tags) {
        getPushClient().setTags(context, tags);
    }

    public static void unsetTags(Context context, String... tags) {
        getPushClient().unsetTags(context, tags);
    }

    public static void enable(Context context) {
        getPushClient().enable(context);
    }

    public static void disable(Context context) {
        getPushClient().disable(context);
    }

    public static class ReSelector {
        public String select(Map<String, RePushClient> pushAdapterMap, String brand) {
            if (pushAdapterMap.containsKey(ReConstants.PUSHNAME_MEIZU) && brand.equalsIgnoreCase(ReConstants.BRANDNAME_MEIZU)) {
                return ReConstants.PUSHNAME_MEIZU;
            } else if (pushAdapterMap.containsKey(ReConstants.PUSHNAME_XIAOMI) && brand.equalsIgnoreCase(ReConstants.BRANDNAME_XIAOMI)) {
                return ReConstants.PUSHNAME_XIAOMI;
            } else if (pushAdapterMap.containsKey(ReConstants.PUSHNAME_HUAWEI) && brand.equalsIgnoreCase(ReConstants.BRANDNAME_HUAWIE)) {
                //使用华为推送
                return ReConstants.PUSHNAME_HUAWEI;
            } else if (pushAdapterMap.containsKey(ReConstants.PUSHNAME_UMNEG)) {
                return ReConstants.PUSHNAME_UMNEG;
            }
            return ReConstants.PUSHNAME_UMNEG;
        }
    }


    private static ReMessageListener mMixMessageListener = new ReMessageListener() {
        @Override
        public void onReceivePassThroughMessage(Context context, RePushMessage message) {
            Intent intent = new Intent(ReConstants.KEY_RECEIVE_THROUGH_MESSAGE);
            intent.putExtra(ReConstants.KEY_MESSAGE, message);

            if (sRePushIntentServiceClass != null) {
                intent.setClass(context, sRePushIntentServiceClass);
                context.startService(intent);
            }
        }

        @Override
        public void onNotificationMessageClicked(Context context, RePushMessage message) {
            Intent intent = new Intent(ReConstants.KEY_NOTIFICATION_CLICKED);
            intent.putExtra(ReConstants.KEY_MESSAGE, message);
            if (sRePushIntentServiceClass != null) {
                intent.setClass(context, sRePushIntentServiceClass);
                context.startService(intent);
            }
        }

        @Override
        public void onNotificationMessageArrived(Context context, RePushMessage message) {
            Intent intent = new Intent(ReConstants.KEY_NOTIFICATION_ARRIVED);
            intent.putExtra(ReConstants.KEY_MESSAGE, message);

            if (sRePushIntentServiceClass != null) {
                intent.setClass(context, sRePushIntentServiceClass);
                context.startService(intent);
            }
        }

        @Override
        public void onToken(Context context, RePushMessage token) {
            Intent intent = new Intent(ReConstants.KEY_ON_TOKEN);
            intent.putExtra(ReConstants.KEY_MESSAGE, token);
            if (sRePushIntentServiceClass != null) {
                intent.setClass(context, sRePushIntentServiceClass);
                context.startService(intent);
            }
        }

    };
}
