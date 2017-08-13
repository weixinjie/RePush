package will.github.com;

import android.content.SharedPreferences;

/**
 * @author susyimes
 *         SharePreferences操作工具类
 */
public class SharePrefUtil {
    private final static String SP_NAME = "will";
    private static SharedPreferences sp;


    /**
     * 保存字符串
     *
     * @param key
     * @param value
     */
    public static void saveString(String key, String value) {
        if (sp == null)
            sp = BaseApp.getInstance().getSharedPreferences(SP_NAME, 0);
        sp.edit().putString(key, value).commit();
    }

    /**
     * 获取字符值
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(String key, String defValue) {
        if (sp == null)
            sp = BaseApp.getInstance().getSharedPreferences(SP_NAME, 0);
        return sp.getString(key, defValue);
    }


}
