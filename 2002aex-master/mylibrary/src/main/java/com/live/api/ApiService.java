package com.live.api;


import com.live.bean.CreateLiveRoomBean;
import com.live.bean.LiveUrlBean;
import com.live.bean.MyRoomBean;
import com.live.bean.RoomListBean;
import com.live.bean.StartLiveBean;

import java.util.HashMap;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    String BASE_URL = "https://cdplay.cn/";

    //创建直播房间
    @POST("api/live/createRoom")
    @FormUrlEncoded
    Flowable<CreateLiveRoomBean> postCreateRoom(@FieldMap HashMap<String, String> map);

    @GET("api/live/getRoomList")
    Flowable<RoomListBean> getRoomList();

    //http://cdplay.cn/api/live/myroom
    //获取自己房间的消息
    @POST("api/live/myroom")
    Flowable<MyRoomBean>myRoom();

    //https://cdplay.cn/api/live/startLive
    //开启直播
    @POST("api/live/startLive")
    @FormUrlEncoded
    Flowable<StartLiveBean>startLive(@FieldMap HashMap<String, String> map);

    //http://cdplay.cn/api/live/roomLiveUrl
    //房间播放地址
    @POST("api/live/roomLiveUrl")
    @FormUrlEncoded
    Flowable<LiveUrlBean>roomLiveUrl(@Field("roomid") int roomid);
}
