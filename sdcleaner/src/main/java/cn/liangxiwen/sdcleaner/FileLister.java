package cn.liangxiwen.sdcleaner;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileLister {
    private File root;
    private File current;
    private ArrayList<File> children = new ArrayList<File>();

    public FileLister(File file) {
        this.root = file;
        listChildren(file);
    }

    public ArrayList<File> getChildren() {
        return children;
    }

    private FileLister getChildFile(File childFile) {
        FileLister child = new FileLister(childFile);

        return child;
    }

    public File getCurrent() {
        return current;
    }

    public void listChildren(File file) {
        this.current = file;
        try {
            List<File> list = Arrays.asList(file.listFiles());
            children.clear();
            children.addAll(list);
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
        }
    }

    public void listParent(){
        if(!isRoot()){
            listChildren(current.getParentFile());
        }
    }

    public boolean isRoot() {
        return root.toString().equals(current.toString());
    }
}
