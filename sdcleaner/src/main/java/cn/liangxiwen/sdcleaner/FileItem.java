package cn.liangxiwen.sdcleaner;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.io.File;

public class FileItem implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    public static final byte FILE_TYPE_NONE = 1;
    public static final byte FILE_TYPE_BLACK = 2;
    public static final byte FILE_TYPE_WHITE = 3;

    private String file;
    private String remark;
    private FileLister lister;
    private byte type;
    private int index;
    private FileAdapter adapter;
    private OnFileClickListener onFileClickListener;

    private BlackListhelper helper;

    public FileItem() {
    }

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

    public String getFileStr() {
        return file;
    }

    public File getFile() {
        return new File(file);
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public FileLister getLister() {
        return lister;
    }

    public void setLister(FileLister lister) {
        this.lister = lister;
    }

    public FileAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(FileAdapter adapter) {
        this.adapter = adapter;
    }

    public OnFileClickListener getOnFileClickListener() {
        return onFileClickListener;
    }

    public void setOnFileClickListener(OnFileClickListener onFileClickListener) {
        this.onFileClickListener = onFileClickListener;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public void setHelper(BlackListhelper helper) {
        this.helper = helper;
    }

    public void updateCheckBox(View view) {
        CheckBox cbWhite = (CheckBox) view.findViewById(R.id.cb_white);
        CheckBox cbBlack = (CheckBox) view.findViewById(R.id.cb_black);
        cbWhite.setOnCheckedChangeListener(null);
        cbBlack.setOnCheckedChangeListener(null);

        cbWhite.setChecked(isInWhiteList());
        cbBlack.setChecked(isInBlackList());

        cbWhite.setOnCheckedChangeListener(this);
        cbBlack.setOnCheckedChangeListener(this);
    }

    public void save() {
        ContentValues cv = new ContentValues();
        cv.put("FilePath", file);
        cv.put("whiteOrBlack", type);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            long result = db.update("BlackList", cv, " FilePath=? ", new String[]{file.toString()});
            if (result <= 0) {
                db.insert("BlackList", null, cv);
            }
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
        }
        db.close();
    }

    public void clear() {
        adapter = null;
        lister = null;
        onFileClickListener = null;
    }

    @Override
    public void onClick(View view) {
        lister.listChildren(this);
        adapter.replaceAll(lister.getChildren(), false);
        if (onFileClickListener != null) {
            onFileClickListener.onFileClick(lister.getCurrent());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        boolean isWhite = compoundButton.getId() == R.id.cb_white;
        boolean isBlack = compoundButton.getId() == R.id.cb_black;
        if (isWhite) {
            type = b ? FILE_TYPE_WHITE : FILE_TYPE_NONE;
        }
        if (isBlack) {
            type = b ? FILE_TYPE_BLACK : FILE_TYPE_NONE;
        }
        adapter.notifyDataSetChanged();
        save();
    }

    public interface OnFileClickListener {
        void onFileClick(FileItem current);
    }
}
