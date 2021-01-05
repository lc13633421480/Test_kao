package com.live.model.live;

import com.live.base.BaseModel;
import com.live.api.Callback;
import com.live.bean.LiveUrlBean;
import com.live.bean.RoomListBean;
import com.live.bean.StartLiveBean;
import com.live.net.CommonSubscriber;
import com.live.net.HttpManager;
import com.live.utils.RxUtils;
import com.live.view.live.IRoomList;

import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class RoomListModel extends BaseModel implements IRoomList.Model {
    @Override
    public void getRoomList(Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getRoomList()
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<RoomListBean>(callback) {
                            @Override
                            public void onNext(RoomListBean roomListBean) {
                                callback.success(roomListBean);
                            }
                        })
        );
    }

    @Override
    public void startLive(HashMap map,Callback callback) {
        addDisposable(
                (Disposable) HttpManager.getInstance().getService().startLive(map)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<StartLiveBean>(callback) {
                            @Override
                            public void onNext(StartLiveBean startLiveBean) {
                                callback.success(startLiveBean);
                            }
                        })
        );
    }

    @Override
    public void roomLiveUrl(int roomid, Callback callback) {
        addDisposable(
                   HttpManager.getInstance().getService().roomLiveUrl(roomid)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<LiveUrlBean>(callback) {
                            @Override
                            public void onNext(LiveUrlBean liveUrlBean) {
                                callback.success(liveUrlBean);
                            }
                        })
        );
    }
}
