package will.github.com.client_master;

import android.content.Context;

public interface RePushClient {

    void registerPush(Context context);

    void unRegisterPush(Context context);

    void setAlias(Context context, String alias);

    void unsetAlias(Context context, String alias);

    void setTags(Context context, String... tags);

    void unsetTags(Context context, String... tags);

    String getName();

    void setMessageListener(ReMessageListener provider);

    /**
     * 如果从小米推送->小米&个推，所以上线新版可能会导致收到2个平台的推送，所以没有用得平台必须让其失效（取消注册）。
     */
    void disable(Context context);

    void enable(Context context);

}
