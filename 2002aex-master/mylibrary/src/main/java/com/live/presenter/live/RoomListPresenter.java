package com.live.presenter.live;

import com.live.base.BasePresenter;
import com.live.api.Callback;
import com.live.bean.LiveUrlBean;
import com.live.bean.MyRoomBean;
import com.live.bean.RoomListBean;
import com.live.bean.StartLiveBean;
import com.live.model.live.RoomListModel;
import com.live.view.live.IRoomList;

import java.util.HashMap;

public class RoomListPresenter extends BasePresenter<IRoomList.View> implements IRoomList.Presenter {
    IRoomList.View view;
    IRoomList.Model model;

    public RoomListPresenter(IRoomList.View view) {
        this.view = view;
        model = new RoomListModel();
    }

    @Override
    public void getRoomList() {
        if (view != null) {
            model.getRoomList(new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getRoomListResult((RoomListBean) o);
                }
            });
        }
    }

    @Override
    public void startLive(HashMap map) {
        model.startLive(map,new Callback() {
            @Override
            public void fail(String msg) {
                if(view != null){
                    view.tips(msg);
                }
            }

            @Override
            public void success(Object o) {
                if(view != null){
                    view.getResult((StartLiveBean) o);
                }
            }
        });
    }

    @Override
    public void roomLiveUrl(int roomid) {
        model.roomLiveUrl(roomid, new Callback() {
            @Override
            public void fail(String msg) {
                if(view != null){
                    view.tips(msg);
                }
            }

            @Override
            public void success(Object o) {
                if(view != null){
                    view.getResult((LiveUrlBean) o);
                }
            }
        });
    }
}
