package cn.liangxiwen.sdcleaner;

import android.app.Application;

public class SDCleanerApplication extends Application {
    private static Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        this.app = this;
    }

    public static Application getApp() {
        return app;
    }
}
