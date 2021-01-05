package com.example.test_kao.model.app;

import android.util.Log;

import com.example.test_kao.base.BaseModel;
import com.example.test_kao.bean.app.AppBean;
import com.example.test_kao.interfaces.Callback;
import com.example.test_kao.interfaces.app.IApp;
import com.example.test_kao.net.CommonSubscriber;
import com.example.test_kao.net.HttpManger;
import com.example.test_kao.utils.RxUtils;

public class AppModel extends BaseModel implements IApp.Model {
    @Override
    public void getAppInfo(Callback callback) {
        addDisposable(HttpManger.getInstance().getApiService().getAppInfo().
                compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<AppBean>(callback) {
                    @Override
                    public void onNext(AppBean appBean) {
                        Log.i("TAG","model onNext register");
                        callback.Scuess(appBean);
                    }
                }));
    }
}
