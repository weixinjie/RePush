package will.github.com;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import will.github.com.client_master.RePushClient;
import will.github.com.client_master.RePushMaster;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv_current_platform;
    Button bt_open_push;
    Button bt_close_push;
    Button bt_set_alias;
    Button bt_set_tags;
    Button bt_get_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        tv_current_platform = (TextView) findViewById(R.id.tv_current_platform);
        bt_open_push = (Button) findViewById(R.id.bt_open_push);
        bt_close_push = (Button) findViewById(R.id.bt_close_push);
        bt_set_alias = (Button) findViewById(R.id.bt_set_alias);
        bt_set_tags = (Button) findViewById(R.id.bt_set_tags);
        bt_get_token = (Button) findViewById(R.id.bt_get_token);

        bt_open_push.setOnClickListener(this);
        bt_close_push.setOnClickListener(this);
        bt_set_alias.setOnClickListener(this);
        bt_set_tags.setOnClickListener(this);
        bt_get_token.setOnClickListener(this);


        tv_current_platform.setText(RePushMaster.getCurrentPlatForm());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_open_push:
                RePushMaster.enable(this);
                break;
            case R.id.bt_close_push:
                RePushMaster.disable(this);
                break;
            case R.id.bt_set_alias:
                RePushMaster.setAlias(this, "alias");
                break;
            case R.id.bt_set_tags:
                RePushMaster.setTags(this, "tag");
                break;
            case R.id.bt_get_token:
                String token = SharePrefUtil.getString("token", "token is null");
                String cacheStr = tv_current_platform.getText().toString().trim();
                StringBuilder sb = new StringBuilder();
                sb.append(cacheStr);
                sb.append("\n");
                sb.append(token);
                tv_current_platform.setText(sb.toString());
                break;
        }
    }
}
