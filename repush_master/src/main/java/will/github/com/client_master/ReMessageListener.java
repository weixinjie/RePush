package will.github.com.client_master;

import android.content.Context;

/**
 * Created by Wiki on 2017/6/1.
 */

public interface ReMessageListener {
    /**
     * 收到透传消息
     */
    public void onReceivePassThroughMessage(Context context, RePushMessage message);

    /**
     * 通知栏消息点击
     */
    public void onNotificationMessageClicked(Context context, RePushMessage message);

    /**
     * 通知栏消息到达
     */
    public void onNotificationMessageArrived(Context context, RePushMessage message);

//    public void onError(Context context, MixPushMessage message);

    /**
     * 接收到客户端唯一标识(token)
     *
     * @param context
     * @param token
     */
    void onToken(Context context, RePushMessage token);
}
