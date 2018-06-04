package com.chint.paylibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chint.paylibrary.config.PayType;

/**
 * Project:PingPayDemo
 * Author:dyping
 * Date:2017/7/25 19:38
 */

public class ChannelAdapter extends BaseAdapter {

    String[] channels;
    String[] channelsName;

    Context context;
    LayoutInflater layoutInflater;

    public ChannelAdapter(String[] channels, String[] channelsName, Context context) {
        this.channels = channels;
        this.channelsName = channelsName;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return channelsName.length;
    }

    @Override
    public Object getItem(int position) {
        return channelsName[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = layoutInflater.inflate(R.layout.bottom_lib_item, parent, false);
        TextView tx = (TextView) view.findViewById(R.id.item);
        ImageView img = (ImageView) view.findViewById(R.id.item_icon);
        tx.setText(channelsName[position]);
        switch (channels[position]) {
            case PayType.ALI_PAY:
                img.setImageResource(R.drawable.ali_icon);
                break;

            case PayType.WX_PAY:
                img.setImageResource(R.drawable.wx_icon);
                break;
        }
        return view;
    }
}
