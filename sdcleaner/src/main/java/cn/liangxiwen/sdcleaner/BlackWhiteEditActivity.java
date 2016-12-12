package cn.liangxiwen.sdcleaner;

import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.TextView;

public class BlackWhiteEditActivity extends SCBaseActivity implements FileItem.OnFileClickListener {
    private FileAdapter adapter;
    private FileLister lister;
    private ListView lvFiles;
    private TextView tvPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcleaner_main);
        initViews();
        initList();
    }

    private void initViews() {
        tvPath = findAndConvertView(R.id.tv_path);
        lvFiles = findAndConvertView(R.id.lv_children);
    }

    private void initList() {
        lister = new FileLister(Environment.getExternalStorageDirectory());
        adapter = new FileAdapter(lister, this, this);
        lvFiles.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        } else {
            if (lister != null && !lister.isRoot()) {
                lister.listParent();
                adapter.replaceAll(lister.getChildren(), true);
                tvPath.setText(lister.getCurrent().getFileStr());
                return false;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
    }

    @Override
    public void onFileClick(FileItem item) {
        tvPath.setText(item.getFileStr());
    }
}
