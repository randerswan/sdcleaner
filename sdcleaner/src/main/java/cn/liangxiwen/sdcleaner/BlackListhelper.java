package cn.liangxiwen.sdcleaner;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BlackListhelper extends SQLiteOpenHelper {

    public BlackListhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BlackListhelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table BlackList('FilePath' varchar primary key not null, 'whiteOrBlack' integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList<FileItem> queryBlackWhiteList(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<FileItem> record = new ArrayList<FileItem>();
        Cursor cur = db.rawQuery("select * from BlackList", null);
        while (cur.moveToNext()) {
            String file = cur.getString(0);
            int type = cur.getInt(1);
            FileItem item = type == 1 ? FileItem.createBlackItem(file) : FileItem.createWhiteItem(file);
            record.add(item);
        }
        cur.close();
        db.close();
        return record;
    }

}
