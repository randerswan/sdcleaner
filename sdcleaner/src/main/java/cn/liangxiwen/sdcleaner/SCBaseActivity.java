package cn.liangxiwen.sdcleaner;

import android.app.Activity;
import android.view.View;

public class SCBaseActivity extends Activity {

    public <T extends View> T findAndConvertView(int id) {
        try {
            T t = (T) findViewById(id);
            return t;
        } catch (Exception e) {
            return null;
        }
    }
}
