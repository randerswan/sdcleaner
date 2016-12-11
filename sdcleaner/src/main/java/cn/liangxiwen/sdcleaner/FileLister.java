package cn.liangxiwen.sdcleaner;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileLister {
    private FileItem root;
    private FileItem current;
    private ArrayList<FileItem> children = new ArrayList<FileItem>();

    public FileLister(File file) {
        FileItem item = new FileItem();
        this.root = item;
        item.setFile(file.toString());
        listChildren(item);
    }

    public ArrayList<FileItem> getChildren() {
        return children;
    }

    private FileLister getChildFile(File childFile) {
        FileLister child = new FileLister(childFile);

        return child;
    }

    public FileItem getCurrent() {
        return current;
    }

    public void listChildren(FileItem file) {
        this.current = file;
        try {
            List<File> list = Arrays.asList(file.getFile().listFiles());
            Collections.sort(list);
            children.clear();
            for (File f : list) {
                FileItem item = new FileItem();
                item.setFile(f.toString());
                children.add(item);
            }
        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
        }
    }

    public void listParent() {
        if (!isRoot()) {
            FileItem item = new FileItem();
            item.setFile(current.getFile().getParent().toString());
            listChildren(item);
        }
    }

    public boolean isRoot() {
        return root.getFile().toString().equals(current.getFile().toString());
    }
}
