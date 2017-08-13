package will.github.com.client_huawei;

import android.content.Context;
import android.util.Log;

import com.huawei.hms.api.ConnectionResult;
import com.huawei.hms.api.HuaweiApiClient;
import com.huawei.hms.support.api.client.PendingResult;
import com.huawei.hms.support.api.client.ResultCallback;
import com.huawei.hms.support.api.push.HuaweiPush;
import com.huawei.hms.support.api.push.TokenResult;

import will.github.com.client_master.Logger;
import will.github.com.client_master.ReConstants;
import will.github.com.client_master.ReMessageListener;
import will.github.com.client_master.RePushClient;

/**
 * Created by will on 2017/8/7.
 */

public class HuaWeiClient implements RePushClient, HuaweiApiClient.ConnectionCallbacks, HuaweiApiClient.OnConnectionFailedListener {

    //华为移动服务Client
    private HuaweiApiClient client;
    //调用HuaweiApiAvailability.getInstance().resolveError传入的第三个参数
    //作用同startactivityforresult方法中的requestcode
    private static final int REQUEST_HMS_RESOLVE_ERROR = 1000;

    public static ReMessageListener sReMessageListener;

    public HuaWeiClient() {

    }

    @Override
    public void registerPush(Context context) {
        //创建华为移动服务client实例用以使用华为push服务
        //需要指定api为HuaweiId.PUSH_API
        //连接回调以及连接失败监听
        client = new HuaweiApiClient.Builder(context)
                .addApi(HuaweiPush.PUSH_API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        //建议在oncreate的时候连接华为移动服务
        //业务可以根据自己业务的形态来确定client的连接和断开的时机，但是确保connect和disconnect必须成对出现
        client.connect();

    }

    @Override
    public void unRegisterPush(Context context) {
//        if (client != null) {
//            client.disconnect();
//        }
    }

    @Override
    public void setAlias(Context context, String alias) {
        //HMS不支持别名
    }

    @Override
    public void unsetAlias(Context context, String alias) {
        //HMS不支持别名
    }

    @Override
    public void setTags(Context context, String... tags) {
        //HMS不支持tag
    }

    @Override
    public void unsetTags(Context context, String... tags) {
        //hms不支持tag
    }

    @Override
    public String getName() {
        return ReConstants.PUSHNAME_HUAWEI;
    }

    @Override
    public void setMessageListener(ReMessageListener provider) {
        this.sReMessageListener = provider;
    }


    @Override
    public void disable(Context context) {
        if (client != null) {
            client.disconnect();
        }
    }

    @Override
    public void enable(Context context) {
        registerPush(context);
    }

    @Override
    public void onConnected() {
        PendingResult<TokenResult> tokenResult = HuaweiPush.HuaweiPushApi.getToken(client);
        tokenResult.setResultCallback(new ResultCallback<TokenResult>() {
            @Override
            public void onResult(TokenResult result) {
                //这边的结果只表明接口调用成功，是否能收到响应结果只在广播中接收
            }
        });
    }

    @Override
    public void onConnectionSuspended(int cause) {
        Logger.e("华为推送链接失败 " + cause);
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Logger.e("华为推送链接失败 " + result.getErrorCode());
    }
}
