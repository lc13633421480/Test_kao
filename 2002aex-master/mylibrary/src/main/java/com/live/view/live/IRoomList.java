package com.live.view.live;

import com.live.base.IBaseModel;
import com.live.base.IBasePersenter;
import com.live.base.IBaseView;
import com.live.api.Callback;
import com.live.bean.LiveUrlBean;
import com.live.bean.RoomListBean;
import com.live.bean.StartLiveBean;

import java.util.HashMap;

public interface IRoomList {
    interface View extends IBaseView {
        void getRoomListResult(RoomListBean createLiveRoomBean);
        void getResult(StartLiveBean startLiveBean);
        void getResult(LiveUrlBean liveUrlBean);
    }

    interface Presenter extends IBasePersenter<IRoomList.View> {
        void getRoomList();
        void startLive(HashMap map);
        void roomLiveUrl(int roomid);
    }


    interface Model extends IBaseModel {
        void getRoomList(Callback callback);
        void startLive(HashMap map,Callback callback);
        void roomLiveUrl(int roomid,Callback callback);
    }
}
