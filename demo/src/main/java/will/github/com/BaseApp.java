package will.github.com;

import android.app.Application;

import java.util.Map;

import will.github.com.client_huawei.HuaWeiClient;
import will.github.com.client_master.RePushClient;
import will.github.com.client_master.RePushMaster;
import will.github.com.client_umeng.UMengClient;
import will.github.com.client_xiaomi.MiPushClient;

/**
 * Created by will on 2017/8/13.
 */

public class BaseApp extends Application {

    private static BaseApp instance;

    public static BaseApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initPush();
    }

    private void initPush() {
        //输入你在小米申请的appid & appkey
        RePushMaster.addPushClient(new MiPushClient("repush_costom", "repush_custom"));
        RePushMaster.addPushClient(new HuaWeiClient());
        RePushMaster.addPushClient(new UMengClient());
        RePushMaster.setSelector(new RePushMaster.ReSelector() {
            @Override
            public String select(Map<String, RePushClient> pushAdapterMap, String brand) {

                return super.select(pushAdapterMap, brand);
            }
        });
        // 配置接收推送消息的服务类
        RePushMaster.setPushIntentService(PushHandlerService.class);
        // 注册推送
        RePushMaster.registerPush(this);

    }
}
