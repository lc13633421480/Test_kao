package com.example.test_kao.model.me;

import com.example.test_kao.base.BaseModel;
import com.example.test_kao.bean.login.TokenBean;
import com.example.test_kao.bean.me.LogOutBean;
import com.example.test_kao.bean.me.UserInfoBean;
import com.example.test_kao.bean.shop.CarBean;
import com.example.test_kao.interfaces.Callback;
import com.example.test_kao.interfaces.me.IMe;
import com.example.test_kao.net.CommonSubscriber;
import com.example.test_kao.net.HttpManger;
import com.example.test_kao.utils.RxUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class UserInfoModel extends BaseModel implements IMe.Model {

    @Override
    public void updateUserInfo(Map map, Callback callback) {
        addDisposable((Disposable) HttpManger.getInstance().getApiService().updateUserInfo(map)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<UserInfoBean>(callback) {
                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        callback.Scuess(userInfoBean);
                    }
                }));
    }

    @Override
    public void logout(Callback callback) {
        addDisposable(HttpManger.getInstance().getApiService().logout()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<LogOutBean>(callback) {
                    @Override
                    public void onNext(LogOutBean logOutBean) {
                        callback.Scuess(logOutBean);
                    }
                }));
    }

    @Override
    public void refreshToken(Callback callback) {
        addDisposable(HttpManger.getInstance().getApiService().refreshToken()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<TokenBean>(callback) {
                    @Override
                    public void onNext(TokenBean tokenBean) {
                        callback.Scuess(tokenBean);
                    }
                }));
    }
}
