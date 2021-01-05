package com.live.presenter.live;


import com.live.base.BasePresenter;
import com.live.api.Callback;
import com.live.bean.CreateLiveRoomBean;
import com.live.bean.MyRoomBean;
import com.live.model.live.CreateLiveRoomModel;
import com.live.view.live.ICreateRoom;

import java.util.HashMap;

public class CreateLiveRoomPresenter extends BasePresenter<ICreateRoom.View> implements ICreateRoom.Presenter{
    ICreateRoom.View view;
    ICreateRoom.Model model;

    public CreateLiveRoomPresenter(ICreateRoom.View view) {
        this.view = view;
        model=new CreateLiveRoomModel();
    }

    @Override
    public void postCreateRoom(HashMap<String, String> map) {
        if(view!=null){
            model.postCreateRoom(map, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.postCreateRoomResult((CreateLiveRoomBean) o);
                }
            });
        }
    }

    @Override
    public void myRoom() {
        model.myRoom(new Callback() {
            @Override
            public void fail(String msg) {
                if(view != null){
                    view.tips(msg);
                }
            }

            @Override
            public void success(Object o) {
                if(view != null){
                    view.getResult((MyRoomBean) o);
                }
            }
        });
    }
}
