package cn.liangxiwen.sdcleaner;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

/**
 *
 */

public class FileAdapter extends BaseAdapter {
    private ArrayList<File> children = new ArrayList<File>();

    public FileAdapter(ArrayList<File> children) {
        if (children != null) {
            this.children.addAll(children);
        }
    }

    @Override
    public int getCount() {
        return children.size();
    }

    @Override
    public File getItem(int i) {
        return children.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void clear() {
        children.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(ArrayList<File> children) {
        clear();
        if (children != null) {
            this.children.addAll(children);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = View.inflate(viewGroup.getContext(), R.layout.item_file, null);
        }
        TextView tvName = (TextView) view.findViewById(R.id.tv_item_file_name);
        tvName.setText(getItem(i).getName());
        return view;
    }
}
