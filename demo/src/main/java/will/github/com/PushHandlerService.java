package will.github.com;

import android.util.Log;
import android.widget.Toast;

import will.github.com.client_master.Logger;
import will.github.com.client_master.RePushIntentService;
import will.github.com.client_master.RePushMaster;
import will.github.com.client_master.RePushMessage;

/**
 * 需要定义一个receiver 或 Service 来接收透传和通知栏点击的信息，建议使用Service，更加简单
 * Created by Wiki on 2017/6/3.
 */

public class PushHandlerService extends RePushIntentService {
    @Override
    public void onReceivePassThroughMessage(RePushMessage message) {
        Toast.makeText(getApplicationContext(), "客户端收到了透传消息" + message.getContent(), Toast.LENGTH_SHORT).show();
        Logger.e("收到透传消息 -> " + message.getPlatform());
        Logger.e("收到透传消息 -> " + message.getContent());
    }

    @Override
    public void onNotificationMessageClicked(RePushMessage message) {
        Logger.e("通知栏消息点击 -> " + message.getPlatform());
        Logger.e("通知栏消息点击 -> " + message.toString());
    }

    @Override
    public void onToken(RePushMessage token) {
        SharePrefUtil.saveString("token", token.getToken());
        Log.e(TAG, "接收到了token -> " + token.getToken() + "   " + RePushMaster.getCurrentPlatForm());
    }

}
