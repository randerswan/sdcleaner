package cn.liangxiwen.sdcleaner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class CleanerMainActivity extends SCBaseActivity implements CleanLogInterface {
    private TextView tvLogs;
    private TextView bnClean;
    private TextView bnEdit;
    private CleanThread cleanThread;
    private BlackListhelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner_main);
        initMainViews();
    }

    private void initMainViews() {
        tvLogs = (TextView) findViewById(R.id.tv_cleaner_log);
        bnClean = (TextView) findViewById(R.id.bn_cleaner_black_clear);
        bnEdit = (TextView) findViewById(R.id.bn_cleaner_black_white_edit);

        bnEdit.setOnClickListener(editClick);
        bnClean.setOnClickListener(cleanClick);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bnClean.setOnClickListener(null);
        bnEdit.setOnClickListener(null);
        cleanThread.clear();
        cleanThread = null;
    }

    private View.OnClickListener cleanClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bnClean.setEnabled(false);
            if (helper == null) {
                helper = new BlackListhelper(getThis(), null, 1);
            }
            ArrayList<FileItem> list = helper.queryBlackWhiteList();
            ArrayList<File> clearList = new ArrayList<File>();
            for (FileItem item : list) {
                clearList.add(item.getFile());
            }
            cleanThread = new CleanThread(clearList);
            cleanThread.setCleanListener(CleanerMainActivity.this);
            cleanThread.start();
        }
    };

    private View.OnClickListener editClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getThis(), BlackWhiteEditActivity.class);
            startActivity(intent);
        }
    };

    @Override
    public void onCleanStart() {
        tvLogs.append(getString(R.string.cleaner_main_clear_start));
    }

    @Override
    public void onCleanFile(File file, String msg) {
        tvLogs.append("\n");
        tvLogs.append(msg);
        tvLogs.append("\n");
        tvLogs.append(file.toString());
    }

    @Override
    public void onCleanEnd() {
        tvLogs.append("\n");
        tvLogs.append(getString(R.string.cleaner_main_clear_end));
        tvLogs.append("\n");
        tvLogs.append("\n");
        bnClean.setEnabled(true);
    }
}
