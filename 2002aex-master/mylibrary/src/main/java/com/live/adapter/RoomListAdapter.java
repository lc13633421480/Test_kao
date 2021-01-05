package com.live.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.live.R;
import com.live.base.BaseAdapter;
import com.live.bean.RoomListBean;

import java.util.List;

public class RoomListAdapter extends BaseAdapter {
    private Context context;
    public RoomListAdapter(Context context, List Data) {
        super(context, Data);
        this.context = context;
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_room_list_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        RoomListBean.DataBean bean = (RoomListBean.DataBean) data;

        TextView name= (TextView) vh.getViewById(R.id.tv_room_list_name);
        ImageView img_room_list= (ImageView) vh.getViewById(R.id.img_room_list);

        name.setText(bean.getName());
        Glide.with(context).load(bean.getIcon()).into(img_room_list);
    }
}
