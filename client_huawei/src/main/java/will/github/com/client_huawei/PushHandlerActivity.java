package will.github.com.client_huawei;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import java.util.List;

import will.github.com.client_master.ReConstants;
import will.github.com.client_master.RePushMessage;

public class PushHandlerActivity extends AppCompatActivity {

    List<String> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromBrowser();
        finish();
    }

    /**
     * 从deep link中获取数据
     */
    private void getDataFromBrowser() {
        //服务端需要传入到自定义动作的URI为：【repush://huawei.push/我是跳转的数据】
        Uri data = getIntent().getData();
        try {
            params = data.getPathSegments();
            String content = params.get(0);

            if (HuaWeiClient.sReMessageListener != null) {
                RePushMessage message = new RePushMessage();
                message.setPlatform(ReConstants.PUSHNAME_HUAWEI);
                message.setContent(content);
                HuaWeiClient.sReMessageListener.onNotificationMessageClicked(this, message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
