package cn.liangxiwen.sdcleaner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

public class SCBaseActivity extends Activity {
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(getThis());
    }

    public <T extends View> T findAndConvertView(int id) {
        try {
            T t = (T) findViewById(id);
            return t;
        } catch (Exception e) {
            return null;
        }
    }

    public void showLoadingDialog(int strId) {
        dialog.setMessage(getString(strId));
        dialog.show();
    }

    public void dismissLoadingDialog() {
        dialog.dismiss();
    }

    public <T extends Activity> T getThis() {
        return (T) SCBaseActivity.this;
    }
}
