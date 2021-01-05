package com.live.ui;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.live.R;
import com.live.base.BaseActivity;
import com.live.bean.CreateLiveRoomBean;
import com.live.bean.MyRoomBean;
import com.live.presenter.live.CreateLiveRoomPresenter;
import com.live.view.live.ICreateRoom;

import java.util.HashMap;

//TODO 直播
public class LiveStreamingActivity extends BaseActivity<ICreateRoom.Presenter> implements ICreateRoom.View {


    EditText etName;
    private HashMap<String, String> map;
    private LinearLayout ll_room;
    private TextView room_name;
    private ImageView img_room;
    private Button btn_cj;

    @Override
    protected int getLayout() {
        return R.layout.activity_live_streaming;
    }

    @Override
    protected void initView() {
        etName = findViewById(R.id.et_name);
        ll_room = findViewById(R.id.ll_room);
        room_name = findViewById(R.id.room_name);
        img_room = findViewById(R.id.img_room);
        btn_cj = findViewById(R.id.btn_cj);


    }

    @Override
    protected ICreateRoom.Presenter createPersenter() {
        return new CreateLiveRoomPresenter(this);
    }

    @Override
    protected void initData() {
        String s = etName.getText().toString();
        map = new HashMap<>();
        map.put("room_name", "az");
        map.put("room_icon", "https://shop-app1.oss-cn-beijing.aliyuncs.com/live/1/room.jpg");
        map.put("isopen", "1");
        persenter.myRoom();
        btn_cj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persenter.postCreateRoom(map);
            }
        });
    }


    @Override
    public void postCreateRoomResult(CreateLiveRoomBean createLiveRoomBean) {
        String name = createLiveRoomBean.getData().getName();
        Log.e("TAG", "名字： " + name);
        Toast.makeText(this, "名字：" + name, Toast.LENGTH_SHORT).show();

        etName.setVisibility(View.GONE);
    }

    @Override
    public void getResult(MyRoomBean myRoomBean) {
        if(myRoomBean.getErrno() == 0){
            ll_room.setVisibility(View.VISIBLE);
            MyRoomBean.DataBean data = myRoomBean.getData();
            room_name.setText(data.getName());
            Glide.with(this).load(data.getIcon()).into(img_room);


            img_room.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LiveStreamingActivity.this, PushActivity.class);
                    intent.putExtra("id",data.getId());
                    startActivity(intent);
                }
            });
        }
    }
}