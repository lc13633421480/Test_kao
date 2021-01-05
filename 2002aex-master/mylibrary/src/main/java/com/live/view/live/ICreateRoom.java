package com.live.view.live;

import com.live.base.IBaseModel;
import com.live.base.IBasePersenter;
import com.live.base.IBaseView;
import com.live.api.Callback;
import com.live.bean.CreateLiveRoomBean;
import com.live.bean.MyRoomBean;

import java.util.HashMap;

public interface ICreateRoom {

    interface View extends IBaseView {
        void postCreateRoomResult(CreateLiveRoomBean createLiveRoomBean);
        void getResult(MyRoomBean myRoomBean);
    }

    interface Presenter extends IBasePersenter<ICreateRoom.View> {
        void postCreateRoom(HashMap<String, String> map);
        void myRoom();
    }


    interface Model extends IBaseModel {
        void postCreateRoom(HashMap<String, String> map, Callback callback);
        void myRoom(Callback callback);
    }
}
