package will.github.com.client_xiaomi;

import android.content.Context;

import com.xiaomi.mipush.sdk.ErrorCode;
import com.xiaomi.mipush.sdk.MiPushCommandMessage;
import com.xiaomi.mipush.sdk.MiPushMessage;
import com.xiaomi.mipush.sdk.PushMessageReceiver;

import java.util.List;

import will.github.com.client_master.ReConstants;
import will.github.com.client_master.RePushMessage;

public class MiPushMessageReceiver extends PushMessageReceiver {
    private static final String TAG = "MiPushMessageReceiver";
    private String mRegId;
    private String mAlias;
    private String mTopic;
    private String mStartTime;
    private String mEndTime;

    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        if (MiPushClient.sMixMessageProvider == null) {
            return;
        }
        String content = message.getContent();
        RePushMessage mixPushMessage = new RePushMessage();
        mixPushMessage.setPlatform(ReConstants.PUSHNAME_XIAOMI);
        mixPushMessage.setContent(content);
        mixPushMessage.setTitle(message.getTitle());
        mixPushMessage.setDescription(message.getDescription());
        mixPushMessage.setAlias(message.getAlias());
        mixPushMessage.setPassThrough(message.getPassThrough());
        MiPushClient.sMixMessageProvider.onReceivePassThroughMessage(context, mixPushMessage);
    }

    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        if (MiPushClient.sMixMessageProvider == null) {
            return;
        }
        String content = message.getContent();
        RePushMessage mixPushMessage = new RePushMessage();
        mixPushMessage.setPlatform(ReConstants.PUSHNAME_XIAOMI);
        mixPushMessage.setContent(content);
        mixPushMessage.setTitle(message.getTitle());
        mixPushMessage.setDescription(message.getDescription());
        mixPushMessage.setAlias(message.getAlias());
        mixPushMessage.setPassThrough(message.getPassThrough());
        mixPushMessage.setExtra(message.getExtra());
        MiPushClient.sMixMessageProvider.onNotificationMessageClicked(context, mixPushMessage);
    }


    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        if (MiPushClient.sMixMessageProvider == null) {
            return;
        }
        String content = message.getContent();
        RePushMessage mixPushMessage = new RePushMessage();
        mixPushMessage.setPlatform(ReConstants.PUSHNAME_XIAOMI);
        mixPushMessage.setContent(content);
        mixPushMessage.setTitle(message.getTitle());
        mixPushMessage.setDescription(message.getDescription());
        mixPushMessage.setAlias(message.getAlias());
        mixPushMessage.setPassThrough(message.getPassThrough());
        mixPushMessage.setExtra(message.getExtra());
        MiPushClient.sMixMessageProvider.onNotificationMessageArrived(context, mixPushMessage);
    }


    @Override
    public void onCommandResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String cmdArg2 = ((arguments != null && arguments.size() > 1) ? arguments.get(1) : null);
        if (com.xiaomi.mipush.sdk.MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
            }
        } else if (com.xiaomi.mipush.sdk.MiPushClient.COMMAND_SET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (com.xiaomi.mipush.sdk.MiPushClient.COMMAND_UNSET_ALIAS.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mAlias = cmdArg1;
            }
        } else if (com.xiaomi.mipush.sdk.MiPushClient.COMMAND_SUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (com.xiaomi.mipush.sdk.MiPushClient.COMMAND_UNSUBSCRIBE_TOPIC.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mTopic = cmdArg1;
            }
        } else if (com.xiaomi.mipush.sdk.MiPushClient.COMMAND_SET_ACCEPT_TIME.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mStartTime = cmdArg1;
                mEndTime = cmdArg2;
            }
        }
    }

    @Override
    public void onReceiveMessage(Context context, MiPushMessage miPushMessage) {
        super.onReceiveMessage(context, miPushMessage);
    }

    @Override
    public void onReceiveRegisterResult(Context context, MiPushCommandMessage message) {
        String command = message.getCommand();
        List<String> arguments = message.getCommandArguments();
        String cmdArg1 = ((arguments != null && arguments.size() > 0) ? arguments.get(0) : null);
        String log;
        if (com.xiaomi.mipush.sdk.MiPushClient.COMMAND_REGISTER.equals(command)) {
            if (message.getResultCode() == ErrorCode.SUCCESS) {
                mRegId = cmdArg1;
                RePushMessage mixPushMessage = new RePushMessage();
                mixPushMessage.setPlatform(ReConstants.PUSHNAME_XIAOMI);
                mixPushMessage.setToken(cmdArg1);
                MiPushClient.sMixMessageProvider.onToken(context, mixPushMessage);
            } else {
            }
        } else {
            log = message.getReason();
        }
    }
}