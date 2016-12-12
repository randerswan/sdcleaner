package cn.liangxiwen.sdcleaner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 *
 */

public class FileAdapter extends BaseAdapter {
    private ArrayList<FileItem> children = new ArrayList<FileItem>();
    private BlackListhelper helper;
    private FileLister lister;
    private FileItem.OnFileClickListener onFileClickListener;
    private LayoutInflater inflater;

    public FileAdapter(FileLister lister, FileItem.OnFileClickListener listener, Activity act) {
        helper = new BlackListhelper(act, FileItem.class.getSimpleName(), null, 1);
        this.lister = lister;
        this.onFileClickListener = listener;
        this.inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        children.addAll(lister.getChildren());
        initItems();
    }

    private void initItems() {
        ArrayList<FileItem> record = helper.queryBlackWhiteList();
        for (FileItem file : children) {
            file.setLister(lister);
            file.setAdapter(this);
            file.setOnFileClickListener(onFileClickListener);
            file.setHelper(helper);
            for (FileItem item : record) {
                if (file.getFile().toString().equals(item.getFile().toString())) {
                    file.setType(item.getType());
                }
            }
        }
    }

    @Override
    public int getCount() {
        return children.size();
    }

    @Override
    public FileItem getItem(int i) {
        return children.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private void clearItem() {
        for (FileItem item : children) {
            item.clear();
        }
    }

    public void clear() {
        children.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(ArrayList<FileItem> children, boolean clearItem) {
        if (clearItem) {
            clearItem();
        }
        clear();
        if (children != null) {
            this.children.addAll(children);
            initItems();
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_file, viewGroup, false);
        }
        TextView tvName = (TextView) view.findViewById(R.id.tv_item_file_name);
        View tvFolder = view.findViewById(R.id.tv_item_folder);
        FileItem itemFile = getItem(i);
        itemFile.updateCheckBox(view);
        tvName.setText(itemFile.getFile().getName());
        tvFolder.setVisibility(itemFile.getFile().isDirectory() ? View.VISIBLE : View.INVISIBLE);
        view.setOnClickListener(itemFile);
        itemFile.setIndex(i);

        return view;
    }
}
