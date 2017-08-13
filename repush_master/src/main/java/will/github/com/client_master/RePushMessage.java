package will.github.com.client_master;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by will on 2017/8/12.
 */

public class RePushMessage implements Serializable {
    private String token;
    private String content;
    private String platform;

    private String title;
    private String description;
    private String alias;
    private int  passThrough;
    private Map<String, String> extra;

    public String getToken() {
        return token;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setToken(String token) {

        this.token = token;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int  getPassThrough() {
        return passThrough;
    }

    public void setPassThrough(int  passthrough) {
        this.passThrough = passthrough;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }
}
