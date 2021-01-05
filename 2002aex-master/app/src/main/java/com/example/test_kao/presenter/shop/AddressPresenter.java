package com.example.test_kao.presenter.shop;

import com.example.test_kao.base.BasePresenter;
import com.example.test_kao.bean.shop.address.AddAddressBean;
import com.example.test_kao.bean.shop.address.AddressBean;
import com.example.test_kao.bean.shop.address.ProvinceBean;
import com.example.test_kao.interfaces.Callback;
import com.example.test_kao.interfaces.shop.IAddress;
import com.example.test_kao.model.shop.AddressModel;

import java.util.HashMap;

public class AddressPresenter extends BasePresenter<IAddress.View> implements IAddress.Presenter {
    IAddress.View view;
    IAddress.Model model;
    public AddressPresenter(IAddress.View view) {
        this.view = view;
        model = new AddressModel();
    }

    @Override
    public void getAddress() {
        model.getAddress(new Callback() {
            @Override
            public void Scuess(Object o) {
                if(view != null){
                    view.getResult((AddressBean) o);
                }
            }

            @Override
            public void Faile(String msg) {
                if(view != null){
                    view.showToast(msg);
                }
            }
        });
    }

    @Override
    public void getProvince(int parentId) {
        model.getProvince(parentId, new Callback() {
            @Override
            public void Scuess(Object o) {
                if(view != null){
                    view.getResult((ProvinceBean) o);
                }
            }

            @Override
            public void Faile(String msg) {
                if(view != null){
                    view.showToast(msg);
                }
            }
        });
    }

    @Override
    public void addAddr(HashMap map) {
        model.addAddr(map, new Callback() {
            @Override
            public void Scuess(Object o) {
                if(view != null){
                    view.getResult((AddAddressBean) o);
                }
            }

            @Override
            public void Faile(String msg) {
                if(view != null){
                    view.showToast(msg);
                }
            }
        });
    }
}