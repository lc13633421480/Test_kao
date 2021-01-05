package com.example.test_kao.interfaces.me;

import com.example.test_kao.bean.login.TokenBean;
import com.example.test_kao.bean.me.LogOutBean;
import com.example.test_kao.bean.me.UserInfoBean;
import com.example.test_kao.interfaces.Callback;
import com.example.test_kao.interfaces.IBaseModel;
import com.example.test_kao.interfaces.IBasePresenter;
import com.example.test_kao.interfaces.IBaseView;

import java.util.Map;

public interface IMe {
    interface View extends IBaseView {
        void getResult(UserInfoBean userInfoBean);
        void getResult(LogOutBean logOutBean);
        void getResult(TokenBean tokenBean);
    }
    interface Presenter extends IBasePresenter<View> {
        void updateUserInfo(Map map);
        void logout();
        void refreshToken();
    }
    interface Model extends IBaseModel {
        void updateUserInfo(Map map, Callback callback);
        void logout(Callback callback);
        void refreshToken(Callback callback);
    }
}
