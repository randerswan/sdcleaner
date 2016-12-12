package cn.liangxiwen.sdcleaner;

import java.io.File;

public interface CleanLogInterface {
    void onCleanStart();

    void onCleanFile(File file, String msg);

    void onCleanEnd();
}
