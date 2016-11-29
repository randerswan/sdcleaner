package cn.liangxiwen.sdcleaner;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FileItem {
    public static final byte FILE_TYPE_NONE = 0;
    public static final byte FILE_TYPE_BLACK = 1;
    public static final byte FILE_TYPE_WHITE = 2;

    private String file;
    private String remark;
    private byte type;

    private FileItem(String file, byte type) {
        this.file = file;
        this.type = type;
    }

    public static FileItem createNormalItem(String file) {
        return new FileItem(file, FILE_TYPE_NONE);
    }

    public static FileItem createBlackItem(String file) {
        return new FileItem(file, FILE_TYPE_BLACK);
    }

    public static FileItem createWhiteItem(String file) {
        return new FileItem(file, FILE_TYPE_WHITE);
    }

    public boolean isInBlackList() {
        return type == FILE_TYPE_BLACK;
    }

    public boolean isInWhiteList() {
        return type == FILE_TYPE_WHITE;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void save(){
        SQLiteOpenHelper helper = new SQLiteOpenHelper(SDCleanerApplication.getApp(), "", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase sqLiteDatabase) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            }
        };
    }
}
