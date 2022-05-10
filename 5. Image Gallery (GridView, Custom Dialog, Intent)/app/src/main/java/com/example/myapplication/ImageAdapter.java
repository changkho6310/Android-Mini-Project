package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Image> imageArray;

    public ImageAdapter(Context context, int layout, ArrayList<Image> imageArray) {
        this.context = context;
        this.layout = layout;
        this.imageArray = imageArray;
    }

    @Override
    public int getCount() {
        return imageArray.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    private class ViewHolder {
        ImageView img;
        TextView txt;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.img = (ImageView) view.findViewById(R.id.image_item_img);
            viewHolder.txt = (TextView) view.findViewById(R.id.image_item_tv);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Image image = imageArray.get(i);
        viewHolder.img.setImageResource(image.getImg());
        viewHolder.txt.setText(image.getContent());

        return view;
    }
}
