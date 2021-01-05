package com.example.test_kao.interfaces.shop;

import com.example.test_kao.bean.shop.address.AddAddressBean;
import com.example.test_kao.bean.shop.address.AddressBean;
import com.example.test_kao.bean.shop.address.ProvinceBean;
import com.example.test_kao.interfaces.Callback;
import com.example.test_kao.interfaces.IBaseModel;
import com.example.test_kao.interfaces.IBasePresenter;
import com.example.test_kao.interfaces.IBaseView;

import java.util.HashMap;

public interface IAddress {
    interface View extends IBaseView {
        void getResult(AddressBean addressBean);
        void getResult(ProvinceBean provinceBean);
        void getResult(AddAddressBean addAddressBean);
    }
    interface Presenter extends IBasePresenter<View> {
        void getAddress();
        void getProvince(int parentId);
        void addAddr(HashMap map);
    }
    interface Model extends IBaseModel {
        void getAddress(Callback callback);
        void getProvince(int parentId, Callback callback);
        void addAddr(HashMap map, Callback callback);
    }
}
