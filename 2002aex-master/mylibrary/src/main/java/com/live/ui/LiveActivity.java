package com.live.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.live.app.Global;
import com.live.R;
import com.live.base.BaseActivity;
import com.live.bean.LiveUrlBean;
import com.live.bean.RoomListBean;
import com.live.bean.StartLiveBean;
import com.live.presenter.live.RoomListPresenter;
import com.live.utils.SpUtils;
import com.live.view.live.IRoomList;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.nio.charset.StandardCharsets;

public class LiveActivity extends BaseActivity<IRoomList.Presenter> implements IRoomList.View,ITXLivePlayListener, View.OnClickListener {

    private static String TAG = LiveActivity.class.getSimpleName();

    ImageView imgBack;

    TXLivePlayer mLivePlayer; //直播拉流的视频播放器
    TXLivePlayConfig mPlayerConfig; //播放器的配置类
    TXCloudVideoView mVideoView; //播放器view

    private String mPlayUrl ;
    private boolean mIsPlaying;


    @Override
    protected int getLayout() {
        return R.layout.activity_live;
    }

    @Override
    protected void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        imgBack = findViewById(R.id.img_back);
        mVideoView = findViewById(R.id.video_play);
        mPlayerConfig = new TXLivePlayConfig();
        mLivePlayer = new TXLivePlayer(this);


        initListener();
    }


    @Override
    protected IRoomList.Presenter createPersenter() {
        return new RoomListPresenter(this);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);
        persenter.roomLiveUrl(id);


    }

    private void initListener(){
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_back){
            stopPlay();
            finish();
        }
    }

    private void startPlay(){

        mLivePlayer.setPlayerView(mVideoView);
        mLivePlayer.setPlayListener(this);
        mLivePlayer.enableHardwareDecode(false);
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        mPlayerConfig.setEnableMessage(true);
        mLivePlayer.setConfig(mPlayerConfig);
        int code = mLivePlayer.startPlay(mPlayUrl,TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
        Log.e("111", "startPlay: "+mPlayUrl );
        mIsPlaying = code == 0;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPlayEvent(int event, Bundle param) {
        Log.d(TAG, "receive event: " + event + ", " + param.getString(TXLiveConstants.EVT_DESCRIPTION));
        switch (event) {
            case TXLiveConstants.PLAY_EVT_PLAY_BEGIN:
                //Log.d("AutoMonitor", "PlayFirstRender,cost=" + (System.currentTimeMillis() - mStartPlayTS));
            case TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME:
                //stopLoadingAnimation();
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_LOADING:
                //startLoadingAnimation();
                break;
            case TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION:
                Log.d(TAG, "size " + param.getInt(TXLiveConstants.EVT_PARAM1) + "x" + param.getInt(TXLiveConstants.EVT_PARAM2));
                break;
            case TXLiveConstants.PLAY_EVT_GET_MESSAGE:
                byte[] data = param.getByteArray(TXLiveConstants.EVT_GET_MSG);
                String seiMessage = "";
                if (data != null && data.length > 0) {
                    try {
                        seiMessage = new String(data, StandardCharsets.UTF_8);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getApplicationContext(), seiMessage, Toast.LENGTH_SHORT).show();
                break;
            case TXLiveConstants.PLAY_EVT_CHANGE_ROTATION:
                break;
            case TXLiveConstants.PLAY_ERR_NET_DISCONNECT:
            case TXLiveConstants.PLAY_EVT_PLAY_END:
                stopPlay();
                break;
        }
        if (event < 0) {
            Toast.makeText(this, param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {
        Log.d(TAG, "Current status, CPU:" + bundle.getString(TXLiveConstants.NET_STATUS_CPU_USAGE) +
                ", RES:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT) +
                ", SPD:" + bundle.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps" +
                ", FPS:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS) +
                ", ARA:" + bundle.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps" +
                ", VRA:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps");
    }

    private void stopPlay() {
        if (!mIsPlaying) {
            return;
        }
        if (mLivePlayer != null) {
            mLivePlayer.stopRecord();
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
        }
        mIsPlaying = false;

        //处理UI相关操作
    }

    @Override
    public void getResult(LiveUrlBean liveUrlBean) {
        if(liveUrlBean.getErrno() == 0){
            String play_url = liveUrlBean.getData().getPlay_url();
//            SpUtils.getInstance().setValue("play_url",play_url);
            mPlayUrl = play_url;

            startPlay();
        }

    }



    @Override
    public void getRoomListResult(RoomListBean createLiveRoomBean) {

    }

    @Override
    public void getResult(StartLiveBean startLiveBean) {

    }


}
