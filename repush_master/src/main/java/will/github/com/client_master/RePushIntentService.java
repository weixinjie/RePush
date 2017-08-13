package will.github.com.client_master;

import android.app.IntentService;
import android.content.Intent;


/**
 * 透传服务类
 */
public abstract class RePushIntentService extends IntentService {

    public static final String TAG = "RePushIntentService";

    public RePushIntentService() {
        super("RePushIntentService");
    }

    @Override
    public final void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            try {
                RePushMessage message = (RePushMessage) intent.getSerializableExtra(ReConstants.KEY_MESSAGE);
                if (ReConstants.KEY_RECEIVE_THROUGH_MESSAGE.equals(action)) {
                    onReceivePassThroughMessage(message);
                } else if (ReConstants.KEY_NOTIFICATION_ARRIVED.equals(action)) {
                    onNotificationMessageArrived(message);
                } else if (ReConstants.KEY_NOTIFICATION_CLICKED.equals(action)) {
                    onNotificationMessageClicked(message);
                } else if (ReConstants.KEY_ON_TOKEN.equals(action)) {
                    onToken(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 透传
     */
    public abstract void onReceivePassThroughMessage(RePushMessage message);

    /**
     * 通知栏消息点击
     */
    public abstract void onNotificationMessageClicked(RePushMessage message);

    /**
     * 通知栏消息点击
     */
    public abstract void onToken(RePushMessage token);

    /**
     * 通知栏消息到达,
     * flyme6基于android6.0以上不再回调，
     * MIUI基于小米推送，在APP被杀死不会回调，
     * 在个推不会回调，所以不建议使用，
     */
    @Deprecated
    public void onNotificationMessageArrived(RePushMessage message) {

    }


}