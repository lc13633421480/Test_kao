package com.live.ui;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.live.R;
import com.live.adapter.RoomListAdapter;
import com.live.base.BaseActivity;
import com.live.base.BaseAdapter;
import com.live.bean.LiveUrlBean;
import com.live.bean.RoomListBean;
import com.live.bean.StartLiveBean;
import com.live.presenter.live.RoomListPresenter;
import com.live.view.live.IRoomList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomListActivity extends BaseActivity<IRoomList.Presenter> implements IRoomList.View {

    private Button jump;
    private RecyclerView mRlv;

    @Override
    protected int getLayout() {
        return R.layout.activity_room_list;
    }

    @Override
    protected void initView() {
        jump = findViewById(R.id.btn_room_list_jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoomListActivity.this,LiveStreamingActivity.class);
                startActivity(intent);
            }
        });

        mRlv = findViewById(R.id.mRlv_room_list);

    }

    @Override
    protected IRoomList.Presenter createPersenter() {
        return new RoomListPresenter(this);
    }

    @Override
    protected void initData() {
        persenter= new RoomListPresenter(this);
        persenter.getRoomList();
    }

    @Override
    public void getRoomListResult(RoomListBean roomListBean) {
        List<RoomListBean.DataBean> data = roomListBean.getData();
        mRlv.setLayoutManager(new GridLayoutManager(this,2));
        RoomListAdapter roomListAdapter = new RoomListAdapter(this,data);
        mRlv.setAdapter(roomListAdapter);

        //点击进入他人直播
        roomListAdapter.addListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                int isopen = data.get(pos).getIsopen();
                if(isopen == 1){  //开放的房间
                    Intent intent = new Intent(RoomListActivity.this, LiveActivity.class);
                    intent.putExtra("id",data.get(pos).getId());
                    startActivity(intent);

                }else if(isopen == 2){  //密码
                    Toast.makeText(RoomListActivity.this, "不是公开的", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void getResult(StartLiveBean startLiveBean) {

    }

    @Override
    public void getResult(LiveUrlBean liveUrlBean) {
        
    }
}