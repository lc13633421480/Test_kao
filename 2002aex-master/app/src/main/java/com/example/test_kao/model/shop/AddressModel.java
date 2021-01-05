package com.example.test_kao.model.shop;

import com.example.test_kao.base.BaseModel;
import com.example.test_kao.bean.shop.address.ProvinceBean;
import com.example.test_kao.bean.shop.address.AddAddressBean;
import com.example.test_kao.bean.shop.address.AddressBean;


import com.example.test_kao.interfaces.Callback;
import com.example.test_kao.interfaces.shop.IAddress;
import com.example.test_kao.net.CommonSubscriber;
import com.example.test_kao.net.HttpManger;
import com.example.test_kao.utils.RxUtils;


import java.util.HashMap;

import io.reactivex.disposables.Disposable;

public class AddressModel extends BaseModel implements IAddress.Model {
    @Override
    public void getAddress(Callback callback) {
        addDisposable(HttpManger.getInstance().getApiService().getAddress()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<AddressBean>(callback) {
                    @Override
                    public void onNext(AddressBean addressBean) {
                        callback.Scuess(addressBean);
                    }
                }));
    }

    @Override
    public void getProvince(int parentId, Callback callback) {
        addDisposable(HttpManger.getInstance().getApiService().getProvince(parentId)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<ProvinceBean>(callback) {
                    @Override
                    public void onNext(ProvinceBean provinceBean) {
                        callback.Scuess(provinceBean);
                    }
                }));
    }

    @Override
    public void addAddr(HashMap map, Callback callback) {
        addDisposable((Disposable) HttpManger.getInstance().getApiService().addAddr(map)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<AddAddressBean>(callback) {
                    @Override
                    public void onNext(AddAddressBean addAddressBean) {
                        callback.Scuess(addAddressBean);
                    }
                }));
    }
}
