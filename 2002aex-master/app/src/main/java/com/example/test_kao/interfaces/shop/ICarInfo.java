package com.example.test_kao.interfaces.shop;

import com.example.test_kao.bean.shop.CheckCarBean;
import com.example.test_kao.bean.shop.address.DefaultAddr;
import com.example.test_kao.interfaces.Callback;
import com.example.test_kao.interfaces.IBaseModel;
import com.example.test_kao.interfaces.IBasePresenter;
import com.example.test_kao.interfaces.IBaseView;

public interface ICarInfo {
    interface View extends IBaseView {
        void getResult(CheckCarBean checkCarBean);
        void getResult(DefaultAddr defaultAddr);
    }
    interface Presenter extends IBasePresenter<View> {
        void getCheckCarList(int addId, int coupId);
        void getdefaultAddress();
    }
    interface Model extends IBaseModel {
        void getCheckCarList(int addId, int coupId, Callback callback);
        void getdefaultAddress(Callback callback);
    }
}
