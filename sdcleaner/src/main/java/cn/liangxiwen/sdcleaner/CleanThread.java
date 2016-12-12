package cn.liangxiwen.sdcleaner;

import android.os.Handler;

import java.io.File;
import java.util.ArrayList;

public class CleanThread extends Thread {
    private CleanLogInterface cleanListener;
    private ArrayList<File> tobeCleanList;
    private Handler handler;

    public CleanThread(ArrayList<File> tobeClean) {
        this.tobeCleanList = tobeClean;
        handler = new Handler();
    }

    public CleanLogInterface getCleanListener() {
        return cleanListener;
    }

    public void setCleanListener(CleanLogInterface cleanListener) {
        this.cleanListener = cleanListener;
    }

    public void clear() {
        handler = null;
        cleanListener = null;
    }

    @Override
    public void run() {
        if (handler != null && cleanListener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    cleanListener.onCleanStart();
                }
            });
        }
        for (File toClean : tobeCleanList) {
            clearIfNotFolder(toClean);
        }
        if (handler != null && cleanListener != null) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    cleanListener.onCleanEnd();
                }
            });
        }
    }

    private void clearIfNotFolder(File file) {
        if (!file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    clearIfNotFolder(child);
                }
            } else {
                if (handler != null && cleanListener != null) {
                    handler.post(new ClearRunnalbe(file, cleanListener, "文件夹原本为空"));
                }
                return;
            }
            int len = file.listFiles() != null ? file.listFiles().length : 0;
            if (len == 0) {
//                file.delete();
                if (handler != null && cleanListener != null) {
                    handler.post(new ClearRunnalbe(file, cleanListener, "文件夹已空，删除文件夹"));
                }
            } else {
                if (handler != null && cleanListener != null) {
                    handler.post(new ClearRunnalbe(file, cleanListener, "文件夹还有" + len + "个文件，无法删除！"));
                }
            }
        } else {
//            file.delete();
            if (handler != null && cleanListener != null) {
                handler.post(new ClearRunnalbe(file, cleanListener, "删除文件"));
            }
        }
    }

    private static class ClearRunnalbe implements Runnable {
        private File file;
        private CleanLogInterface cleanListener;
        private String msg;

        public ClearRunnalbe(File file, CleanLogInterface cleanListener, String msg) {
            this.file = file;
            this.msg = msg;
            this.cleanListener = cleanListener;
        }

        @Override
        public void run() {
            cleanListener.onCleanFile(file, msg);
            cleanListener = null;
            file = null;
            msg = null;
        }
    }
}
