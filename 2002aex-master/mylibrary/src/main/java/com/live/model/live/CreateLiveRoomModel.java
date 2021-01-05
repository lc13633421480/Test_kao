package com.live.model.live;


import com.live.base.BaseModel;
import com.live.api.Callback;
import com.live.bean.CreateLiveRoomBean;
import com.live.bean.MyRoomBean;
import com.live.net.CommonSubscriber;
import com.live.net.HttpManager;
import com.live.utils.RxUtils;
import com.live.view.live.ICreateRoom;

import java.util.HashMap;

public class CreateLiveRoomModel extends BaseModel implements ICreateRoom.Model{

    //创建直播房间
    @Override
    public void postCreateRoom(HashMap<String, String> map, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().postCreateRoom(map)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<CreateLiveRoomBean>(callback) {
                            @Override
                            public void onNext(CreateLiveRoomBean createLiveRoomBean) {
                                callback.success(createLiveRoomBean);
                            }
                        })
        );
    }

    @Override
    public void myRoom(Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().myRoom()
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<MyRoomBean>(callback) {
                            @Override
                            public void onNext(MyRoomBean myRoomBean) {
                                callback.success(myRoomBean);
                            }
                        })
        );
    }
}
