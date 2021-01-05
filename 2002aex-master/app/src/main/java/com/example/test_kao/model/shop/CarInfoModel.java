package com.example.test_kao.model.shop;

import com.example.test_kao.base.BaseModel;
import com.example.test_kao.bean.shop.CheckCarBean;
import com.example.test_kao.bean.shop.address.DefaultAddr;
import com.example.test_kao.interfaces.Callback;
import com.example.test_kao.interfaces.shop.ICarInfo;
import com.example.test_kao.net.CommonSubscriber;
import com.example.test_kao.net.HttpManger;
import com.example.test_kao.utils.RxUtils;

public class CarInfoModel extends BaseModel implements ICarInfo.Model {
    @Override
    public void getCheckCarList(int addId, int coupId, Callback callback) {
        addDisposable(HttpManger.getInstance().getApiService().getCheckCarList(addId,coupId)
        .compose(RxUtils.rxScheduler())
        .subscribeWith(new CommonSubscriber<CheckCarBean>(callback) {
            @Override
            public void onNext(CheckCarBean checkCarBean) {
                callback.Scuess(checkCarBean);
            }
        }));
    }

    @Override
    public void getdefaultAddress(Callback callback) {
        addDisposable(HttpManger.getInstance().getApiService().getdefaultAddress()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<DefaultAddr>(callback) {
                    @Override
                    public void onNext(DefaultAddr defaultAddr) {
                        callback.Scuess(defaultAddr);
                    }
                }));
    }
}
