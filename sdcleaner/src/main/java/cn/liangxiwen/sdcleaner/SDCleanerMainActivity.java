package cn.liangxiwen.sdcleaner;

import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;

public class SDCleanerMainActivity extends SCBaseActivity implements AdapterView.OnItemClickListener {
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
        lvFiles.setOnItemClickListener(this);
    }

    private void initList() {
        lister = new FileLister(Environment.getExternalStorageDirectory());
        adapter = new FileAdapter(lister.getChildren());
        lvFiles.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        File file = adapter.getItem(i);
        lister.listChildren(file);
        adapter.replaceAll(lister.getChildren());
        tvPath.setText(lister.getCurrent().toString());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return super.onKeyDown(keyCode, event);
        } else {
            if(lister != null && !lister.isRoot()){
                lister.listParent();
                adapter.replaceAll(lister.getChildren());
                tvPath.setText(lister.getCurrent().toString());
                return false;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
    }
}
